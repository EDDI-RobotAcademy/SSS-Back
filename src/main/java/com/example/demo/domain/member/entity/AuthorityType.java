package com.example.demo.domain.member.entity;

import lombok.Getter;

@Getter
public enum AuthorityType {

    ADMIN("관리자"), MEMBER("일반회원");

    final private String AUTHORITY_TYPE;

    private AuthorityType(String AUTHORITY_TYPE) {
        this.AUTHORITY_TYPE = AUTHORITY_TYPE;
    }
}
