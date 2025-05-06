package com.kenya.jug.arena.repository;

import com.kenya.jug.arena.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
}
