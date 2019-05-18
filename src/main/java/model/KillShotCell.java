package model;

import model.player.PlayerColor;

public class KillShotCell {

    private int tokenNumber;
    private PlayerColor tokenColor;
    private boolean isSkull;

    public KillShotCell(){
        this.isSkull = true;
        this.tokenColor = null;
        this.tokenNumber = 0;
    }

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
}
