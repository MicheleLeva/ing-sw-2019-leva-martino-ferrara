package model;

import model.map.Map;
import model.player.PlayerColor;

public class GameBoard {

    private KillShotTrack killShotTrack;
    private Map map;
    private Decks decks;

    public GameBoard(int chosenMap, int killShotCellsNumber, Model model){
        this.killShotTrack = new KillShotTrack(killShotCellsNumber, model);
        this.map = new Map(chosenMap);
        this.decks = new Decks(model);
    }

    public void addToken(PlayerColor playerColor){
    this.killShotTrack.removeSkull(playerColor);
    }

    public KillShotTrack getKillShotTrack(){

        return this.killShotTrack;
    }

    public Map getMap(){
        return this.map;
    }

    public Decks getDecks(){
        return this.decks;
    }

    public void setCardsOnMap(){

    }
}
