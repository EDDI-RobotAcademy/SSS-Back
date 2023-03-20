package com.example.demo.domain.member.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    //@OneToMany 조인 컬럼 작성 필요

    public Member(String email, String nickname) {
        this.email = email;
        this.nickname = nickname;

    }
}
