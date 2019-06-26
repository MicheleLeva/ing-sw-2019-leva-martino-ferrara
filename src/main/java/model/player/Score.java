package model.player;

/**
 * Represents a player's score
 */
public class Score {

    private int score;

    private int numKillShot;

    /**
     * Constructor for the Score class
     */
    public Score(){
        score = 0;
    }

    public void addScore(int i){
        score = score + i;

    }

    public int getScore(){
        return score;

    }

    //todo servono ancora?
    /*public void addNumKillShot(){
        numKillShot++;
    }

    public int getNumKillShot(){
        return numKillShot;
    }*/

}
