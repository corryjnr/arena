package com.kenya.jug.arena.service;

import com.kenya.jug.arena.io.UserRequest;
import com.kenya.jug.arena.io.UserResponse;
import com.kenya.jug.arena.model.UserEntity;
import com.kenya.jug.arena.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse createUser(UserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }

        UserEntity newUser = convertToUserEntity(request);
        newUser = userRepository.save(newUser);
        return convertToUserResponse(newUser);
    }

    @Override
    public UserResponse getUser(String email) {
        UserEntity existingUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        return convertToUserResponse(existingUser);
    }

    private UserResponse convertToUserResponse(UserEntity newProfile) {
        return UserResponse.builder()
                .userId(newProfile.getUserId())
                .firstName(newProfile.getFirstName())
                .lastName(newProfile.getLastName())
                .email(newProfile.getEmail())
                .build();
    }

    private UserEntity convertToUserEntity(UserRequest request) {
        return UserEntity.builder()
                .email(request.getEmail())
                .userId(UUID.randomUUID().toString())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
    }
}
