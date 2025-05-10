package com.kenya.jug.arena.service;

import com.kenya.jug.arena.exception.AlreadyExistsException;
import com.kenya.jug.arena.io.UserRequest;
import com.kenya.jug.arena.io.UserResponse;
import com.kenya.jug.arena.model.UserEntity;
import com.kenya.jug.arena.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateNewUserWhenEmailDoesNotExist(){
        UserRequest request = new UserRequest(
                "John",
                "Doe",
                "testuser@email.com",
                "secret"
                );

        when(userRepository.existsByEmail("john@example.com")).thenReturn(false);
        when(passwordEncoder.encode("secret")).thenReturn("encodedPassword");

        UserEntity savedUser = UserEntity.builder()
                .id(1L)
                .userId(UUID.randomUUID().toString())
                .email("testuser@email.com")
                .firstName("John")
                .lastName("Doe")
                .password("encodedPassword")
                .build();
        savedUser.setId(1L);

        when(userRepository.save(any(UserEntity.class))).thenReturn(savedUser);

        UserResponse response = userService.createUser(request);

        assertEquals(request.getFirstName(), response.getFirstName());
        assertEquals(request.getLastName(), response.getLastName());
        assertEquals(request.getEmail(), response.getEmail());
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        // Arrange
        UserRequest request = new UserRequest("Jane", "Doe", "jane@example.com", "password");
        when(userRepository.existsByEmail("jane@example.com")).thenReturn(true);

        // Act & Assert
        assertThrows(AlreadyExistsException.class, () -> userService.createUser(request));
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    void shouldReturnUserWhenEmailExists() {
        // Arrange
        String email = "john@example.com";
        UserEntity entity = UserEntity.builder()
                .userId(UUID.randomUUID().toString())
                .firstName("John")
                .lastName("Doe")
                .email(email)
                .password("secret")
                .build();

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(entity));

        // Act
        UserResponse response = userService.getUser(email);

        // Assert
        assertEquals("John", response.getFirstName());
        assertEquals("Doe", response.getLastName());
        assertEquals(email, response.getEmail());
    }

    @Test
    void shouldThrowWhenUserNotFound() {
        // Arrange
        String email = "ghost@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> userService.getUser(email));
    }
}
