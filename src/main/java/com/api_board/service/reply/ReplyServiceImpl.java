package com.api_board.service.reply;

import com.api_board.domain.entity.Board;
import com.api_board.domain.entity.Reply;
import com.api_board.domain.entity.User;
import com.api_board.domain.payload.request.ReplyRequest;
import com.api_board.domain.payload.request.ReplyUpdateRequest;
import com.api_board.domain.payload.response.ReplyResponse;
import com.api_board.domain.repository.BoardRepository;
import com.api_board.domain.repository.ReplyRepository;
import com.api_board.domain.repository.UserRepository;
import com.api_board.exception.BoardNotFoundException;
import com.api_board.exception.CommentNotFoundException;
import com.api_board.exception.UserNotFoundException;
import com.api_board.exception.UserNotSameException;
import com.api_board.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
        if (replyRequest.getParent_comment_id() != null) {
            parent = replyRepository.findByRno(replyRequest.getParent_comment_id());
        }

        Reply reply = replyRepository.save(
                Reply.builder()
                        .content(replyRequest.getContent())
                        .parentComment(parent)
                        .writer(user.getName())
                        .bno(board.getId())
                        .userId(user.getId())
                        .createAt(LocalDate.now())
                        .build()
        );
        return reply.getRno();
    }

    @Override
    public List<ReplyResponse> getComments(Integer bno) {

        Board board = boardRepository.findById(bno).orElseThrow(BoardNotFoundException::new);

        return board.getComments().stream()
                .map(comment -> {
                    List<Integer> comments = comment.getChildComment().stream()
                            .map(Reply::getRno)
                            .collect(Collectors.toList());

                    return ReplyResponse.builder()
                            .content(comment.getContent())
                            .rno(comment.getRno())
                            .bno(comment.getBno())
                            .writer(comment.getWriter())
                            .child_comments(comments)
                            .createAt(String.valueOf(comment.getCreateAt()))
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Override
    public void updateComments(String token, ReplyUpdateRequest replyUpdateRequest) {
        User user = userRepository.findById(JwtTokenUtil.parseAccessToken(token))
                .orElseThrow(UserNotFoundException::new);

        Reply reply = replyRepository.findByRno(replyUpdateRequest.getRno());

        if(reply == null) throw new CommentNotFoundException();
        if(!user.getId().equals(reply.getUserId())) throw new UserNotSameException();

        reply.setContent(replyUpdateRequest.getContent());
        reply.setCreateAt(LocalDate.now());

        replyRepository.save(reply);
    }

    @Override
    public void deleteComments(String token, Integer comment) {
        User user = userRepository.findById(JwtTokenUtil.parseAccessToken(token))
                .orElseThrow(UserNotFoundException::new);
        Reply reply = replyRepository.findByRno(comment);

        if(reply == null) throw new CommentNotFoundException();
        if(!user.getId().equals(reply.getUserId())) throw new UserNotSameException();

        replyRepository.deleteByRno(comment);
    }

}
