package com.example.demo.domain.board.service;

import com.example.demo.domain.board.dto.request.ReplyRequest;
import com.example.demo.domain.board.entity.Reply;

import java.util.List;

public interface ReplyService {

    /**
     * 댓글 저장
     */
    public Reply register(ReplyRequest replyRequest);

    /**
     * 댓글 리스트
     */
    List<Reply> list();

    /**
     * 댓글 읽기
     */
    Reply read(Long replyId);

    /**
     * 댓글 삭제
     */
    void remove(Long replyId);

    /**
     * 댓글 수정
     */
    Reply modify(Long replyId, ReplyRequest replyRequest);

    /**
     * 게시물 개수
     */
    Long getCount();

    /**
     * 마지막 엔티티 id번호
     */
    Long getLastEntityId();
}