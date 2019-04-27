package model.events.message;

import model.cards.PowerUp;
import java.util.ArrayList;
import model.player_package.PlayerColor;

public class DrawPowerUpMessage extends Message {

    private final ArrayList<PowerUp> powerUp;

    public DrawPowerUpMessage(PlayerColor playerColor , String playerName , ArrayList<PowerUp> powerUp){
        super(playerColor , playerName);
        this.powerUp = powerUp;
    }


    @Override
    public String toPlayer(){
        String result;
        String andString = "";

        result = "You drew: ";

        for (int i = 0; i < powerUp.size(); i++){
            if (i != 0){
                andString = "and";
            }

            result = result + andString + powerUp.get(i).toString();
        }

        return result;
    }

    @Override
    public String toOthers(){
        String result;

        result = getPlayerName() + " drew " +powerUp.size() +" PowerUp";

        if (powerUp.size() > 1){
            result = result +"s";
        }

        return result;
    }

}

