package com.api_board.controller;

import com.api_board.domain.payload.request.CommentRequest;
import com.api_board.domain.payload.request.CommentUpdateRequest;
import com.api_board.service.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public Integer writeComment(@RequestBody @Valid CommentRequest commentRequest) {
        return commentService.postComment(commentRequest);
    }

    @PutMapping
    public void updateComment(@RequestBody @Valid CommentUpdateRequest commentUpdateRequest) {
        commentService.changeComment(commentUpdateRequest);
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable Integer commentId) {
        commentService.deleteComment(commentId);
    }
}
