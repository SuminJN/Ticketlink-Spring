package com.hello.ticketlink.user.service;

import com.hello.ticketlink.user.repository.UserRepository;
import com.hello.ticketlink.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User create(String username, String email, String password) { // TODO DTO 처리 할 것
        User user = User.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .build();

        this.userRepository.save(user);

        return user;
    }

    public User getUser(String username) {
        return userRepository.findByusername(username).orElseThrow();
    }
}
