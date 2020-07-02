package com.api_board.domain.payload.request;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Builder
public class SignIn {

    @Email
    private String email;

    @NotBlank
    private String password;
}
