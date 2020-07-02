package com.api_board.service.auth;

import com.api_board.domain.payload.request.SignIn;
import com.api_board.domain.payload.response.TokenResponse;

public interface AuthService {

    TokenResponse signIn(SignIn signIn);
    TokenResponse refreshToken(String refreshToken);
}
