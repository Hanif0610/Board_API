package com.api_board.domain.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReplyUpdateRequest {

    private Integer rno;

    private String content;
}
