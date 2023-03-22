package com.example.demo.domain.board.service;

import com.example.demo.domain.board.controller.request.BoardReplyRequest;
import com.example.demo.domain.board.entity.BoardReply;

public interface BoardReplyService {

    public BoardReply replyRegister(BoardReplyRequest boardReplyRequest);


    BoardReply replyRead(Long replyId);

}
