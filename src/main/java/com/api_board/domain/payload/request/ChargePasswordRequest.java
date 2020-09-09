package com.api_board.domain.payload.request;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ChargePasswordRequest {

    @NotBlank
    private String password;
}
