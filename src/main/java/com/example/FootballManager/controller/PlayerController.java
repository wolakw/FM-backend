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

    @PutMapping("/playerstat/{id}/{type}")
    Player updateUser(@PathVariable Long id,@PathVariable String type) throws Exception {

        if (type.equals("pace")) {
            return playerRepository.findById(id).map(player -> {
                player.setPace(player.getPace() + 1);
                return playerRepository.save(player);
            }).orElseThrow(() -> new Exception(id + ""));
        }
        else if(type.equals("shooting")){
            return playerRepository.findById(id).map(player -> {
                player.setShooting(player.getShooting() + 1);
                return playerRepository.save(player);
            }).orElseThrow(() -> new Exception(id + ""));
        }
        else if(type.equals("passing")){
            return playerRepository.findById(id).map(player -> {
                player.setPassing(player.getPassing() + 1);
                return playerRepository.save(player);
            }).orElseThrow(() -> new Exception(id + ""));
        }
        else if(type.equals("dribbling")){
            return playerRepository.findById(id).map(player -> {
                player.setDribbling(player.getDribbling() + 1);
                return playerRepository.save(player);
            }).orElseThrow(() -> new Exception(id + ""));
        }
        else if(type.equals("defending")){
            return playerRepository.findById(id).map(player -> {
                player.setDefending(player.getDefending() + 1);
                return playerRepository.save(player);
            }).orElseThrow(() -> new Exception(id + ""));
        }
        else if(type.equals("physicality")){
            return playerRepository.findById(id).map(player -> {
                player.setPhysicallity(player.getPhysicallity() + 1);
                return playerRepository.save(player);
            }).orElseThrow(() -> new Exception(id + ""));
        }
        return null;
    }

}