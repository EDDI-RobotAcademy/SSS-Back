package com.example.demo.domain.board.controller;

import com.example.demo.domain.board.dto.request.ReplyRequest;
import com.example.demo.domain.board.entity.Reply;
import com.example.demo.domain.board.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/reply")
@RequiredArgsConstructor
public class ReplyController {

    @Autowired
    ReplyService replyService;

    // 댓글 등록
    @PostMapping("/register")
    public void replyRegister (
            @RequestBody ReplyRequest replyRequest) {
        log.info("replyRegister() 해당 게시물 아이디 : " + replyRequest.getBoardId());
        log.info(replyRequest.getReplyContent());
        log.info(replyRequest.getReplyWriter());

        replyService.replyRegister(replyRequest);
    }

    @GetMapping("/{replyId}")
    public List<Reply> replyList (@PathVariable("replyId") Long replyId) {
        log.info("replyList() 동작!");

        return replyService.replyList(replyId);
    }


    // 댓글 삭제
    @DeleteMapping("/{replyId}")
    public void replyRemove(@PathVariable("replyId") Long replyId) {
        log.info("replyRemove()");

        replyService.replyRemove(replyId);
    }

    // 댓글 수정
    @PutMapping("/{replyId}")
    public void replyModify(@PathVariable("replyId") Long replyId,
                            @RequestBody ReplyRequest replyRequest) {

        log.info("replyModify(): " + replyRequest + "replyId: " + replyId);

        replyService.replyModify(replyId, replyRequest);
    }
}