package com.example.demo.domain.member.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Authority {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    private AuthorityType authorityType;

    public Authority(AuthorityType authorityType) {
        this.authorityType = authorityType;
    }

    public static Authority ofMember(AuthorityType authorityType) {
        return new Authority(authorityType);
    }
}
