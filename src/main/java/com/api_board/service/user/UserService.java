package com.api_board.service.user;

import com.api_board.domain.payload.request.ChargePasswordRequest;
import com.api_board.domain.payload.request.SignUpRequest;

public interface UserService {

    void signUp(SignUpRequest signUpRequest);
    void chargePassword(ChargePasswordRequest chargePasswordRequest);
}
