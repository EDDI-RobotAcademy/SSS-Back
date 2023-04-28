package com.example.demo.domain.board.repository;

import com.example.demo.domain.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findByMember_MemberId(Long memberId);

    Long countBy();

    Board findFirstByOrderByBoardIdDesc();

    void deleteByMember_memberId(Long memberId);
}