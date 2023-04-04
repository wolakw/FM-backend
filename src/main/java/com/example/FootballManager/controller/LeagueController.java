package com.example.FootballManager.controller;

import com.example.FootballManager.model.League;
import com.example.FootballManager.repository.LeagueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class LeagueController {
    @Autowired
    private LeagueRepository leagueRepository;

    @PostMapping("/league")
    League newLeague(@RequestBody League newLeague) {
        return leagueRepository.save(newLeague);
    }

    @GetMapping("/leagues")
    List<League> getAllLeagues() {
        return leagueRepository.findAll();
    }
}