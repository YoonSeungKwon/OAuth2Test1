package yoon.test.oAuthTest1.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import yoon.test.oAuthTest1.enums.Role;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "member")
public class Members {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idx;

    @Column(nullable = false, length = 50)
    private String email;

    @Column(nullable = false, length = 250)
    private String provider;

    @Column(nullable = false, length = 50)
    private String name;

    @Enumerated(EnumType.STRING)
    private Role role;

    @CreationTimestamp
    private LocalDateTime regdate;

    public Members(String email, String provider, String name, Role role){
        this.email = email;
        this.provider = provider;
        this.name = name;
        this.role = role;
    }

    public String getEmail(){
        return this.email;
    }

    public String getRoleKey(){
        return this.role.getKey();
    }

    public Members update(String name, String provider){
        this.name = name;
        this.provider = provider;
        return this;
    }
}
