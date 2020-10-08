package com.api_board.domain.payload.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class CommentResponse {

    private Integer id;

    private Integer boardId;

    private String writer;

    private String content;

    private List<Integer> child_comments;

    private LocalDateTime createdAt;

    private boolean inMine;
}
