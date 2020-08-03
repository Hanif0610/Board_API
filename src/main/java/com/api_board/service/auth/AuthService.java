package com.api_board.service.auth;

import com.api_board.domain.payload.request.SignInRequest;
import com.api_board.domain.payload.response.TokenResponse;

public interface AuthService {

    TokenResponse signIn(SignInRequest signInRequest);
    TokenResponse refreshToken(String refreshToken);
}
