package com.example.FootballManager.controller;


import com.example.FootballManager.exception.GameNotFoundException;
import com.example.FootballManager.model.Club;
import com.example.FootballManager.model.Game;
import com.example.FootballManager.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@CrossOrigin("http://localhost:3000")
public class GameController {

    @Autowired
    private GameRepository gameRepository;

    @PostMapping("/game")
    Game newMatch(@RequestBody Game newGame){
        return gameRepository.save(newGame);
    }
    @GetMapping("/games")
    List<Game> getAllMatches(){
        return gameRepository.findAll();
    }

    @GetMapping("/game/{id}")
    Game getMatchById(@PathVariable Long id) {
        return gameRepository.findById(id)
                .orElseThrow(()->new GameNotFoundException(id));
    }

    @PutMapping("/game/{id}/simulate")
    public ResponseEntity<String> simulateGame(@PathVariable Long id) {
        Optional<Game> optionalGame = gameRepository.findById(id);
        if (optionalGame.isPresent()) {
            Game game = optionalGame.get();

            // Pobranie klubów z meczu
            Club club1 = game.getClub1();
            Club club2 = game.getClub2();

            // Obliczenie mocy klubów na podstawie oceny
            double powerClub1 = club1.getGrade() / 100.0; // Konwersja oceny klubu na moc (0.01-0.99)
            double powerClub2 = club2.getGrade() / 100.0; // Konwersja oceny klubu na moc (0.01-0.99)

            // Generowanie wyniku meczu
            int goalsClub1 = generateGoals(powerClub1); // Wywołanie metody generującej liczbę goli dla Club 1
            int goalsClub2 = generateGoals(powerClub2); // Wywołanie metody generującej liczbę goli dla Club 2

            // Generowanie statystyk meczu
            int shotsClub1 = generateShots(powerClub1); // Wywołanie metody generującej liczbę strzałów dla Club 1
            int shotsClub2 = generateShots(powerClub2); // Wywołanie metody generującej liczbę strzałów dla Club 2
            int possessionClub1 = generatePossession(powerClub1); // Wywołanie metody generującej posiadanie piłki dla Club 1
            int possessionClub2 = 100 - possessionClub1; // Posiadanie piłki dla Club 2 (100 - possessionClub1)
            int passesClub1 = generatePasses(powerClub1); // Wywołanie metody generującej liczbę podań dla Club 1
            int passesClub2 = generatePasses(powerClub2); // Wywołanie metody generującej liczbę podań dla Club 2

            // Aktualizacja wyniku i statystyk meczu
            game.setGoalsClub1(goalsClub1);
            game.setGoalsClub2(goalsClub2);
            game.setShotsClub1(shotsClub1);
            game.setShotsClub2(shotsClub2);
            game.setPossessionClub1(possessionClub1);
            game.setPossessionClub2(possessionClub2);
            game.setPassesClub1(passesClub1);
            game.setPassesClub2(passesClub2);
            gameRepository.save(game);

            return ResponseEntity.ok("Game has been simulated.");
        }
        return ResponseEntity.notFound().build();
    }

    private int generateGoals(double power) {
        // Wykorzystanie mocy klubu w generowaniu liczby goli
        int minGoals = (int) (power * 0); // Minimalna liczba goli (0)
        int maxGoals = (int) (power * 5); // Maksymalna liczba goli (power * 5)
        return generateRandomNumber(minGoals, maxGoals);
    }

    private int generateShots(double power) {
        // Wykorzystanie mocy klubu w generowaniu liczby strzałów
        int minShots = (int) (power * 5); // Minimalna liczba strzałów (power * 5)
        int maxShots = (int) (power * 15); // Maksymalna liczba strzałów (power * 15)
        return generateRandomNumber(minShots, maxShots);
    }

    private int generatePossession(double power) {
        // Wykorzystanie mocy klubu w generowaniu posiadania piłki
        int minPossession = (int) (power * 40); // Minimalne posiadanie piłki (power * 40%)
        int maxPossession = (int) (power * 60); // Maksymalne posiadanie piłki (power * 60%)
        return generateRandomNumber(minPossession, maxPossession);
    }

    private int generatePasses(double power) {
        // Wykorzystanie mocy klubu w generowaniu liczby podań
        int minPasses = (int) (power * 200); // Minimalna liczba podań (power * 200)
        int maxPasses = (int) (power * 400); // Maksymalna liczba podań (power * 400)
        return generateRandomNumber(minPasses, maxPasses);
    }

    private int generateRandomNumber(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

}
