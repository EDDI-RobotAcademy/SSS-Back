package com.example.demo.domain.member.entity;

import com.example.demo.domain.security.entity.BasicAuthentication;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.swing.text.html.Option;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.example.demo.domain.security.entity.Authentication;

@Entity
@NoArgsConstructor
public class Member {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Column(nullable = false)
    private String email;

    @Getter
    @Column(nullable = false)
    private String nickname;

    @Getter
    @Column
    private String authorityCode;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<Authentication> authentications = new HashSet<>();

    public Member(String email, String nickname, String authorityCode) {
        this.email = email;
        this.nickname = nickname;
        this.authorityCode = authorityCode;
    }

    //올바른 패스워드인지 확인
    public boolean isRightPassword(String plainToCheck) {
        final Optional<Authentication> maybeBasicAuth =  findBasicAuthentication();

        if (maybeBasicAuth.isPresent()) {
            final BasicAuthentication auth = (BasicAuthentication) maybeBasicAuth.get();
            return auth.isRightPassword(plainToCheck);
        }
        return false;
    }

    private Optional<Authentication> findBasicAuthentication() {
        return authentications
                .stream()
                .filter(auth -> auth instanceof BasicAuthentication)
                .findFirst();
    }

}
