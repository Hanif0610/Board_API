package com.api_board.controller;

import com.api_board.domain.payload.request.ChargePasswordRequest;
import com.api_board.domain.payload.request.SignUpRequest;
import com.api_board.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public void signUp(@RequestBody @Valid SignUpRequest signUpRequest) {
        userService.signUp(signUpRequest);
    }

    @PutMapping("/password")
    public void changePassword(@RequestHeader("Authorization") @NotNull String token,
                               @RequestBody @Valid ChargePasswordRequest chargePasswordRequest) {
        userService.chargePassword(token, chargePasswordRequest);
    }
}
