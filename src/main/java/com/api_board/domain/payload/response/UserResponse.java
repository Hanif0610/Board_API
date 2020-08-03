package com.api_board.domain.payload.response;

import lombok.*;

@Getter
@Builder
public class UserResponse {

    private Integer id;
    private String email;
    private String password;
    private String name;
}
