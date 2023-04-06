package com.example.demo.domain.member.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Authority {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "authority_id")
    private Long id;

    @Getter
    private AuthorityType authorityName;

    public Authority(AuthorityType authorityName) {
        this.authorityName = authorityName;
    }

    public static Authority ofMember(AuthorityType authorityName) {
        return new Authority(authorityName);
    }
}
