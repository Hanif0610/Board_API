package com.api_board.domain.payload.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BoardListResponse {

    private Integer id;

    private String title;

    private String author;

    private LocalDateTime createdAt;
}
