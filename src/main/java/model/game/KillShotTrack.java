package model.game;

import model.player.PlayerColor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class KillShotTrack {
    /**
     * KillShotTrack Class
     * It represents the game KillShotTrack
     * @author Michele Leva, Stefano Martino, Marco Maria Ferrara
     */
    private KillShotCell[] killShotTrack;
    private int lastIndex;
    private Model model;
    private HashMap<PlayerColor, Integer> frenzyTokens = new HashMap<>();

    /**
     * Constructor of the class
     * @param size the number of game's skulls
     * @param model the current game's model
     */
    public KillShotTrack(int size, Model model) {
        killShotTrack = new KillShotCell[size];

        lastIndex = 0;

        for (int i = 0; i < size; i++) {
            killShotTrack[i] = new KillShotCell();
        }

        this.model = model;
    }

    /**
     * Returns the killshottrack
     * @return the killShotTrack
     */
    public KillShotCell[] getKillShotTrack() {
        return killShotTrack;
    }

    /**
     * Removes the skull from the KillShotTrack, switching it with the current player's token
     * @param playerColor the color of the token
     */
    public void removeSkull(PlayerColor playerColor) {


        if (model.getTurnManager().isFrenzy()) {
            if(frenzyTokens.containsKey(playerColor)) {
                frenzyTokens.replace(playerColor, frenzyTokens.get(playerColor) + 1);
            }
            else {
                frenzyTokens.put(playerColor, 1);
            }
        }
        else {

            if (lastIndex < killShotTrack.length) {

                killShotTrack[lastIndex].addToken(playerColor);
                lastIndex++;
            }

            else {

                killShotTrack[killShotTrack.length - 1].addToken();
            }
        }
    }

    /**
     * Adds the overkill(12th damage)
     */
    public void addOverKill() {

        if (model.getTurnManager().isFrenzy()) {
            PlayerColor currentPlayerColor = model.getTurnManager().getCurrentPlayerColor();
            if(frenzyTokens.containsKey(currentPlayerColor))
                frenzyTokens.replace(currentPlayerColor, frenzyTokens.get(currentPlayerColor) + 1);
            else
                frenzyTokens.put(currentPlayerColor,1);
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

    /**
     * Returns the rank of the players, based on the number of tokens they have on the KillShotTrack
     * @return the rank of the players, based on the number of tokens they have on the KillShotTrack
     */
    public LinkedHashMap<PlayerColor,Integer> getRank(){

        LinkedHashMap<PlayerColor,Integer> result = new LinkedHashMap<>();
        //order of player that dealt the killshot damage
        ArrayList<PlayerColor> orderKillShotColor = new ArrayList<>();
         //build the orderKillShotColor array list
        for (int i = 0; i < killShotTrack.length; i++){
            PlayerColor currentPlayerColor = killShotTrack[i].getTokenColor();
            if(!orderKillShotColor.contains(currentPlayerColor)){
                orderKillShotColor.add(currentPlayerColor);
            }
        }

        for (PlayerColor currentPlayerColor : orderKillShotColor) {
            int tokenNumber = 0;

            for (KillShotCell killShotCell : killShotTrack) {
                if (killShotCell.getTokenColor() != null &&
                        killShotCell.getTokenColor().equals(currentPlayerColor)) {
                    tokenNumber = tokenNumber + killShotCell.getTokenNumber();
                }
            }
            //add frenzy tokens
            if (frenzyTokens.containsKey(currentPlayerColor)) {
                tokenNumber = tokenNumber + frenzyTokens.get(currentPlayerColor);
            }
            //build the linkedhashmap
            if (tokenNumber != 0) {
                result.put(currentPlayerColor, tokenNumber);
            }
        }

        return result;
    }

    /**
     * Returns the string representation of the KillShotTrack to be displayed on the Command Line
     * @return the string representation of the KillShotTrack to be displayed on the Command Line
     */
    public String printKillshotTrack(){
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder tokenBuilder = new StringBuilder();
        StringBuilder frenzyBuilder;
        for (KillShotCell killShotCell : killShotTrack) {
            if (killShotCell.isSkull()) {
                stringBuilder.append(CLI.getSkull());
                stringBuilder.append(" ");
            } else {

                String currentColor = CLI.getColor(killShotCell.getTokenColor());
                int tokenNumber = killShotCell.getTokenNumber();
                tokenBuilder.append(currentColor);
                tokenBuilder.append(tokenNumber);
                tokenBuilder.append(CLI.getResetString());
                tokenBuilder.append(" ");

            }

        }

        stringBuilder.append(tokenBuilder);
        if(model.getTurnManager().isFrenzy()){
            frenzyBuilder = new StringBuilder();
            frenzyBuilder.append("\n");
            frenzyBuilder.append("Frenzy tokens: \n");
            for(Map.Entry<PlayerColor,Integer> entry: frenzyTokens.entrySet()){
                PlayerColor currentPlayerColor = entry.getKey();
                Integer currentInt = entry.getValue();
                frenzyBuilder.append(CLI.getColor(currentPlayerColor));
                frenzyBuilder.append(currentInt);
                frenzyBuilder.append(CLI.getResetString());
                frenzyBuilder.append(" ");
            }

            stringBuilder.append(frenzyBuilder);
        }
        return stringBuilder.toString();

    }

    /**
     * Returns the last occupied index
     * @return the last occupied index
     */
    public int getLastIndex(){
        return lastIndex;
    }
}
