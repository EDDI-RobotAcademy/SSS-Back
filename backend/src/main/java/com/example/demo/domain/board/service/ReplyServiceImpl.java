package com.example.demo.domain.board.service;

import com.example.demo.domain.board.dto.request.ReplyRequest;
import com.example.demo.domain.board.entity.Board;
import com.example.demo.domain.board.entity.Reply;
import com.example.demo.domain.board.repository.BoardRepository;
import com.example.demo.domain.board.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {

    @Autowired
    ReplyRepository replyRepository;

    @Autowired
    BoardRepository boardRepository;

//    public Reply register(ReplyRequest replyRequest) {
//        Reply reply = new Reply();
////        reply.setReplyId(replyRequest.getReplyId());
//        reply.setReplyWriter(replyRequest.getReplyWriter());
//        reply.setReplyContent(replyRequest.getReplyContent());
//
//        replyRepository.save(reply);
//
//        return reply;
//    }

    @Override
    public void replyRegister(ReplyRequest replyRequest) {
        Optional<Board> maybeBoard = boardRepository.findById(replyRequest.getBoardId());

        Reply reply = new Reply();
        reply.setBoard(maybeBoard.get());
        reply.setReplyWriter(replyRequest.getReplyWriter());
        reply.setReplyContent(replyRequest.getReplyContent());

        replyRepository.save(reply);
    }

    @Override
    public List<Reply> replyList(Long boardId) {
        return replyRepository.findAll(boardId);
    }

    @Override
    public Reply replyRead(Long replyId) {
        Optional<Reply> maybeReply = replyRepository.findById(replyId);

        if (maybeReply.isEmpty()) {
            log.info("읽을 수 없습니다!");
            return null;
        }

        return maybeReply.get();
    }

    @Override
    public void replyRemove(Long replyId) {
        replyRepository.deleteById(replyId);
    }

    @Override
    public Reply replyModify(Long replyId, ReplyRequest replyRequest) {
        Optional<Reply> maybeReply = replyRepository.findById(replyId);

        if (maybeReply.isEmpty()) {
            System.out.println("reply 정보를 찾지 못했습니다: " + replyId);
            return null;
        }

        Reply reply = maybeReply.get();
//        reply.setReplyId(replyRequest.getReplyId());
        reply.setReplyContent(replyRequest.getReplyContent());

        replyRepository.save(reply);

        return reply;
    }


/*
    @Override
    public List<reply> bigMisstake(Long replyId, replyRequest replyRequest) {
        return replyRepository.findByreplyIdAndWriter(replyId, replyRequest.getWriter());
    }
*/
    @Override
    public Long getCount() {
        return replyRepository.countBy();
    }

    @Override
    public Long getLastEntityId() {
        Reply reply = replyRepository.findFirstByOrderByReplyIdDesc();
        return reply.getReplyId();
    }
}