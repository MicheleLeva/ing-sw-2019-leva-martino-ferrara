package model.events.message;

import model.cards.PowerUp;
import model.player_package.PlayerColor;

public class PowerUpMessage extends  Message{

    private String effect;

    public String getEffect() {
        return effect;
    }

    public PowerUpMessage(PlayerColor playerColor, String playerName, String effect){
        super(playerColor, playerName);
        this.effect = effect;
    }

    @Override
    public String toPlayer(){
        return("You used the PowerUp chosen:\n"+ getEffect());
    }

    @Override
    public String toOthers(){
        return(getPlayerName() + "used a PowerUp:\n"+ getEffect());
    }
}
