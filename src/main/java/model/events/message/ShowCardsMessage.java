package model.events.message;

import model.cards.PowerUp;
import model.cards.Weapon;
import model.player_package.PlayerColor;

import java.util.ArrayList;


//da modificare: un messaggio non può avere classi del model!
public class ShowCardsMessage extends Message{

    private int type;
    private String cards = " ";

    public ShowCardsMessage(PlayerColor playerColor , String playerName, int type, String cards){
        super(playerColor, playerName);
        this.type = type;
        this.cards = cards;
    }

    public int getType() {
        return type;
    }

    @Override
    public String toPlayer(){
        return ("The cards in your hand are:\n" + cards);
    }

    @Override
    public String toOthers(){
        return (getPlayerName() +" is watching their cards" );
    }

    /*
        for (PowerUp powerUp : powerUps)
            temp = temp.concat(powerUp.toString() + " \n\n");
        for (Weapon weapon : weapons)
            temp = temp.concat(weapon.toString() + " \n\n");
     */
    //da usare in model per costruire questa classe a seconda del tipo di ShowCardsMove

}
