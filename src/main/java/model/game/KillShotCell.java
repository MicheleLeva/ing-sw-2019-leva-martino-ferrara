package model.game;

import model.player.PlayerColor;

public class KillShotCell {
    /**
     * KillShotCell Class
     * it models the single cell on the KillShotTrack
     * @author Michele Leva, Stefano Martino, Marco Maria Ferrara
     */
    private int tokenNumber;
    private PlayerColor tokenColor;
    private boolean isSkull;

    /**
     * Constructor for the killShotCell class
     */
    public KillShotCell(){
        this.isSkull = true;
        this.tokenColor = null;
        this.tokenNumber = 0;
    }

    /**
     * Alternative constructor for the killShotCell class
     * @param playerColor color of the chosen player
     * @param tokenNumber number of tokens to add to the killShotCell
     */
    public KillShotCell(PlayerColor playerColor, int tokenNumber){
        this.isSkull = false;
        this.tokenColor = playerColor;
        this.tokenNumber = tokenNumber;
    }

    /**
     * Returns the number of tokens on the cell
     * @return the number of tokens on the cell
     */
    public int getTokenNumber(){
        return tokenNumber;
    }

    /**
     * Returns the color of the tokens
     * @return the color of the tokens
     */
    public PlayerColor getTokenColor(){
        return tokenColor;
    }

    /**
     * Returns true if the cell contains a skull, false if it contains one or more tokens
     * @return true if the cell contains a skull, false if it contains one or more tokens
     */
    public boolean isSkull() {
        return isSkull;
    }

    /**
     * Adds the selected token to the specific killShotCell
     * @param tokenColor the color of the token to add
     */
    public void addToken(PlayerColor tokenColor){
        isSkull = false;
        this.tokenColor = tokenColor;
        tokenNumber++;
    }

    /**
     * Adds one more token to the current cell
     */
    public void addToken(){
        tokenNumber++;
    }
}
