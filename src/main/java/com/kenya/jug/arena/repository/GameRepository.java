package com.kenya.jug.arena.repository;

import com.kenya.jug.arena.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
