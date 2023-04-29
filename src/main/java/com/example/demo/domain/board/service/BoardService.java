package com.example.demo.domain.board.service;

import com.example.demo.domain.board.dto.request.BoardRequest;
import com.example.demo.domain.board.entity.Board;

import java.util.List;

public interface BoardService {

    /**
     * 게시물 저장
     */
    public Board register(Long memberId, BoardRequest boardRequest);

    /**
     * 게시물 리스트
     */
    List<Board> list();

    /**
     * 게시물 읽기
     */
    Board read(Long boardId);

    /**
     * 게시물 삭제
     */
    void remove(Long boardId);

    /**
     * 게시물 수정
     */
    Board modify(Long boardId, BoardRequest boardRequest);

    /**
     * 게시물 개수
     */
    Long getCount();

    /**
     * 마지막 엔티티 id번호
     */
    Long getLastEntityId();

    List<Board> memberBoardList(Long memberId);
}