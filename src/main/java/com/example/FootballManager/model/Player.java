package com.example.FootballManager.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Player {
    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;
    private int speed;
    private int shooting;
    private int defending;
    private int passing;
    private int price;
    private boolean isTaken;
    private boolean isFirstXI;
//    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "club_id")
    @JsonBackReference // dodana adnotacja
    private Club club;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastNameame) {
        this.lastName = lastNameame;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getShooting() {
        return shooting;
    }

    public void setShooting(int shooting) {
        this.shooting = shooting;
    }

    public int getDefending() {
        return defending;
    }

    public void setDefending(int defending) {
        this.defending = defending;
    }

    public int getPassing() {
        return passing;
    }

    public void setPassing(int passing) {
        this.passing = passing;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isTaken() {
        return isTaken;
    }

    public void setTaken(boolean taken) {
        isTaken = taken;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public boolean isFirstXI() {
        return isFirstXI;
    }

    public void setFirstXI(boolean firstXI) {
        isFirstXI = firstXI;
    }
}
