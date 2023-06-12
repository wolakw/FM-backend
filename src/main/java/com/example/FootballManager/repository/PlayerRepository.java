package com.example.FootballManager.repository;

import com.example.FootballManager.model.Club;
import com.example.FootballManager.model.League;
import com.example.FootballManager.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    List<Player> findByClub(Club club);
}
