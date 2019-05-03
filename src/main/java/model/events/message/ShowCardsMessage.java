package model.events.message;

import model.cards.PowerUp;
import model.cards.Weapon;
import model.player_package.PlayerColor;

import java.util.ArrayList;

public class ShowCardsMessage extends Message{

    private ArrayList<PowerUp> powerUps;
    private ArrayList<Weapon> weapons;
    private int Type;

    public ShowCardsMessage(PlayerColor playerColor , String playerName, ArrayList<PowerUp> powerUps, ArrayList<Weapon> weapons){
        super(playerColor, playerName);
        this.powerUps = powerUps;
        this.weapons = weapons;
    }

    public int getType(){
        return this.Type;
    }

    @Override
    public String toPlayer(){
        String temp = "";
        for (PowerUp powerUp : powerUps)
            temp = temp.concat(powerUp.toString() + " \n\n");
        for (Weapon weapon : weapons)
            temp = temp.concat(weapon.toString() + " \n\n");

        return ("The cards in your hand are:\n" + temp);
    }

    @Override
    public String toOthers(){
        return (getPlayerName() +" is watching their cards" );
    }

}
