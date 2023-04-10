package com.example.demo.domain.board.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReplyListResponse {
    private Long replyId;
    private Long boardId;
    private String replyContent;
    private String replyWriter;

}
