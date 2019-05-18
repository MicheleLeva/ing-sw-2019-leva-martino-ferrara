package model;

import model.player.PlayerColor;

public class KillShotTrack {

    private KillShotCell[] killShotTrack;
    private boolean isFrenzy;


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

    public boolean isFrenzy(){
        return this.isFrenzy;
    }
}
