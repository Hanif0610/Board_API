package com.api_board.service.user;

import com.api_board.domain.entity.User;
import com.api_board.domain.payload.request.SignUp;
import com.api_board.domain.repository.UserRepository;
import com.api_board.exception.UserNotFoundException;
import com.api_board.util.JwtTokenUtil;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void signUp(SignUp signUp) {

        String email = signUp.getEmail();
        String name = signUp.getName();
        String password = passwordEncoder.encode(signUp.getPassword());

        userRepository.save(
                User.builder()
                    .email(email)
                    .password(password)
                    .name(name)
                    .build()
        );
    }
}
