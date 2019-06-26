package model;

import model.player.PlayerColor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class KillShotTrack {

    private KillShotCell[] killShotTrack;
    private int lastIndex;
    private Model model;
    private HashMap<PlayerColor, Integer> frenzyTokens = new HashMap<>();

    public KillShotTrack(int size, Model model) {
        killShotTrack = new KillShotCell[size];

        lastIndex = 0;

        for (int i = 0; i < size; i++) {
            killShotTrack[i] = new KillShotCell();
        }

        this.model = model;
    }

    public KillShotCell[] getKillShotTrack() {
        return killShotTrack;
    }

    public void removeSkull(PlayerColor playerColor) {

        if (model.getTurnManager().isFrenzy()) {
            frenzyTokens.replace(playerColor, frenzyTokens.get(playerColor) + 1);
        }
        else {

            if (lastIndex < killShotTrack.length) {
                killShotTrack[lastIndex].addToken(playerColor);
                lastIndex++;
            }

            if (lastIndex >= killShotTrack.length) {
                model.getTurnManager().setFrenzy();
                killShotTrack[killShotTrack.length - 1].addToken();
            }
        }
    }

    public void addOverKill() {
        if (model.getTurnManager().isFrenzy()) {
            PlayerColor currentPlayerColor = model.getTurnManager().getCurrentPlayerColor();
            frenzyTokens.replace(currentPlayerColor, frenzyTokens.get(currentPlayerColor) + 1);
        }
        else {
            int currentIndex;
            if (lastIndex == 0) {
                currentIndex = lastIndex;
            } else {
                currentIndex = lastIndex - 1;
            }

            killShotTrack[currentIndex].addToken();
        }
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

            for (int j = 0; j < killShotTrack.length; j++){
                if (killShotTrack[j].getTokenColor() != null && //todo chiedere se è necessario (serve solo per test)
                        killShotTrack[j].getTokenColor().equals(currentPlayerColor)){
                    tokenNumber = tokenNumber + killShotTrack[j].getTokenNumber();
                }
            }
            //add frenzy tokens
            if(frenzyTokens.containsKey(currentPlayerColor)) {
                tokenNumber = tokenNumber + frenzyTokens.get(currentPlayerColor);
            }
            //build the linkedhashmap
            if(tokenNumber != 0){
                result.put(currentPlayerColor , tokenNumber);
            }
        }

        return result;
    }

    public String printKillshotTrack(){
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder tokenBuilder = new StringBuilder();

        for (int i = 0; i < killShotTrack.length; i++){
            if(killShotTrack[i].isSkull()){
               stringBuilder.append(CLI.getSkull());
               stringBuilder.append(" ");
            }
            else{

                String currentColor = CLI.getColor(killShotTrack[i].getTokenColor());
                int tokenNumber = killShotTrack[i].getTokenNumber();
                tokenBuilder.append(currentColor);
                tokenBuilder.append(tokenNumber);
                tokenBuilder.append(CLI.getResetString());

                stringBuilder.append(tokenBuilder);
            }
        }

        return stringBuilder.toString();

    }
}
