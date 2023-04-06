package com.example.demo.domain.board.controller;

import com.example.demo.domain.board.dto.request.ReplyRequest;
import com.example.demo.domain.board.entity.Reply;
import com.example.demo.domain.board.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/reply")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8080", allowedHeaders = "*")
public class ReplyController {

    final private ReplyService replyService;

    // 댓글 등록
    @PostMapping("/register")
    public void replyRegister (@RequestBody ReplyRequest replyRequest) {
        log.info("replyRegister() 게시물 아이디 : " + replyRequest.getBoardId());
        log.info(replyRequest.getReplyContent());
        log.info(replyRequest.getReplyWriter());

        replyService.replyRegister(replyRequest);
    }

    @GetMapping("/{replyId}")
    public List<Reply> replyList (@PathVariable("replyId") Long replyId) {
        log.info("replyList() 동작!");

        return replyService.replyList(replyId);
    }

    // 댓글 조회
    @GetMapping("/{replyId}")
    public Reply replyRead(@PathVariable("replyId") Long replyId) {
        log.info("replyRead()");

        return replyService.replyRead(replyId);
    }

    // 댓글 삭제
    @DeleteMapping("/{replyId}")
    public void replyRemove(@PathVariable("replyId") Long replyId) {
        log.info("replyRemove()");

        replyService.replyRemove(replyId);
    }

    // 댓글 수정
    @PutMapping("/{replyId}")
    public Reply replyModify(@PathVariable("replyId") Long replyId,
                             @RequestBody ReplyRequest replyRequest) {

        log.info("replyModify(): " + replyRequest + "replyId: " + replyId);

        return replyService.replyModify(replyId, replyRequest);
    }
}