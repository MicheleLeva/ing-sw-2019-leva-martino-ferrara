package model.player_package;

public class Score {

    private int score;

    public Score(){
        score = 0;
    }

    public void addScore(int i){
        score = score + i;

    }

    public int getScore(){
        return score;

    }

}
