package com.example.FootballManager.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;


@Entity
public class Club {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private int matchesPlayed;
    private int matchesWon;
    private int matchesDraw;
    private int matchesLost;
    private int points;
    @OneToOne(mappedBy = "club")
    private User user;

    public Club(Long id) {
        this.id = id;
    }

    public Club() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMatchesPlayed() {
        return matchesPlayed;
    }

    public void setMatchesPlayed(int matchesPlayed) {
        this.matchesPlayed = matchesPlayed;
    }

    public int getMatchesWon() {
        return matchesWon;
    }

    public void setMatchesWon(int matchesWon) {
        this.matchesWon = matchesWon;
    }

    public int getMatchesDraw() {
        return matchesDraw;
    }

    public void setMatchesDraw(int matchesDraw) {
        this.matchesDraw = matchesDraw;
    }

    public int getMatchesLost() {
        return matchesLost;
    }

    public void setMatchesLost(int matchesLost) {
        this.matchesLost = matchesLost;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
