package com.example.FootballManager.controller;

import com.example.FootballManager.exception.PlayerNotFoundException;
import com.example.FootballManager.exception.UserNotFoundException;
import com.example.FootballManager.model.Club;
import com.example.FootballManager.model.Player;
import com.example.FootballManager.model.User;
import com.example.FootballManager.repository.PlayerRepository;
import com.example.FootballManager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:3000")
public class PlayerController {
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/player")
    Player newPlayer(@RequestBody Player newPlayer) {
        return playerRepository.save(newPlayer);
    }

    @GetMapping("/players")
    List<Player> getAllPlayers(){
        return playerRepository.findAll();
    }

    @GetMapping("/player/{id}")
    Player getPlayerById(@PathVariable Long id) {
        return playerRepository.findById(id)
                .orElseThrow(()->new PlayerNotFoundException(id));
    }

    @PutMapping("/players/{id}/buy")
    public ResponseEntity<String> buyPlayer(@PathVariable Long id) {
        Optional<Player> optionalPlayer = playerRepository.findById(id);
        if (optionalPlayer.isPresent()) {
            Player player = optionalPlayer.get();
            if (!player.isTaken()) {
                User user = userRepository.findById(1L).orElse(null); // Pobierz użytkownika o ID 1 (założenie)
                if (user != null) {
                    Club club = user.getClub();
                    if (player.getPrice() <= club.getBudget()) {
                        player.setTaken(true);
                        club.setBudget(club.getBudget() - player.getPrice());
                        club.getPlayers().add(player);
                        player.setClub(club); // Przypisanie klubu do zawodnika
                        playerRepository.save(player);
                        userRepository.save(user);
                        return ResponseEntity.ok("Player has been bought.");
                    } else {
                        return ResponseEntity.badRequest().body("Not enough budget to buy this player.");
                    }
                }
            }
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/players/{id}/sell")
    public ResponseEntity<String> sellPlayer(@PathVariable Long id) {
        Optional<Player> optionalPlayer = playerRepository.findById(id);
        if (optionalPlayer.isPresent()) {
            Player player = optionalPlayer.get();
            if (player.isTaken()) {
                User user = userRepository.findById(1L).orElse(null); // Pobierz użytkownika o ID 1 (założenie)
                if (user != null) {
                    Club club = user.getClub();
                    player.setTaken(false);
                    player.setFirstXI(false);
                    club.setBudget(club.getBudget() + (int) (player.getPrice() * 0.8));
                    club.getPlayers().remove(player);
                    player.setClub(null);
                    playerRepository.save(player);
                    userRepository.save(user);
                    return ResponseEntity.ok("Player has been sold.");
                }
            }
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/players/{id}/remove-from-xi")
    public ResponseEntity<String> removePlayerFromXI(@PathVariable Long id) {
        Optional<Player> optionalPlayer = playerRepository.findById(id);
        if (optionalPlayer.isPresent()) {
            Player player = optionalPlayer.get();
            if (player.isFirstXI()) {
                player.setFirstXI(false);
                playerRepository.save(player);
                return ResponseEntity.ok("Player has been removed from the first XI.");
            }
            return ResponseEntity.badRequest().body("Player is not in the first XI.");
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/player/{id}/moveToXI")
    public ResponseEntity<String> movePlayerToXI(@PathVariable Long id) {
        Optional<Player> optionalPlayer = playerRepository.findById(id);
        if (optionalPlayer.isPresent()) {
            Player player = optionalPlayer.get();
            player.setFirstXI(true);
            playerRepository.save(player);
            return ResponseEntity.ok("Player moved to XI successfully.");
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/playerstat/{id}/{type}")
    Player TrainPlayer(@PathVariable Long id,@PathVariable String type) throws Exception {

        if(type.equals("shooting")){
            return playerRepository.findById(id).map(player -> {
                int temp = (player.getShooting() < 99) ? player.getShooting() + 1 : 99;
                player.setShooting(temp);
                return playerRepository.save(player);
            }).orElseThrow(() -> new Exception(id + ""));
        }
        else if(type.equals("passing")){
            return playerRepository.findById(id).map(player -> {
                int temp = (player.getPassing() < 99) ? player.getPassing() + 1 : 99;
                player.setPassing(temp);
                return playerRepository.save(player);
            }).orElseThrow(() -> new Exception(id + ""));
        }
        else if(type.equals("defending")){
            return playerRepository.findById(id).map(player -> {
                int temp = (player.getDefending() < 99) ? player.getDefending() + 1 : 99;
                player.setDefending(temp);
                return playerRepository.save(player);
            }).orElseThrow(() -> new Exception(id + ""));
        }
        else if(type.equals("speed")){
            return playerRepository.findById(id).map(player -> {
                int temp = (player.getSpeed() < 99) ? player.getSpeed() + 1 : 99;
                player.setSpeed(temp);
                return playerRepository.save(player);
            }).orElseThrow(() -> new Exception(id + ""));
        }
        return null;
    }

}


