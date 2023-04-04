package com.example.FootballManager.controller;

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
}
