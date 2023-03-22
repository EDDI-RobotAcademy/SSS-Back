package com.example.demo.domain.board.controller;

import com.example.demo.domain.board.controller.request.BoardReplyRequest;
import com.example.demo.domain.board.entity.BoardReply;
import com.example.demo.domain.board.service.BoardReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8080", allowedHeaders = "*")
public class BoardReplyController {

    final private BoardReplyService boardReplyService;

    @PostMapping("/replyRegister")
    public BoardReply boardReplyRegister (@RequestBody BoardReplyRequest boardReplyRequest) {
        log.info("boardRegister()");

        return boardReplyService.replyRegister(boardReplyRequest);
    }

    @GetMapping("/{replyId}")
    public BoardReply boardReplyRead(@PathVariable("replyId") Long replyId) {
        log.info("boardReplyRead()");

        return boardReplyService.replyRead(replyId);
    }
}