package com.example.demo.domain.board.controller;

import com.example.demo.domain.board.controller.request.ReplyRequest;
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

    @PostMapping("/register")
    public Reply replyRegister (@RequestBody ReplyRequest replyRequest) {
        log.info("replyRegister()");

        return replyService.register(replyRequest);
    }

    @GetMapping("/list")
    public List<Reply> replyList () {
        log.info("replyList()");

        return replyService.list();
    }

    @GetMapping("/{replyId}")
    public Reply replyRead(@PathVariable("replyId") Long replyId) {
        log.info("replyRead()");

        return replyService.read(replyId);
    }

    @DeleteMapping("/{replyId}")
    public void replyRemove(@PathVariable("replyId") Long replyId) {
        log.info("replyRemove()");

        replyService.remove(replyId);
    }

    @PutMapping("/{replyId}")
    public Reply replyModify(@PathVariable("replyId") Long replyId,
                             @RequestBody ReplyRequest replyRequest) {

        log.info("replyModify(): " + replyRequest + "replyId: " + replyId);

        return replyService.modify(replyId, replyRequest);
    }
}