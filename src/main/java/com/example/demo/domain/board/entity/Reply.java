package com.example.demo.domain.board.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long replyId;

    @Lob
    private String replyContent;

    @Column(length = 32, nullable = false)
    private String replyWriter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "board_id")
    private Board board;

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDateTime regDate = LocalDateTime.now();

    @UpdateTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDateTime updDate = LocalDateTime.now();

    public Reply(String replyContent, String replyWriter, Board board, LocalDateTime regDate, LocalDateTime updDate) {
        this.replyContent = replyContent;
        this.replyWriter = replyWriter;
        this.board = board;
        this.regDate = regDate;
        this.updDate = updDate;
    }

    public void update(String replyContent) {
        this.replyContent = replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }
}