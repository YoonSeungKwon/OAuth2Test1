package yoon.test.oAuthTest1.dto;

import lombok.Builder;
import lombok.Getter;
import yoon.test.oAuthTest1.entity.Members;
import yoon.test.oAuthTest1.enums.Role;

import java.util.Map;

@Getter
public class OAuthAttributes {

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String provider;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String provider){
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.provider = provider;
    }

    public static OAuthAttributes of(String regId, String nameAttributes, Map<String, Object> attribute){
        return ofGoogle(nameAttributes, attribute);
    }

    public static OAuthAttributes ofGoogle(String nameAttributeKey,Map<String, Object> attribute){
        return OAuthAttributes.builder()
                .email(String.valueOf(attribute.get("email")))
                .provider(String.valueOf(attribute.get("provider")))
                .name(String.valueOf(attribute.get("name")))
                .attributes(attribute)
                .nameAttributeKey(nameAttributeKey)
                .build();
    }

    public Members toEntity(){
        return Members.builder()
                .email(email)
                .provider(provider)
                .name(name)
                .role(Role.GUEST)
                .build();
    }
}
