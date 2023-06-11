package com.example.FootballManager.repository;

import com.example.FootballManager.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match, Long> {
}
