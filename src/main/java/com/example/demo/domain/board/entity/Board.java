package com.example.demo.domain.board.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok. *;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="board_id")
    private Long boardId;

    @Column(length = 128, nullable = false)
    private String title;

//
//    @OneToMany(mappedBy = "board", fetch=FetchType.LAZY)
//    @JsonManagedReference
//    private List<Reply> replyList = new ArrayList<>();

    @Column(length = 32, nullable = false)
    private String writer;

    private String content;

    @CreationTimestamp
    private Date regDate;

    @UpdateTimestamp
    private Date updDate;
}