package com.example.FootballManager.controller;

import com.example.FootballManager.exception.UserNotFoundException;
import com.example.FootballManager.model.League;
import com.example.FootballManager.model.Player;
import com.example.FootballManager.model.User;
import com.example.FootballManager.repository.LeagueRepository;
import com.example.FootballManager.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class PlayerController {
    @Autowired
    private PlayerRepository playerRepository;

    @PostMapping("/player")
    Player newPlayer(@RequestBody Player newPlayer){
        return playerRepository.save(newPlayer);
    }
    @GetMapping("/players")
    List<Player> getAllUsers(){
        return playerRepository.findAll();
    }

    @PutMapping("/playerstat/{id}")
    Player updateUser(@RequestBody Player newPlayer,@PathVariable Long id) throws Exception {
        return playerRepository.findById(id).map(player -> {
            player.setPace(newPlayer.getPace());
            player.setDefending(newPlayer.getDefending());
            player.setShooting(newPlayer.getShooting());
            player.setPassing(newPlayer.getPassing());
            player.setDribbling(newPlayer.getDribbling());
            player.setPhysicallity(newPlayer.getPhysicallity());
            return playerRepository.save(player);
        }).orElseThrow(()-> new Exception(id + ""));
    }

}