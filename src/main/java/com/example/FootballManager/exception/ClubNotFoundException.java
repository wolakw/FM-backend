package com.example.FootballManager.exception;

public class ClubNotFoundException extends RuntimeException {
    public ClubNotFoundException(Long id) { super("Could not found the club with id " + id);}
}
