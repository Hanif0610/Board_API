package com.api_board.domain.payload.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class BoardListResponse {

    private String title;

    private String author;

    private LocalDate createdDate;
}
