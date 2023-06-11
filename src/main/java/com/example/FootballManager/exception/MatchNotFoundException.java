package com.example.FootballManager.exception;

public class MatchNotFoundException extends RuntimeException{
    public MatchNotFoundException(Long id) {
        super("Could not found the match with id " + id);
    }
}
