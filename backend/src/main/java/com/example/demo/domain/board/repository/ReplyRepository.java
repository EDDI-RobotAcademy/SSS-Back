package com.example.demo.domain.board.repository;

import com.example.demo.domain.board.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

//    List<Reply> findByReplyIdAndReplyContent(Long replyId, String replyContent);
//    List<Reply> findReplyContentByReplyId(Long replyId);


    @Query("select qc from Reply qc join fetch qc.Board qb where qb.boardId = :boardId")
    List<Reply> findAll(Long boardId);

    Long countBy();


    Reply findFirstByOrderByReplyIdDesc();

}