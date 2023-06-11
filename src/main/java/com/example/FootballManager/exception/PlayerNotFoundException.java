package com.example.FootballManager.exception;

public class PlayerNotFoundException extends RuntimeException {
    public PlayerNotFoundException(Long id) {
        super("Could not found the player with id " + id);
    }
}
