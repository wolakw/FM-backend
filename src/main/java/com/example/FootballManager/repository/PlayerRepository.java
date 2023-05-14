package com.example.FootballManager.repository;

import com.example.FootballManager.model.League;
import com.example.FootballManager.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
}
