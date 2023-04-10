package com.example.demo.board;

import com.example.demo.domain.board.dto.request.BoardRequest;
import com.example.demo.domain.board.dto.request.ReplyRequest;
import com.example.demo.domain.board.entity.Reply;
import com.example.demo.domain.board.service.BoardService;
import com.example.demo.domain.board.service.ReplyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("reply 게시판에 대한 테스트 코드 - mock 적용")
public class ReplyTests {

    @Autowired
    @Mock
    private ReplyService replyService;

    @Autowired
    @Mock
    private BoardService boardService;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void 댓글_등록을_위한_하나의_게시글_등록_테스트() {
        BoardRequest boardRequest =
                new BoardRequest("test", "김창주", "테스트돌려줘");

        boardService.register(boardRequest);
    }
    @Test
    public void 댓글_저장_테스트() {
        ReplyRequest replyRequest =
                new ReplyRequest(1L, "하하",  "되냐");

        replyService.replyRegister(replyRequest);
    }

    @Test
    public void 댓글_리스트_테스트() {
        System.out.println(replyService.replyList(1L));
    }

    @Test
    public void 댓글_수정_테스트 () {
        // 글 작성자가 바뀌면 되나요 ?
        Reply reply = replyService.replyModify(1L, new ReplyRequest(
                1L,"나다", "변경하니 ?"));

        System.out.println(reply);
    }

    @Test
    public void 댓글_삭제_테스트 () {
        replyService.replyRemove(1L);
    }

    @Test
    public void 현재_댓글의_개수 () {
        System.out.println(replyService.getCount());
    }

//    @Test
//    public void 마지막_엔티티_id번호 () {
//        System.out.println(replyService.getLastEntityId());
//    }

//    @Test
//    public void 게시판_구동_전체_테스트 () {
//        ReplyRequest replyRequest =
//                new ReplyRequest(1L,  "되냐");
//        replyService.register(replyRequest);
//        Long lastBoardId = replyService.getLastEntityId();
//
//        System.out.println("초기 등록: " + replyService.read(lastBoardId));
//
//        replyService.modify(lastBoardId, new ReplyRequest(
//                1L, "변경그만"));
//
//        System.out.println("수정 후: " + replyService.read(lastBoardId));
//
//        replyService.remove(lastBoardId);
//
//        System.out.println("삭제 후: " + replyService.read(lastBoardId));
//    }
}