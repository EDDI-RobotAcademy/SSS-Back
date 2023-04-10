package com.example.demo.domain.board.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReplyRequest {
        final private Long boardId;
        final private String replyWriter;
        final private String replyContent;

}
