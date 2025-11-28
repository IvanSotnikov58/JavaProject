package com.example.hearthclone.Service;

import com.example.hearthclone.Repository.UserRepository;
import com.example.hearthclone.model.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Registacia
    public User register(User user) {

        if (userRepository.existsByName(user.getName())) {
            throw new RuntimeException("Пользователь с таким именем уже существует!");
        }

        return userRepository.save(user);
    }

    // Login
    public User login(String name, String password) {

        User existing = userRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден!"));

        if (!existing.getPassword().equals(password)) {
            throw new RuntimeException("Неверный пароль!");
        }

        return existing;
    }
}
////