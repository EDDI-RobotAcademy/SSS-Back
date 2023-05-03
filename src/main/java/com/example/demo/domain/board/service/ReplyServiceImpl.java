package com.example.demo.domain.board.service;

import com.example.demo.domain.board.dto.request.ReplyRequest;
import com.example.demo.domain.board.entity.Board;
import com.example.demo.domain.board.entity.Reply;
import com.example.demo.domain.board.repository.BoardRepository;
import com.example.demo.domain.board.repository.ReplyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ReplyServiceImpl implements ReplyService {

    @Autowired
    ReplyRepository replyRepository;

    @Autowired
    BoardRepository boardRepository;

    @Override
    public void replyRegister(ReplyRequest replyRequest) {
        Board myBoard = boardRepository.findById(replyRequest.getBoardId())
                .orElseThrow(() -> new RuntimeException("Board 정보를 찾지 못했습니다. " + replyRequest.getBoardId()));

        Reply newReply = replyRequest.toReply(myBoard);

        replyRepository.save(newReply);
    }

    @Override
    public List<Reply> replyList(Long boardId) {
        return replyRepository.findAll(boardId);
    }

    @Override
    public void replyRemove(Long replyId) {
        replyRepository.deleteById(replyId);
    }

    @Override
    public Reply replyModify(Long replyId, ReplyRequest replyRequest) {

        Reply myReply = replyRepository.findById(replyId)
                .orElseThrow(() -> new RuntimeException("reply 정보를 찾지 못했습니다: " + replyId));

        myReply.setReplyContent(replyRequest.getReplyContent());

        replyRepository.save(myReply);
        return myReply;
    }

    @Override
    public Long getCount() {
        return replyRepository.countBy();
    }


}