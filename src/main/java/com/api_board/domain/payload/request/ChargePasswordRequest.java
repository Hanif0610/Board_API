package com.api_board.domain.payload.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ChargePasswordRequest {

    @NotBlank
    private String password;
}
