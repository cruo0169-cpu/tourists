package com.tourist.service.service;

import com.tourist.service.domain.Role;
import com.tourist.service.domain.User;
import com.tourist.service.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User register(String username, String password, String name, String phone) {
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("用户名已存在");
        }
        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .name(name)
                .phone(phone)
                .role(Role.TOURIST)
                .build();
        return userRepository.save(user);
    }

    public List<User> listByRole(Role role) {
        return userRepository.findByRole(role);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public User getById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}
