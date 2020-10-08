package com.api_board.domain.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CommentUpdateRequest {

    @NotNull
    private Integer Id;

    @NotNull
    private String content;
}
