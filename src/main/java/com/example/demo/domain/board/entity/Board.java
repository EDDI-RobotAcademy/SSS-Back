package com.example.demo.domain.board.entity;

import com.example.demo.domain.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Getter
@Data
@Entity
@NoArgsConstructor
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="board_id")
    private Long boardId;

    @Column(length = 128, nullable = false)
    private String title;

    @Column(length = 32, nullable = false)
    private String writer;

    private String content;

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private boolean privateCheck;

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date regDate;

    @UpdateTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date updDate;

    @JsonIgnore

    @JoinColumn(name="member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    public Board(String title, String writer, String content, Boolean privateCheck, Member member) {
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.privateCheck = privateCheck;
        this.member = member;
    }

    public void modifyBoard(String title, String content) {
        this.title = title;
        this.content = content;
    }
}