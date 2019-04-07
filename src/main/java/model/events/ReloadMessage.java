package model.events;

import model.cards.Weapon;
import model.player_package.PlayerColor;

public class ReloadMessage extends Message {

    private Weapon weapon;

    public ReloadMessage(PlayerColor playerColor , String playerName , Weapon weapon){
        super(playerColor , playerName);
        this.weapon = weapon;
    }
    public Weapon getWeapon() {
        return weapon;
    }

    @Override
    public String toPlayer(){
        return ("You have reloaded " +weapon.toString());
    }

    @Override
    public String toOthers(){
        return(getPlayerName() +" has reloaded " +weapon.toString());
    }
}
