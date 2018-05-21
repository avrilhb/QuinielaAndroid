package com.avrilhb.quinielaandroid.model;

/**
 * Created by anzendigital on 21/05/18.
 */

public class User {

    private String userName;
    private String team;
    private int points;
    private String email;

    public User(){

    }

    public User(String userName, String team, int points, String email) {
        this.userName = userName;
        this.team = team;
        this.points = points;
        this.email = email;
    }

    public User(String email, int points, String team, String userName) {
        this.userName = userName;
        this.team = team;
        this.points = points;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
