package com.example.FootballManager.controller;


import com.example.FootballManager.exception.GameNotFoundException;
import com.example.FootballManager.model.Club;
import com.example.FootballManager.model.Game;
import com.example.FootballManager.repository.ClubRepository;
import com.example.FootballManager.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@CrossOrigin("http://localhost:3000")
public class GameController {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private ClubRepository clubRepository;

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

            // Aktualizacja statystyk klubów
            club1.setMatchesPlayed(club1.getMatchesPlayed() + 1);
            club2.setMatchesPlayed(club2.getMatchesPlayed() + 1);

            if (goalsClub1 > goalsClub2) {
                // Club 1 wygrał
                club1.setMatchesWon(club1.getMatchesWon() + 1);
                club1.setPoints(club1.getPoints() + 3);
                club2.setMatchesLost(club2.getMatchesLost() + 1);
                club1.setBudget(club1.getBudget() + 1000000);
                club2.setBudget(club2.getBudget() + 250000);
            } else if (goalsClub1 < goalsClub2) {
                // Club 2 wygrał
                club2.setMatchesWon(club2.getMatchesWon() + 1);
                club2.setPoints(club2.getPoints() + 3);
                club1.setMatchesLost(club1.getMatchesLost() + 1);
                club1.setBudget(club1.getBudget() + 250000);
                club2.setBudget(club2.getBudget() + 1000000);
            } else {
                // Remis
                club1.setMatchesDraw(club1.getMatchesDraw() + 1);
                club2.setMatchesDraw(club2.getMatchesDraw() + 1);
                club1.setPoints(club1.getPoints() + 1);
                club2.setPoints(club2.getPoints() + 1);
                club1.setBudget(club1.getBudget() + 500000);
                club2.setBudget(club2.getBudget() + 500000);
            }

            clubRepository.save(club1);
            clubRepository.save(club2);

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
        int minPossession = (int) (power * 30); // Minimalne posiadanie piłki (power * 40%)
        int maxPossession = (int) (power * 70); // Maksymalne posiadanie piłki (power * 60%)
        return generateRandomNumber(minPossession, maxPossession);
    }

    private int generatePasses(double power) {
        // Wykorzystanie mocy klubu w generowaniu liczby podań
        int minPasses = (int) (power * 200); // Minimalna liczba podań (power * 200)
        int maxPasses = (int) (power * 500); // Maksymalna liczba podań (power * 400)
        return generateRandomNumber(minPasses, maxPasses);
    }

    private int generateRandomNumber(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    @GetMapping("/generate-matches")
    public ResponseEntity<String> generateMatches() {
        List<Club> clubs = clubRepository.findAll();

        if (clubs.size() < 2) {
            return ResponseEntity.badRequest().body("Potrzeba co najmniej dwóch klubów do utworzenia meczów.");
        }

        int totalClubs = clubs.size();
        int matchesPerTeam = totalClubs - 1;  // Liczba meczów na jedną drużynę

        LocalDate currentDate = LocalDate.now();
        LocalDate nextMatchDate = currentDate.plusWeeks(matchesPerTeam);

        for (int i = 0; i < totalClubs; i++) {
            Club club1 = clubs.get(i);

            for (int j = i + 1; j < totalClubs; j++) {
                Club club2 = clubs.get(j);

                // Tworzenie dwóch meczów pomiędzy dwiema drużynami
                createMatch(club1, club2, currentDate);

                // Przesuń datę następnego meczu o kolejny tydzień
                currentDate = currentDate.plusDays(7);
            }
        }

        for (int i = 0; i < totalClubs; i++) {
            Club club1 = clubs.get(i);

            for (int j = i + 1; j < totalClubs; j++) {
                Club club2 = clubs.get(j);

                // Tworzenie dwóch meczów pomiędzy dwiema drużynami
                createMatch(club2, club1, nextMatchDate);

                // Przesuń datę następnego meczu o kolejny tydzień
                nextMatchDate = nextMatchDate.plusDays(7);
            }
        }

        return ResponseEntity.ok("Mecze zostały wygenerowane.");
    }

    private void createMatch(Club club1, Club club2, LocalDate matchDate) {
        Game game = new Game();
        game.setGameDate(Date.from(matchDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        game.setClub1(club1);
        game.setClub2(club2);
        // Ustaw pozostałe parametry meczu

        gameRepository.save(game);
    }

    @GetMapping("/generate-games")
    public ResponseEntity<String> generateGames() {
        List<Club> clubs = clubRepository.findAll();
        int totalClubs = clubs.size();
        int rounds = totalClubs - 1; // Ilość rund

        // Pętla generująca gry dla każdej rundy
        for (int round = 0; round < rounds; round++) {
            for (int i = 0; i < totalClubs; i++) {
                int homeClubIndex = (round + i) % (totalClubs - 1);
                int awayClubIndex = (totalClubs - 1 - i + round) % (totalClubs - 1);

                // Pominięcie gry z samym sobą
                if (awayClubIndex == homeClubIndex) {
                    awayClubIndex = totalClubs - 1;
                }

                Club homeClub = clubs.get(homeClubIndex);
                Club awayClub = clubs.get(awayClubIndex);

                Game game = new Game();
                game.setClub1(homeClub);
                game.setClub2(awayClub);
                // Ustawienie daty gry co tydzień
                Date gameDate = Date.from(LocalDate.now().plusDays((round * totalClubs) + i).atStartOfDay(ZoneId.systemDefault()).toInstant());
                game.setGameDate(gameDate);

                gameRepository.save(game);
            }
        }

        return ResponseEntity.ok("Gry zostały wygenerowane.");
    }

    @GetMapping("/generate")
    public ResponseEntity<String> generate() {
        List<Club> clubs = clubRepository.findAll(); // Pobierz wszystkie drużyny z repozytorium

        int totalClubs = clubs.size();
        Date startDate = new Date(); // Ustaw startową datę jako bieżącą datę

        for (int i = 0; i < totalClubs - 1; i++) {
            Club homeClub = clubs.get(i);

            for (int j = i + 1; j < totalClubs; j++) {
                Club awayClub = clubs.get(j);

                Game game = new Game();
                game.setClub1(homeClub);
                game.setClub2(awayClub);
                game.setGameDate(startDate);

                gameRepository.save(game);

                startDate = addDays(startDate, 1); // Dodaj 7 dni do daty dla kolejnej kolejki
            }
        }

        return ResponseEntity.ok("Mecze zostały wygenerowane.");
    }

    private Date addDays(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, days);
        return calendar.getTime();
    }

    @PutMapping("/games/simulate")
    public ResponseEntity<String> simulateAllGames() {
        List<Game> games = gameRepository.findAll();

        for (Game game : games) {
            simulateGame(game.getId());
        }

        return ResponseEntity.ok("All games have been simulated.");
    }

    @PutMapping("/clubs/reset")
    public ResponseEntity<String> resetClubs() {
        List<Club> clubs = clubRepository.findAll();

        for (Club club : clubs) {
            club.setMatchesPlayed(0);
            club.setMatchesWon(0);
            club.setMatchesDraw(0);
            club.setMatchesLost(0);
            club.setPoints(0);
        }

        clubRepository.saveAll(clubs);

        return ResponseEntity.ok("All clubs have been reset.");
    }
}
