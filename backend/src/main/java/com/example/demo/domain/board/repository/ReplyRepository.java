package com.example.demo.domain.board.repository;

import com.example.demo.domain.board.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    List<Reply> findByReplyIdAndReplyWriter(Long replyId, String replyWriter);
    List<Reply> findReplyContentByReplyId(Long replyId);



    Long countBy();


    Reply findFirstByOrderByReplyIdDesc();
}