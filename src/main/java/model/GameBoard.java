package model;

import model.cards.AmmoCard;
import model.cards.PowerUp;
import model.cards.Weapon;
import model.map_package.Map;
import model.player_package.PlayerColor;

public class GameBoard {

    private KillShotTrack killShotTrack;
    private Decks decks;
    private Map map;

    public void addToken(PlayerColor playerColor, int tokenNumber){

    }

    public void setSpawnWeapon(){

    }

    public void setNonSpawnWeapon(){

    }

    public void setMap(Map map){

    }

    public KillShotTrack getKillShotTrack(){
        return killShotTrack;
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
