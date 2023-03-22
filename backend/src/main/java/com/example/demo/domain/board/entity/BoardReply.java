package com.example.demo.domain.board.entity;

import lombok.Data;

import javax.persistence.*;


    @Data
    @Entity
    public class BoardReply {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long replyId;

        @Column(length = 32, nullable = false)
        private String replyWriter;

        @Lob
        private String replyContent;

}
