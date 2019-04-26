package model;

import model.cards.AmmoCard;
import model.cards.PowerUp;
import model.cards.Weapon;
import model.map_package.Map;
import model.player_package.PlayerColor;

public class GameBoard {

    private KillShotTrack killShotTrack;
    private Map map;
    private Decks decks;



    public GameBoard(int playersNumber, int KillShotCellsNumber){
        this.killShotTrack = new KillShotTrack(KillShotCellsNumber);
        this.map = new Map(playersNumber);
        this.decks = new Decks();
    }

    public void addToken(PlayerColor playerColor, int tokenNumber){
    this.killShotTrack.removeSkull(playerColor, tokenNumber);
    }

    public void setSpawnWeapon(){

    }

    public void setNonSpawnWeapon(){

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

    public Weapon removeWeapon(){
        return null;
    }

    public PowerUp removePowerUp(){
        return null;
    }

    public AmmoCard removeAmmoCard(){
        return null;
    }
}
