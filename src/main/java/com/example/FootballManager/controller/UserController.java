package com.example.FootballManager.controller;

import com.example.FootballManager.exception.ClubNotFoundException;
import com.example.FootballManager.exception.UserNotFoundException;
import com.example.FootballManager.model.Club;
import com.example.FootballManager.model.User;
import com.example.FootballManager.repository.ClubRepository;
import com.example.FootballManager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ClubRepository clubRepository;

    @PostMapping("/user")
    User newUser(@RequestBody User newUser){
        return userRepository.save(newUser);
    }
    @GetMapping("/users")
    List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/user/{id}")
    User getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
                .orElseThrow(()->new UserNotFoundException(id));
    }

//    @PutMapping("/user/{id}")
//    User updateUser(@RequestBody User newUser, @PathVariable Long id) {
//        return userRepository.findById(id)
//                .map(user -> {
//                    user.setUsername(newUser.getUsername());
//                    user.setName(newUser.getName());
//                    user.setEmail(newUser.getEmail());
//                    user.setClub(newUser.getClub());
//                    return userRepository.save(user);
//                }).orElseThrow(() -> new UserNotFoundException(id));
//    }

    @PutMapping("/user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody User updatedUser) {
        // Pobierz istniejącego użytkownika na podstawie identyfikatora
        User existingUser = userRepository.findById(id)
                .orElseThrow(()->new UserNotFoundException(id));

        // Pobierz klub na podstawie przekazanego identyfikatora
        Club club = clubRepository.findById(updatedUser.getClub().getId())
                .orElseThrow(()->new ClubNotFoundException(updatedUser.getClub().getId()));

        // Zaktualizuj dane użytkownika
        existingUser.setName(updatedUser.getName());
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setEmail(updatedUser.getEmail());

        // Przypisz klub do użytkownika
        existingUser.setClub(club);

        // Zapisz zaktualizowanego użytkownika w bazie danych
        User savedUser = userRepository.save(existingUser);

        return ResponseEntity.ok(savedUser);
    }

    @DeleteMapping("/user/{id}")
    String deleteUser(@PathVariable Long id) {
        if(!userRepository.existsById(id)) {
            throw  new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
        return "User with id " + id + " has been deleted.";
    }

    @GetMapping("/club/user/{id}")
    public ResponseEntity<Club> getUserClub(@PathVariable Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        if (user != null && user.getClub() != null) {
            Club club = user.getClub();
            return ResponseEntity.ok(club);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
