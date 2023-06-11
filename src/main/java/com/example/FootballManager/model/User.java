package com.example.FootballManager.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String name;
    private String email;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "club_id")
    private Club club;
    private Date currDate;

    @JsonProperty("club") // Dopasuj nazwę pola do nazwy w JSON-ie
    public Club getClub() {
        return club;
    }

    @JsonProperty("club") // Dopasuj nazwę pola do nazwy w JSON-ie
    public void setClub(Club club) {
        this.club = club;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCurrDate() {
        return currDate;
    }

    public void setCurrDate(Date currDate) {
        this.currDate = currDate;
    }
}
