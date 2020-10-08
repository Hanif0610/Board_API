package com.api_board.domain.repository;

import com.api_board.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findAllByBoardIdOrderByBoardIdAsc(Integer boardId);
    List<Comment> findAllByBoardId(Integer boardId);
    Comment findByCommentId(Integer commentId);
}
