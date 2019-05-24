package model;

import model.player.PlayerColor;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class KillShotTrack {

    private KillShotCell[] killShotTrack;
    private int lastIndex;

    public KillShotTrack(int size){
            killShotTrack = new KillShotCell[size];

            lastIndex = 0;

            for (int i = 0; i < size; i++) {
                killShotTrack[i] = new KillShotCell();
            }
    }

    public KillShotCell[] getKillShotTrack(){
        return killShotTrack;
    }

    public void removeSkull(PlayerColor playerColor){

        if(lastIndex < killShotTrack.length){
            killShotTrack[lastIndex].addToken(playerColor);
            lastIndex++;

            if(lastIndex >= killShotTrack.length){
                //todo setta frenzy turn
                killShotTrack[killShotTrack.length - 1].addToken();
            }
        }
    }

    public void addOverKill(){
        int currentIndex;
        if(lastIndex == 0){
            currentIndex = lastIndex;
        }
        else{
            currentIndex = lastIndex - 1;
        }

        killShotTrack[currentIndex].addToken();
    }

    public LinkedHashMap<PlayerColor,Integer> getRank(){

        LinkedHashMap<PlayerColor,Integer> result = new LinkedHashMap<>();
        //order of player that dealt the killshot damage
        ArrayList<PlayerColor> orderKillShotColor = new ArrayList<>();
         //build the orderKillShotColor array list
        for (int i = 0; i < killShotTrack.length - 1; i++){
            PlayerColor currentPlayerColor = killShotTrack[i].getTokenColor();
            if(!orderKillShotColor.contains(currentPlayerColor)){
                orderKillShotColor.add(currentPlayerColor);
            }
        }

        for (int i = 0; i < orderKillShotColor.size(); i++){
            PlayerColor currentPlayerColor = orderKillShotColor.get(i);
            int tokenNumber = 0;

            for (int j = 0; j < killShotTrack.length - 1; j++){
                if (killShotTrack[j].getTokenColor() == currentPlayerColor){
                    tokenNumber = tokenNumber + killShotTrack[j].getTokenNumber();
                }
            }
            //build the linkedhashmap
            if(tokenNumber != 0){
                result.put(currentPlayerColor , tokenNumber);
            }
        }

        return result;
    }
}
