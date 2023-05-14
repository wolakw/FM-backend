package com.example.FootballManager.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Entity
public class Player {

    @Id
    @GeneratedValue
    private Long id;
    private String fname;
    private String lname;
    private int age;
    private int goals;
    private int assists;
    private int kit_number;
    private int height;
    private enum _position{
        Goalie,Defense,Attack
    }


    private _position position;
    private int weight;
    private int pace;
    private int shooting;
    private int passing;
    private int dribbling;
    private int defending;
    private int physicallity;
    private int club_id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getGoals() {
        return goals;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public int getKit_number() {
        return kit_number;
    }

    public void setKit_number(int kit_number) {
        this.kit_number = kit_number;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public _position getPosition() {
        return position;
    }

    public void setPosition(_position position) {
        this.position = position;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getPace() {
        return pace;
    }

    public void setPace(int pace) {
        this.pace = pace;
    }

    public int getShooting() {
        return shooting;
    }

    public void setShooting(int shooting) {
        this.shooting = shooting;
    }

    public int getPassing() {
        return passing;
    }

    public void setPassing(int passing) {
        this.passing = passing;
    }

    public int getDribbling() {
        return dribbling;
    }

    public void setDribbling(int dribbling) {
        this.dribbling = dribbling;
    }

    public int getDefending() {
        return defending;
    }

    public void setDefending(int defending) {
        this.defending = defending;
    }

    public int getPhysicallity() {
        return physicallity;
    }

    public void setPhysicallity(int physicallity) {
        this.physicallity = physicallity;
    }

    public int getClub_id() {
        return club_id;
    }

    public void setClub_id(int club_id) {
        this.club_id = club_id;
    }
}