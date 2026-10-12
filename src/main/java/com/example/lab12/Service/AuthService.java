package com.example.lab12.Service;

import com.example.lab12.API.ApiException;
import com.example.lab12.Model.User;
import com.example.lab12.Repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;


    public List<User> getAllUsers(){
        return authRepository.findAll();
    }

    public void register(User user) {
        if (authRepository.findUserByUsername(user.getUsername()) != null) {
            throw new ApiException("Username already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");
        authRepository.save(user);
    }

    public void updateUser(Integer userId, User user) {
        User oldUser = authRepository.findUserById(userId);
        if (oldUser == null) {
            throw new ApiException("User not found");
        }
        User sameName = authRepository.findUserByUsername(user.getUsername());
        if (sameName != null && !sameName.getId().equals(userId)) {
            throw new ApiException("Username already exists");
        }
        oldUser.setUsername(user.getUsername());
        oldUser.setPassword(passwordEncoder.encode(user.getPassword()));
        authRepository.save(oldUser);
    }

    public void deleteUser(Integer userId) {
        User user = authRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }
        authRepository.delete(user);
    }
}
