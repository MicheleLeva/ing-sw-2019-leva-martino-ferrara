package model;

import model.cards.AmmoCard;
import model.cards.powerups.PowerUp;
import model.cards.weapons.Weapon;
import model.map.Map;
import model.map.Square;
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
        setCardsOnMap();
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
        for(int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if(getMap().getMap()[i][j]!=null && getMap().getMap()[i][j].isSpawn) {
                    getMap().getMap()[i][j].setWeapon(getDecks().drawWeapon());
                    getMap().getMap()[i][j].setWeapon(getDecks().drawWeapon());
                    getMap().getMap()[i][j].setWeapon(getDecks().drawWeapon());
                }
                else
                if(getMap().getMap()[i][j]!=null){
                    getMap().getMap()[i][j].setAmmo(getDecks().drawAmmoCard());
                }
            }
        }
    }
}
