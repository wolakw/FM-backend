package com.example.FootballManager.repository;

import com.example.FootballManager.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
