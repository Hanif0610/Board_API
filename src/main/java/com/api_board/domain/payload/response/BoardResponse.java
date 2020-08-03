package com.api_board.domain.payload.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class BoardResponse {

    private String title;

    private String content;

    private String author;

    private List<String> uri;

    private String createdDate;
}
