package com.example.FootballManager.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date gameDate;
    @ManyToOne
    @JoinColumn(name = "club_1_id")
    private Club club1;
    @ManyToOne
    @JoinColumn(name = "club_2_id")
    private Club club2;
    private int goalsClub1;
    private int goalsClub2;
    private int shotsClub1;
    private int shotsClub2;
    private int possessionClub1;
    private int possessionClub2;
    private int passesClub1;
    private int passesClub2;

    public Date getGameDate() {
        return gameDate;
    }

    public void setGameDate(Date matchDate) {
        this.gameDate = matchDate;
    }

    public Club getClub1() {
        return club1;
    }

    public void setClub1(Club club1) {
        this.club1 = club1;
    }

    public Club getClub2() {
        return club2;
    }

    public void setClub2(Club club2) {
        this.club2 = club2;
    }

    public int getGoalsClub1() {
        return goalsClub1;
    }

    public void setGoalsClub1(int goalsClub1) {
        this.goalsClub1 = goalsClub1;
    }

    public int getGoalsClub2() {
        return goalsClub2;
    }

    public void setGoalsClub2(int goalsClub2) {
        this.goalsClub2 = goalsClub2;
    }

    public int getShotsClub1() {
        return shotsClub1;
    }

    public void setShotsClub1(int shotsClub1) {
        this.shotsClub1 = shotsClub1;
    }

    public int getShotsClub2() {
        return shotsClub2;
    }

    public void setShotsClub2(int shotsClub2) {
        this.shotsClub2 = shotsClub2;
    }

    public int getPossessionClub1() {
        return possessionClub1;
    }

    public void setPossessionClub1(int possessionClub1) {
        this.possessionClub1 = possessionClub1;
    }

    public int getPossessionClub2() {
        return possessionClub2;
    }

    public void setPossessionClub2(int possessionClub2) {
        this.possessionClub2 = possessionClub2;
    }

    public int getPassesClub1() {
        return passesClub1;
    }

    public void setPassesClub1(int passesClub1) {
        this.passesClub1 = passesClub1;
    }

    public int getPassesClub2() {
        return passesClub2;
    }

    public void setPassesClub2(int passesClub2) {
        this.passesClub2 = passesClub2;
    }
}
