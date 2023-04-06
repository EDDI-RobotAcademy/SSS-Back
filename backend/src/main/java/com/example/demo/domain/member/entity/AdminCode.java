package com.example.demo.domain.member.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class AdminCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_code_id")
    private Long id;

    @Getter
    @Column(nullable = false)
    private String code;

    public AdminCode(String code) {
        this.code = code;
    }

    public static AdminCode of(String code) {
        return new AdminCode(code);
    }
}
