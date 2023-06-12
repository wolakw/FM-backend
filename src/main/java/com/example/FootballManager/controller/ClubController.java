package com.example.FootballManager.controller;

import com.example.FootballManager.exception.ClubNotFoundException;
import com.example.FootballManager.exception.UserNotFoundException;
import com.example.FootballManager.model.Club;
import com.example.FootballManager.model.User;
import com.example.FootballManager.repository.ClubRepository;
import com.example.FootballManager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class ClubController {
    @Autowired
    private ClubRepository clubRepository;

    @PostMapping("/club")
    Club newCLub(@RequestBody Club newClub){
        return clubRepository.save(newClub);
    }
    @GetMapping("/clubs")
    List<Club> getAllClubs(){
        return clubRepository.findAll();
    }

    @GetMapping("/club/{id}")
    Club getClubById(@PathVariable Long id) {
        return clubRepository.findById(id)
                .orElseThrow(()->new ClubNotFoundException(id));
    }

    @PutMapping("/club/{id}")
    Club updateClub(@RequestBody Club newClub, @PathVariable Long id) {
        return clubRepository.findById(id)
                .map(club -> {
                    club.setName(newClub.getName());
                    club.setMatchesPlayed(newClub.getMatchesPlayed());
                    club.setMatchesWon(newClub.getMatchesWon());
                    club.setMatchesLost(newClub.getMatchesLost());
                    club.setMatchesDraw(newClub.getMatchesDraw());
                    club.setPoints(newClub.getPoints());
                    return clubRepository.save(club);
                }).orElseThrow(() -> new ClubNotFoundException(id));
    }

    @PutMapping("/clubup/{id}")
    Club upgradeClub(@PathVariable Long id) {
        return clubRepository.findById(id)
                .map(club -> {
                    int temp = (club.getGrade() < 99) ? club.getGrade() + 1 : 99;
                    club.setGrade(temp);
                    return clubRepository.save(club);
                }).orElseThrow(() -> new ClubNotFoundException(id));
    }
}
