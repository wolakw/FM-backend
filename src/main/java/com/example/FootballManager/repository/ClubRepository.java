package com.example.FootballManager.repository;

import com.example.FootballManager.model.Club;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubRepository extends JpaRepository<Club, Long> {
}
