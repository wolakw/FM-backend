package com.example.FootballManager.controller;


import com.example.FootballManager.exception.MatchNotFoundException;
import com.example.FootballManager.model.Match;
import com.example.FootballManager.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class MatchController {

    @Autowired
    private MatchRepository matchRepository;

    @PostMapping("/match")
    Match newMatch(@RequestBody Match newMatch){
        return matchRepository.save(newMatch);
    }
    @GetMapping("/matches")
    List<Match> getAllMatches(){
        return matchRepository.findAll();
    }

    @GetMapping("/match/{id}")
    Match getMatchById(@PathVariable Long id) {
        return matchRepository.findById(id)
                .orElseThrow(()->new MatchNotFoundException(id));
    }
}
