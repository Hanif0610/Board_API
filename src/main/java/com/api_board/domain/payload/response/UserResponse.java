package com.api_board.domain.payload.response;

import lombok.*;

@Getter
@Builder
public class UserResponse {

    private Integer uuid;
    private String email;
    private String password;
    private String name;
}
