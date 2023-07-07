package yoon.test.oAuthTest1.cofig;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import yoon.test.oAuthTest1.service.MemberOAuth2Service;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final MemberOAuth2Service memberOAuth2Service;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())

                //세션 사용 안함
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                //OAuth 설정
                .oauth2Login(oauth-> oauth.userInfoEndpoint(userinfo->userinfo.userService(memberOAuth2Service)))

                .build();
    }


}
