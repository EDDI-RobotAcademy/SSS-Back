package com.example.demo.domain.board.service;

import com.example.demo.domain.board.dto.request.ReplyRequest;
import com.example.demo.domain.board.entity.Reply;

import java.util.List;

public interface ReplyService {

    /**
     * 댓글 저장
     */
    void replyRegister(ReplyRequest replyRequest);

    /**
     * 댓글 리스트
     */
    List<Reply> replyList(Long boarId);

    /**
     * 댓글 삭제
     */
    void replyRemove(Long replyId);

    /**
     * 댓글 수정
     */
    Reply replyModify(Long replyId, ReplyRequest replyRequest);

    /**
     * 게시물 개수
     */
    Long getCount();

}