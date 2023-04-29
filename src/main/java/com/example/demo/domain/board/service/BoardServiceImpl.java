package com.example.demo.domain.board.service;

import com.example.demo.domain.board.dto.request.BoardRequest;
import com.example.demo.domain.board.entity.Board;
import com.example.demo.domain.board.entity.Reply;
import com.example.demo.domain.board.repository.BoardRepository;
import com.example.demo.domain.board.repository.ReplyRepository;
import com.example.demo.domain.member.entity.Member;
import com.example.demo.domain.member.repository.MemberRepository;
import com.example.demo.domain.security.service.RedisService;
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
    MemberRepository memberRepository;

    @Autowired
    final private ReplyRepository replyRepository;

    @Autowired
    RedisService redisService;

    @Override
    public Board register(Long memberId, BoardRequest boardRequest) {
        Optional<Member> maybeMember = memberRepository.findByMemberId(memberId);

        if (maybeMember.isEmpty()) {
            System.out.println("Member 정보를 찾지 못했습니다: ");
            return null;
        } else {
            Board board = new Board();
            board.setTitle(boardRequest.getTitle());
            board.setWriter(boardRequest.getWriter());
            board.setContent(boardRequest.getContent());
            board.setPrivateCheck(boardRequest.getPrivateCheck());
            board.setMember(maybeMember.get());

            boardRepository.save(board);
            return board;
        }

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

        for(Reply reply : replyList) {
            replyRepository.delete(reply);
        }
        boardRepository.deleteById(boardId);
    }

    @Override
    public Board modify(Long boardId, BoardRequest boardRequest) {
        Optional<Board> maybeBoard = boardRepository.findById(boardId);

        if (maybeBoard.isEmpty()) {
            System.out.println("Board 정보를 찾지 못했습니다: " + boardId);
            return null;
        }

        Board board = maybeBoard.get();
        board.setTitle(boardRequest.getTitle());
        board.setContent(boardRequest.getContent());

        boardRepository.save(board);

        return board;
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
        Optional<Member> maybeMember = memberRepository.findById(memberId);
        Member member = maybeMember.get();
        log.info("게시글 작성자: " + member.getMemberId());
        List<Board> boardList = boardRepository.findByMember_MemberId(member.getMemberId());
        return boardList;
    }
}