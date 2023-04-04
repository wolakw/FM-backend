package com.example.FootballManager.repository;

import com.example.FootballManager.model.League;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeagueRepository extends JpaRepository<League, Long> {
}
