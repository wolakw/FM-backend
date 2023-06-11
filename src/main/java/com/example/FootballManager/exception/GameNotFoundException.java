package com.example.FootballManager.exception;

public class GameNotFoundException extends RuntimeException{
    public GameNotFoundException(Long id) {
        super("Could not found the game with id " + id);
    }
}
