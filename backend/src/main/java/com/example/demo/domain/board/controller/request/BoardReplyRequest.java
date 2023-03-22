package com.example.demo.domain.board.controller.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BoardReplyRequest {

    final private String replyWriter;
    final private String replyContent;
}