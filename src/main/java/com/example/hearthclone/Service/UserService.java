package com.example.hearthclone.Service;

import com.example.hearthclone.model.User;
import com.example.hearthclone.Repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }
    public List<User> getallUsers() {
        return userRepository.findAll();


    }
    public void deleteUser(Long id){
        userRepository.deleteById(id);

    }
    public User addUser(User user){
        return userRepository.save(user);
    }
 }


