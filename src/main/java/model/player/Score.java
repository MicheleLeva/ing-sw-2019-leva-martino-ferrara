package model.player;

/**
 * Represents a player's score
 * @author Michele Leva, Stefano Martino, Marco Maria Ferrara
 */
public class Score {

    private int score;


    /**
     * Constructor for the Score class
     */
    public Score(){
        score = 0;
    }

    public void addScore(int i){
        score = score + i;

    }

    /**
     * Returns the player's score
     * @return the player's score
     */
    public int getScore(){
        return score;

    }
}
