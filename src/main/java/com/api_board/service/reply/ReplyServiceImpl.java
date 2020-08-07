package com.api_board.service.reply;

import com.api_board.domain.entity.Board;
import com.api_board.domain.entity.Reply;
import com.api_board.domain.entity.User;
import com.api_board.domain.payload.request.ReplyRequest;
import com.api_board.domain.payload.response.ReplyResponse;
import com.api_board.domain.repository.BoardRepository;
import com.api_board.domain.repository.ReplyRepository;
import com.api_board.domain.repository.UserRepository;
import com.api_board.exception.BoardNotFoundException;
import com.api_board.exception.UserNotFoundException;
import com.api_board.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {

    private final UserRepository userRepository;

    private final BoardRepository boardRepository;

    private final ReplyRepository replyRepository;

    @Override
    public Integer writeComments(String token, ReplyRequest replyRequest) {
        User user = userRepository.findById(JwtTokenUtil.parseAccessToken(token))
                .orElseThrow(UserNotFoundException::new);

        Board board = boardRepository.findById(replyRequest.getBno()).orElseThrow(BoardNotFoundException::new);

        Reply parent = null;
        if(replyRequest.getParent_comment_id() != null) {
            parent = replyRepository.findByRno(replyRequest.getParent_comment_id());
        }

        Reply reply = replyRepository.save(
                Reply.builder()
                        .content(replyRequest.getContent())
                        .parentComment(parent)
                        .writer(user.getName())
                        .bno(board.getId())
                        .build()
        );
        return reply.getRno();
    }

}
