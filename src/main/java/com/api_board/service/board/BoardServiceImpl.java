package com.api_board.service.board;

import com.api_board.domain.entity.Board;
import com.api_board.domain.entity.User;
import com.api_board.domain.payload.request.BoardRequest;
import com.api_board.domain.payload.response.BoardListResponse;
import com.api_board.domain.payload.response.BoardResponse;
import com.api_board.domain.repository.BoardRepository;
import com.api_board.domain.repository.UserRepository;
import com.api_board.exception.BoardNotFoundException;
import com.api_board.exception.UserNotFoundException;
import com.api_board.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    private final UserRepository userRepository;

    @Override
    public void write(String token, BoardRequest boardRequest) {
        User user = userRepository.findById(JwtTokenUtil.parseAccessToken(token))
                .orElseThrow(UserNotFoundException::new);

        boardRepository.save(
                Board.builder()
                .title(boardRequest.getTitle())
                .content(boardRequest.getContent())
                .author(user.getName())
                .createDate(LocalDate.now())
                .build()
        );
    }

    @Override
    public List<BoardListResponse> boardList() {
        List<BoardListResponse> listBoard = new ArrayList<>();

        for (Board board : boardRepository.findAllByOrderByCreateDateAsc()) {
            listBoard.add(
                    BoardListResponse.builder()
                                .uuid(board.getUuid())
                                .title(board.getTitle())
                                .author(board.getAuthor())
                                .createdDate(board.getCreateDate())
                                .build()
            );
        }
        return listBoard;
    }

    @Override
    public BoardResponse getBoard(String token, Integer uuid) {
        userRepository.findById(JwtTokenUtil.parseAccessToken(token))
                .orElseThrow(UserNotFoundException::new);
        Board board = boardRepository.findById(uuid).orElseThrow(BoardNotFoundException::new);

        return BoardResponse.builder()
                            .title(board.getTitle())
                            .content(board.getTitle())
                            .author(board.getAuthor())
                            .createdDate(board.getCreateDate())
                            .build();
    }

    @Override
    public void deleteBoard(String token, Integer uuid) {
        
    }

}