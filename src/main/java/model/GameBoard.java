package model;

import model.cards.AmmoCard;
import model.cards.powerups.PowerUp;
import model.cards.weapons.Weapon;
import model.map.Map;
import model.player.PlayerColor;

import java.util.ArrayList;

public class GameBoard {

    private KillShotTrack killShotTrack;
    private Map map;
    private Decks decks;



    public GameBoard(int playersNumber, int KillShotCellsNumber){
        this.killShotTrack = new KillShotTrack(KillShotCellsNumber);
        this.map = new Map(playersNumber);
        this.decks = new Decks();
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
