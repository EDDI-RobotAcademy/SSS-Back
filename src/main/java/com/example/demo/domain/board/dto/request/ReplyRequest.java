package com.example.demo.domain.board.dto.request;

import com.example.demo.domain.board.entity.Board;
import com.example.demo.domain.board.entity.Reply;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ReplyRequest {

        final private Long boardId;
        final private String replyWriter;
        final private String replyContent;

        public Reply toReply(Board board){
                return new Reply(
                        replyWriter, replyContent, board,
                        LocalDateTime.now(), LocalDateTime.now()
                );
        }

}
