package com.api_board.domain.payload.request;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class SignInRequest {

    @Email
    private String email;

    @NotBlank
    private String password;
}
