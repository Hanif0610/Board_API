package com.api_board.domain.payload.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ChargePasswordRequest {

    private String password;
}
