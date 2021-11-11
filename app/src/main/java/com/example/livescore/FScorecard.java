package com.example.livescore;

import java.util.ArrayList;
import java.util.List;

public class FScorecard {

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    private String sport;
    private String title;
    private String team1;
    private String team2;
    private String score1;
    private String score2;
    private String time;
    private String fendId;
    private List<String> homeTeamGoals = new ArrayList<String>();
    private List<String> awayTeamGoals = new ArrayList<String>();

    public FScorecard(String sport, String title, String team1, String team2, String score1, String score2, String time) {
        this.title = title;
        this.team1 = team1;
        this.team2 = team2;
        this.score1 = score1;
        this.score2 = score2;
        this.time = time;
        this.sport = sport;
    }
    public FScorecard(String sport, String title, String team1, String team2, String score1, String score2, String time, String fendId) {
        this.title = title;
        this.team1 = team1;
        this.team2 = team2;
        this.score1 = score1;
        this.score2 = score2;
        this.time = time;
        this.fendId = fendId;
        this.sport = sport;
    }

    public FScorecard(String sport, String title, String team1, String team2, String score1, String score2, String time, List<String> homeTeamGoals, List<String> awayTeamGoals) {
        this.title = title;
        this.team1 = team1;
        this.team2 = team2;
        this.score1 = score1;
        this.score2 = score2;
        this.time = time;
        this.sport = sport;
        this.homeTeamGoals = homeTeamGoals;
        this.awayTeamGoals = awayTeamGoals;
    }
    public FScorecard(String sport, String title, String team1, String team2, String score1, String score2, String time, String fendId, List<String> homeTeamGoals, List<String> awayTeamGoals) {
        this.title = title;
        this.team1 = team1;
        this.team2 = team2;
        this.score1 = score1;
        this.score2 = score2;
        this.time = time;
        this.fendId = fendId;
        this.sport = sport;
        this.awayTeamGoals = awayTeamGoals;
        this.homeTeamGoals = homeTeamGoals;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public String getScore1() {
        return score1;
    }

    public void setScore1(String score1) {
        this.score1 = score1;
    }

    public String getScore2() {
        return score2;
    }

    public void setScore2(String score2) {
        this.score2 = score2;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFendId() {
        return fendId;
    }

    public void setFendId(String time) {
        this.fendId = fendId;
    }

    public List<String> getHomeTeamGoals() {
        return homeTeamGoals;
    }

    public void setHomeTeamGoals(List<String> homeTeamGoals) {
        this.homeTeamGoals = homeTeamGoals;
    }

    public List<String> getAwayTeamGoals() {
        return awayTeamGoals;
    }

    public void setAwayTeamGoals(List<String> awayTeamGoals) {
        this.awayTeamGoals = awayTeamGoals;
    }

}
