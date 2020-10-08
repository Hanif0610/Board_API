package com.api_board.service.comment;

import com.api_board.domain.payload.request.CommentRequest;
import com.api_board.domain.payload.request.CommentUpdateRequest;

public interface CommentService {

    Integer postComment(CommentRequest commentRequest);
    void changeComment(CommentUpdateRequest commentUpdateRequest);
    void deleteComment(Integer commentId);
}
