package com.api_board.domain.payload.request;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    @Email
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String name;
}
