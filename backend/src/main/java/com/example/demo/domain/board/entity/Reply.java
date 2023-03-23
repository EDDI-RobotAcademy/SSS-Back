package com.example.demo.domain.board.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String replyContent;

    @Column(length = 32, nullable = false)
    private String replyWriter;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Board.class)
    @JoinColumn(name= "board_id")
    private Board board;



}