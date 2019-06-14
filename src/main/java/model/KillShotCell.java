package model;

import model.player.PlayerColor;

public class KillShotCell {

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
     */
    public KillShotCell(PlayerColor playerColor, int tokenNumber){
        this.isSkull = false;
        this.tokenColor = playerColor;
        this.tokenNumber = tokenNumber;
    }

    public int getTokenNumber(){
        return tokenNumber;
    }

    public PlayerColor getTokenColor(){
        return tokenColor;
    }

    public boolean isSkull() {
        return isSkull;
    }

    /**
     * Adds the selected token to the specific killShotCell
     */
    public void addToken(PlayerColor tokenColor){
        isSkull = false;
        this.tokenColor = tokenColor;
        tokenNumber++;
    }

    public void addToken(){
        tokenNumber++;
    }
}
