package com.kenya.jug.arena.controller;

import com.kenya.jug.arena.io.UserRequest;
import com.kenya.jug.arena.io.UserResponse;
import com.kenya.jug.arena.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@Valid @RequestBody UserRequest request) {
        return userService.createUser(request);
    }

    @GetMapping("/user")
    public UserResponse getUser(@CurrentSecurityContext(expression = "authentication?.name") String email) {
        return userService.getUser(email);
    }
}
