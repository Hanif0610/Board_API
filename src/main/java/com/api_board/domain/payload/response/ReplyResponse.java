package com.api_board.domain.payload.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ReplyResponse {

    private Integer rno;

    private Integer bno;

    private String writer;

    private String content;

    private List<Integer> child_comments;
}
