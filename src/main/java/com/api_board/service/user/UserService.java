package com.api_board.service.user;

import com.api_board.domain.payload.request.ChargePassword;
import com.api_board.domain.payload.request.SignUp;

public interface UserService {

    void signUp(SignUp signUp);
    void chargePassword(String token, ChargePassword chargePassword);
}
