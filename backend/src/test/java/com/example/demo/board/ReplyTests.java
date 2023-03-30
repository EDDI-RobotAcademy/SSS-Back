package com.example.demo.board;

import com.example.demo.domain.board.controller.request.BoardRequest;
import com.example.demo.domain.board.controller.request.ReplyRequest;
import com.example.demo.domain.board.entity.Board;
import com.example.demo.domain.board.entity.Reply;
import com.example.demo.domain.board.service.ReplyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ReplyTests {

    @Autowired
    private ReplyService replyService;

    @Test
    public void 게시물_저장_테스트() {
        ReplyRequest replyRequest =
                new ReplyRequest(1L,  "되냐");

        replyService.register(replyRequest);
    }

    @Test
    public void 게시물_리스트_테스트() {
        System.out.println(replyService.list());
    }

    @Test
    public void 게시물_읽기_테스트() {

        Reply reply = replyService.read(14L);
        System.out.println(reply);
    }

    @Test
    public void 게시물_수정_테스트 () {
        // 글 작성자가 바뀌면 되나요 ?
        Reply reply = replyService.modify(14L, new ReplyRequest(
                1L, "변경하니 ?"));

        System.out.println(reply);
    }

    @Test
    public void 게시물_삭제_테스트 () {
        replyService.remove(1L);
        replyService.read(1L);
    }

    @Test
    public void 현재_게시물의_개수 () {
        System.out.println(replyService.getCount());
    }

    @Test
    public void 마지막_엔티티_id번호 () {
        System.out.println(replyService.getLastEntityId());
    }

    @Test
    public void 게시판_구동_전체_테스트 () {
        ReplyRequest replyRequest =
                new ReplyRequest(1L,  "되냐");
        replyService.register(replyRequest);
        Long lastBoardId = replyService.getLastEntityId();

        System.out.println("초기 등록: " + replyService.read(lastBoardId));

        replyService.modify(lastBoardId, new ReplyRequest(
                1L, "변경그만"));

        System.out.println("수정 후: " + replyService.read(lastBoardId));

        replyService.remove(lastBoardId);

        System.out.println("삭제 후: " + replyService.read(lastBoardId));
    }
}