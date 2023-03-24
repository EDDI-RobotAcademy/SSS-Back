package com.example.demo.domain.board.service;

import com.example.demo.domain.board.controller.request.BoardRequest;
import com.example.demo.domain.board.entity.Reply;

import java.util.List;

public interface ReplyService {

    /**
     * 게시물 저장
     */
    public Reply register(BoardRequest boardRequest);

    /**
     * 게시물 리스트
     */
    List<Reply> list();

    /**
     * 게시물 읽기
     */
    Reply read(Long boardId);

    /**
     * 게시물 삭제
     */
    void remove(Long boardId);

    /**
     * 게시물 수정
     */
    Reply modify(Long boardId, BoardRequest boardRequest);

    /**
     * 게시물 개수
     */
    Long getCount();

    /**
     * 마지막 엔티티 id번호
     */
    Long getLastEntityId();
}