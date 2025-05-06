package com.kenya.jug.arena.repository;

import com.kenya.jug.arena.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepoitory extends JpaRepository<User, Long> {
}
