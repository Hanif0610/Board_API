package com.api_board.service.auth;

import com.api_board.domain.entity.User;
import com.api_board.domain.payload.request.SignInRequest;
import com.api_board.domain.payload.response.TokenResponse;
import com.api_board.domain.repository.UserRepository;
import com.api_board.exception.UserNotFoundException;
import com.api_board.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public TokenResponse signIn(SignInRequest signInRequest) {
        User user = userRepository.findByEmail(signInRequest.getEmail())
                .filter(u -> passwordEncoder.matches(signInRequest.getPassword(), u.getPassword()))
                .orElseThrow(UserNotFoundException::new);
        return responseToken(user.getId());
    }

    @Override
    public TokenResponse refreshToken(String refreshToken) {
        Integer id = JwtTokenUtil.parseRefreshToken(refreshToken);
        return responseToken(id);
    }

    private TokenResponse responseToken(Integer id) {
        return TokenResponse.builder()
                .accessToken(JwtTokenUtil.generateAccessToken(id))
                .refreshToken(JwtTokenUtil.generateRefreshToken(id))
                .build();
    }
}
