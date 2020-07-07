package com.api_board.domain.payload.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String content;
}
