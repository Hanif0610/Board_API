package com.api_board.service.board;

import com.api_board.domain.entity.Board;
import com.api_board.domain.entity.User;
import com.api_board.domain.payload.request.BoardRequest;
import com.api_board.domain.payload.response.UserResponse;
import com.api_board.domain.repository.BoardRepository;
import com.api_board.domain.repository.UserRepository;
import com.api_board.exception.UserNotFoundException;
import com.api_board.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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

}