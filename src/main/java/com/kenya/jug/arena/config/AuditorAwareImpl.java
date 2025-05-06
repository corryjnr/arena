package com.kenya.jug.arena.config;

import com.kenya.jug.arena.model.UserEntity;
import com.kenya.jug.arena.repository.UserRepository;
import com.kenya.jug.arena.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

@RequiredArgsConstructor
public class AuditorAwareImpl implements AuditorAware<Long> {
    private final UserRepository userRepository;

    @Override
    public Optional<Long> getCurrentAuditor() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .filter(Authentication::isAuthenticated)
                .map(auth -> {
                    Object principal = auth.getPrincipal();
                    if (principal instanceof UserDetails userDetails) {
                        var email = userDetails.getUsername();
                        UserEntity user = userRepository.findByEmail(email)
                                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
                        return user.getId(); // Assuming your user object has an ID
                    }
                    return null;
                });
    }
}
