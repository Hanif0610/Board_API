package com.api_board.domain.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ReplyRequest {

    @NotBlank
    private String content;

    private Integer bno;

    private Integer parent_comment_id;

}
