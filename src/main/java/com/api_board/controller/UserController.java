package com.api_board.controller;

import com.api_board.domain.payload.request.ChargePassword;
import com.api_board.domain.payload.request.SignUp;
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
    public void signUp(@RequestBody @Valid SignUp signUp) {
        userService.signUp(signUp);
    }

    @PutMapping("/password")
    public void changePassword(@RequestHeader("Authorization") @NotNull String token,
                               @RequestBody @Valid ChargePassword chargePassword) {
        userService.chargePassword(token, chargePassword);
    }
}
