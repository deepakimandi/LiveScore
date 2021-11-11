package com.example.livescore;

import java.util.ArrayList;
import java.util.List;

public class Scorecard {

    private String id;
    private String title;
    private String team1;
    private String team2;
    private String score1;
    private String score2;
    private String endTitle;
    private String status;
    private String toss;
    private String url;

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    private int win = 0;

    public Scorecard(String title, String team1, String team2, String score1, String score2, String endTitle, String status, String toss, String id, String url) {
        this.title = title;
        this.team1 = team1;
        this.team2 = team2;
        this.score1 = score1;
        this.score2 = score2;
        this.endTitle = endTitle;
        this.status = status;
        this.toss = toss;
        this.id = id;
        this.url = url;
        this.win = 0;
    }

    public Scorecard(String title, String team1, String team2, String score1, String score2, String endTitle, String status, String toss, String id, String url, int win) {
        this.title = title;
        this.team1 = team1;
        this.team2 = team2;
        this.score1 = score1;
        this.score2 = score2;
        this.endTitle = endTitle;
        this.status = status;
        this.toss = toss;
        this.id = id;
        this.url = url;
        this.win = win;
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

    public String getEndTitle() {
        return endTitle;
    }

    public void setEndTitle(String endTitle) {
        this.endTitle = endTitle;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToss() {
        return toss;
    }

    public void setToss(String toss) {
        this.toss = toss;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
