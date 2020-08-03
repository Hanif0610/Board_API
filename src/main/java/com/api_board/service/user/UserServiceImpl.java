package com.api_board.service.user;

import com.api_board.domain.entity.User;
import com.api_board.domain.payload.request.ChargePasswordRequest;
import com.api_board.domain.payload.request.SignUpRequest;
import com.api_board.domain.repository.UserRepository;
import com.api_board.exception.UserAlreadyExistsException;
import com.api_board.exception.UserNotFoundException;
import com.api_board.exception.PasswordSameException;
import com.api_board.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void signUp(SignUpRequest signUpRequest) {

        if(userRepository.findByEmail(signUpRequest.getEmail()).isPresent()) throw new UserAlreadyExistsException();

        String email = signUpRequest.getEmail();
        String name = signUpRequest.getName();
        String password = passwordEncoder.encode(signUpRequest.getPassword());

        userRepository.save(
                User.builder()
                    .email(email)
                    .password(password)
                    .name(name)
                    .build()
        );
    }

    @Override
    public void chargePassword(String token, ChargePasswordRequest chargePasswordRequest) {
        User user = userRepository.findById(JwtTokenUtil.parseAccessToken(token)).orElseThrow(UserNotFoundException::new);

        if (isSamePassword(chargePasswordRequest.getPassword(), user.getPassword())) {
            throw new PasswordSameException();
        }

        user.setPassword(passwordEncoder.encode(chargePasswordRequest.getPassword()));
        userRepository.save(user);
    }

    private boolean isSamePassword(String password, String encodedPassword) {
        return passwordEncoder.matches(password, encodedPassword);
    }
}
