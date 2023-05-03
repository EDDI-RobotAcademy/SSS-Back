package com.example.demo.domain.board.controller;

import com.example.demo.domain.board.dto.request.BoardRequest;
import com.example.demo.domain.board.entity.Board;
import com.example.demo.domain.board.service.BoardService;
import com.example.demo.domain.utility.member.TokenBasedController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController extends TokenBasedController {

    final private BoardService boardService;

    // 게시물 등록
    @PostMapping("/register")
    public void boardRegister (@RequestBody BoardRequest boardRequest,
                               HttpServletRequest requestToken) {
        log.info("boardRegister()");
        Long memberId = findMemberIdByToken(requestToken);
        boardService.register(memberId, boardRequest);
    }

    @GetMapping("/list")
    public List<Board> boardList () {
        log.info("boardList()");
        return boardService.list();
    }

    // 게시물 조회
    @GetMapping("/{boardId}")
    public Board boardRead(@PathVariable("boardId") Long boardId) {
        log.info("boardRead()");

        return boardService.read(boardId);
    }

    // 게시물 삭제
    @DeleteMapping("/{boardId}")
    public void boardRemove(@PathVariable("boardId") Long boardId) {
        log.info("boardRemove()");

        boardService.remove(boardId);
    }

    // 게시물 수정
    @PutMapping("/{boardId}")
    public Board boardModify(@PathVariable("boardId") Long boardId,
                             @RequestBody BoardRequest boardRequest) {

        log.info("boardModify(): " + boardRequest + "id: " + boardId);

        return boardService.modify(boardId, boardRequest);
    }

    @GetMapping("/list-myBoard")
    public List<Board> memberBoardList(HttpServletRequest requestToken) {
        log.info("memberBoardList()");
        Long memberId = findMemberIdByToken(requestToken);
        return boardService.memberBoardList(memberId);
    }
}