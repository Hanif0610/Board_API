package com.api_board.service.comment;

import com.api_board.domain.entity.Board;
import com.api_board.domain.entity.Comment;
import com.api_board.domain.entity.User;
import com.api_board.domain.payload.request.CommentRequest;
import com.api_board.domain.payload.request.CommentUpdateRequest;
import com.api_board.domain.repository.BoardRepository;
import com.api_board.domain.repository.CommentRepository;
import com.api_board.domain.repository.UserRepository;
import com.api_board.exception.BoardNotFoundException;
import com.api_board.exception.CommentNotFoundException;
import com.api_board.exception.UserNotFoundException;
import com.api_board.exception.UserNotSameException;
import com.api_board.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    private final AuthenticationFacade authenticationFacade;

    @Override
    public Integer postComment(CommentRequest commentRequest) {
        User user = userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        Board board = boardRepository.findById(commentRequest.getBoardId())
                .orElseThrow(BoardNotFoundException::new);

        Comment parent = null;
        if (commentRequest.getParent_comment_id() != null) {
            parent = commentRepository.findByCommentId(commentRequest.getParent_comment_id());
        }

        Comment comment = commentRepository.save(
                Comment.builder()
                        .content(commentRequest.getContent())
                        .parentComment(parent)
                        .boardId(board.getId())
                        .userId(user.getId())
                        .createdAt(LocalDateTime.now())
                        .build()
        );
        return comment.getCommentId();
    }

    @Override
    public void changeComment(CommentUpdateRequest commentUpdateRequest) {
        User user = userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        Comment comment = commentRepository.findByCommentId(commentUpdateRequest.getId());

        if(comment == null) throw new CommentNotFoundException();
        if(!user.getId().equals(comment.getUserId())) throw new UserNotSameException();

        comment.setContent(commentUpdateRequest.getContent());
        comment.setCreatedAt(LocalDateTime.now());

        commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Integer commentId) {
        User user = userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);
        Comment comment = commentRepository.findByCommentId(commentId);

        if(comment == null) throw new CommentNotFoundException();
        if(!user.getId().equals(comment.getUserId())) throw new UserNotSameException();

        commentRepository.deleteById(commentId);
    }
}
