package com.kenya.jug.arena.service;

import com.kenya.jug.arena.io.UserRequest;
import com.kenya.jug.arena.io.UserResponse;

public interface UserService {

    UserResponse createUser(UserRequest request);

    UserResponse getUser(String email);
}
