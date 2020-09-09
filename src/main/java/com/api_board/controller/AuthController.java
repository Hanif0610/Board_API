package com.api_board.controller;

import com.api_board.domain.payload.request.SignInRequest;
import com.api_board.domain.payload.response.TokenResponse;
import com.api_board.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping
    public TokenResponse signIn(@RequestBody @Valid SignInRequest signInRequest) {
        return authService.signIn(signInRequest);
    }

    @PutMapping
    public TokenResponse refreshToken(@RequestHeader("X-Refresh-Token") @Valid String refreshToken) {
        return authService.refreshToken(refreshToken);
    }
}
