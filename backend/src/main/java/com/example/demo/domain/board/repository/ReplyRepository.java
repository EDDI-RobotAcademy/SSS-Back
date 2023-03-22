package com.example.demo.domain.board.repository;

import com.example.demo.domain.board.entity.BoardReply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<BoardReply, Long> {

    List<BoardReply> findByWriter(String replyWriter);

}