package com.example.demo.domain.board.service;

import com.example.demo.domain.board.dto.request.BoardRequest;
import com.example.demo.domain.board.entity.Board;
import com.example.demo.domain.board.entity.Reply;
import com.example.demo.domain.board.repository.BoardRepository;
import com.example.demo.domain.board.repository.ReplyRepository;
import com.example.demo.domain.member.entity.Member;
import com.example.demo.domain.member.repository.MemberRepository;
import com.example.demo.domain.utility.member.MemberUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    @Autowired
    final private BoardRepository boardRepository;

    @Autowired
    final private ReplyRepository replyRepository;

    final private MemberRepository memberRepository;



    @Override
    public Board register(Long memberId, BoardRequest boardRequest) {
        Member member = MemberUtils.getMemberById(memberRepository,memberId);
        Board newBoard = boardRequest.toBoard(member);

        boardRepository.save(newBoard);
        return newBoard;
    }

    @Override
    @Transactional
    public List<Board> list() {

        return boardRepository.findAll(Sort.by(Sort.Direction.DESC, "BoardId"));
    }

    @Override
    public Board read(Long boardId) {
        Optional<Board> maybeBoard = boardRepository.findById(boardId);

        if (maybeBoard.isEmpty()) {
            log.info("읽을 수 없습니다!");
            return null;
        }
        return maybeBoard.get();
    }

    @Override
    public void remove(Long boardId) {
        List<Reply> replyList = replyRepository.findAll(boardId);
        replyRepository.deleteAll(replyList);
        boardRepository.deleteById(boardId);
    }

    @Override
    public Board modify(Long boardId, BoardRequest boardRequest) {
        Board myBoard = boardRepository.findById(boardId)
                        .orElseThrow(() -> new RuntimeException("Board 정보를 찾지 못했습니다. " + boardId));

        myBoard.modifyBoard(boardRequest.getTitle(),
                            boardRequest.getContent());
        boardRepository.save(myBoard);
        return myBoard;
    }
    @Override
    public Long getCount() {
        return boardRepository.countBy();
    }

    @Override
    public Long getLastEntityId() {
        Board board = boardRepository.findFirstByOrderByBoardIdDesc();
        return board.getBoardId();
    }

    @Override
    public List<Board> memberBoardList(Long memberId) {
        Member member =
                MemberUtils.getMemberById(memberRepository,memberId);
        log.info("게시글 작성자: " + member.getMemberId());

        return boardRepository.findByMember_MemberId(member.getMemberId());
    }
}