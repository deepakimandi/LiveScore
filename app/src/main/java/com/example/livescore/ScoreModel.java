package com.example.livescore;

public class ScoreModel {

    private String batter;
    private String out;
    private String r;
    private String b;
    private String _4s;
    private String _6s;
    private String sr;


    public ScoreModel(String batter, String out, String r, String b, String _4s, String _6s, String sr) {
        this.batter = batter;
        this.r = r;
        this.b = b;
        this._4s = _4s;
        this._6s = _6s;
        this.sr = sr;
        this.out = out;
    }

    public ScoreModel(String bowler, String o, String m, String r, String w, String eco) {
        this.batter = bowler;
        this.r = o;
        this.b = m;
        this._4s = r;
        this._6s = w;
        this.sr = eco;
    }

    public String getBatter() {
        return batter;
    }

    public void setBatter(String batter) {
        this.batter = batter;
    }

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String get_4s() {
        return _4s;
    }

    public void set_4s(String _4s) {
        this._4s = _4s;
    }

    public String get_6s() {
        return _6s;
    }

    public void set_6s(String _6s) {
        this._6s = _6s;
    }

    public String getSr() {
        return sr;
    }

    public void setSr(String sr) {
        this.sr = sr;
    }

    public String getOut() {
        return out;
    }

    public void setOut(String out) {
        this.out = out;
    }

}
