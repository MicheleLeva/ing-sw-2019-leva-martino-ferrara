package model;

import model.player_package.PlayerColor;

import java.util.ArrayList;

public class KillShotTrack {

    private KillShotCell[] killShotTrack;

    private int lastSkull;

    public KillShotTrack(int size){

            killShotTrack = new KillShotCell[size];
            for (int i = 0; i < size; i++) {
                killShotTrack[i] = new KillShotCell();
            }

            lastSkull = size -1;

    }

    public KillShotCell[] getKillShotTrack(){
        return killShotTrack;
    }

    public void removeSkull(PlayerColor playerColor, int tokenNumber){
        if (lastSkull > 0)
            killShotTrack[lastSkull] = new KillShotCell(playerColor, tokenNumber);
        lastSkull--;
    }

    public boolean isFrenzy(){
        return(lastSkull == 0);
    }
}

