package se2.model;

import java.util.ArrayList;
import java.util.Objects;

public class Player {

    private int score;
    private String name;
    private ArrayList<Integer> roundScores = new ArrayList<>(5);
    private int allRoundNumbers;

    public Player() {
        score = 0;
        name = "";
    }

    public Player(String name) {
        this.name = name;
        score = 0;
    }

    public Player(int score, String name) {
        this.score = score;
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Integer> getRoundScores() {
        return roundScores;
    }

    public void setRoundScores(ArrayList<Integer> roundScores) {
        this.roundScores = roundScores;
    }

    public void clearData(){
        score = 0;
        name = "";
        roundScores.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return score == player.score &&
                Objects.equals(name, player.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(score, name);
    }

    public int getRoundNumbers(){ return this.allRoundNumbers;}
    public void addOneRound(){ this.allRoundNumbers++;}

}
