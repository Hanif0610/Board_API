package com.api_board.domain.payload.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class BoardResponse {

    private String title;

    private String content;

    private String author;

    private List<String> images;

    private List<CommentResponse> comments;

    private LocalDateTime createdAt;

    private boolean inMine;
}
