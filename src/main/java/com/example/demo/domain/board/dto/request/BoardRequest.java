package com.example.demo.domain.board.dto.request;

import com.example.demo.domain.board.entity.Board;
import com.example.demo.domain.member.entity.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BoardRequest {

    final private String title;

    final private String writer;

    final private String content;

    final private Boolean privateCheck;


    public Board toBoard(Member member){
        return new Board(
                title, writer, content, privateCheck, member
        );
    }
}