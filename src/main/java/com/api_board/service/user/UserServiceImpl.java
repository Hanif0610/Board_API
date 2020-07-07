package com.api_board.service.user;

import com.api_board.domain.entity.User;
import com.api_board.domain.payload.request.ChargePassword;
import com.api_board.domain.payload.request.SignUp;
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
    public void signUp(SignUp signUp) {

        if(userRepository.findByEmail(signUp.getEmail()).isPresent()) throw new UserAlreadyExistsException();

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

    @Override
    public void chargePassword(String token, ChargePassword chargePassword) {
        User user = userRepository.findById(JwtTokenUtil.parseAccessToken(token)).orElseThrow(UserNotFoundException::new);

        if (!isSamePassword(chargePassword.getPassword(), user.getPassword())) {
            throw new PasswordSameException();
        }

        user.setPassword(passwordEncoder.encode(chargePassword.getPassword()));
        userRepository.save(user);
    }

    private boolean isSamePassword(String password, String encodedPassword) {
        return passwordEncoder.matches(password, encodedPassword);
    }
}
