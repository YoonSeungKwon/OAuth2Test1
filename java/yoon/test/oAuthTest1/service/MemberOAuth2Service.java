package yoon.test.oAuthTest1.service;


import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import yoon.test.oAuthTest1.dto.OAuthAttributes;
import yoon.test.oAuthTest1.dto.SessionMember;
import yoon.test.oAuthTest1.entity.Members;
import yoon.test.oAuthTest1.repository.MemberRepository;

import java.util.Collection;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class MemberOAuth2Service implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        String userNameAttributeName =
                userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName,oAuth2User.getAttributes());
        Members members = saverOrUpdate(attributes);

        httpSession.setAttribute("user", new SessionMember(members));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(members.getRoleKey())), attributes.getAttributes(),
                        attributes.getNameAttributeKey()
        );
    }

    private Members saverOrUpdate(OAuthAttributes attributes){
        Members member = memberRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getProvider())).orElse(attributes.toEntity());
        return memberRepository.save(member);
    }
}
