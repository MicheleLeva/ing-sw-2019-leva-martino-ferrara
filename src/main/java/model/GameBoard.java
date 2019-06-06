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

    public GameBoard(int chosenMap, int killShotCellsNumber, Model model){
        this.killShotTrack = new KillShotTrack(killShotCellsNumber, model);
        this.map = new Map(chosenMap);
        this.decks = new Decks(model);
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
                Square square = getMap().getMap()[i][j];
                if (square != null) {
                    if (square.isSpawn) {
                        while (square.hasEmptyWeaponSlot()) {
                            square.setWeapon(getDecks().drawWeapon());
                        }
                    } else if (square.hasEmptyAmmoSlot()) {
                        square.setAmmo(getDecks().drawAmmoCard());
                    }
                }
            }
        }
    }

}
