package yoon.test.oAuthTest1.dto;

import lombok.Getter;
import yoon.test.oAuthTest1.entity.Members;

import java.io.Serializable;

@Getter
public class SessionMember implements Serializable {

    private String email;
    private String provider;
    private String name;

    public SessionMember(Members members){
        this.email = members.getEmail();
        this.provider = members.getProvider();
        this.name = members.getName();
    }
}
