package model;

import model.player_package.PlayerColor;

import java.util.ArrayList;

public class KillShotTrack {

    private KillShotCell[] killShotTrack;


    public KillShotTrack(int size){

            killShotTrack = new KillShotCell[size];
            for (int i = 0; i < size; i++) {
                killShotTrack[i] = new KillShotCell();
            }

    }

    public KillShotCell[] getKillShotTrack(){
        return killShotTrack;
    }

    public void removeSkull(PlayerColor playerColor, int tokenNumber){
        for (int i=0; i<killShotTrack.length; i++){
            if (killShotTrack[i].isSkull()) {
                killShotTrack[i] = new KillShotCell(playerColor, tokenNumber);
                break;
            }
        }
    }
}
