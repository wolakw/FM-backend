package com.example.FootballManager.controller;

import com.example.FootballManager.exception.UserNotFoundException;
import com.example.FootballManager.model.Club;
import com.example.FootballManager.model.League;
import com.example.FootballManager.model.Player;
import com.example.FootballManager.model.User;
import com.example.FootballManager.repository.ClubRepository;
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
    @Autowired
    private ClubRepository clubRepository;

    @PostMapping("/player")
    Player newPlayer(@RequestBody Player newPlayer){
        return playerRepository.save(newPlayer);
    }
    @GetMapping("/players")
    List<Player> getAllPlayers(){
        return playerRepository.findAll();
    }

    @GetMapping("/players/{club_id}")
    List<Player> getAllClubPlayers(@PathVariable Long id){
        Club club = clubRepository.findById(id).get();
        return playerRepository.findByClub(club);
    }

    @PutMapping("/playerstat/{id}/{type}")
    Player TrainPlayer(@PathVariable Long id,@PathVariable String type) throws Exception {

        if(playerRepository.findById(1L).get().getClub().getId() != 1){
            throw new Exception(id + "");
        }
        if(type.equals("shooting")){
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
        else if(type.equals("defending")){
            return playerRepository.findById(id).map(player -> {
                player.setDefending(player.getDefending() + 1);
                return playerRepository.save(player);
            }).orElseThrow(() -> new Exception(id + ""));
        }
        else if(type.equals("speed")){
            return playerRepository.findById(id).map(player -> {
                player.setSpeed(player.getSpeed() + 1);
                return playerRepository.save(player);
            }).orElseThrow(() -> new Exception(id + ""));
        }
        return null;
    }

}