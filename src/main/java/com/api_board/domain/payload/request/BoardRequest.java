package com.api_board.domain.payload.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardRequest {

    private String title;

    private String content;
}
