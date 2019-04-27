package model.events.message;

import model.cards.Weapon;
import model.GameBoard;
import model.player_package.PlayerColor;

import java.util.ArrayList;

public class ShootMessage extends Message {

    private final Weapon weapon;
    private final ArrayList<PlayerColor> opponentsColor;
    private final String resultString;

    public ShootMessage(PlayerColor shooterColor , String shooterName , ArrayList<PlayerColor> opponentsColor ,String resultString, Weapon weapon , GameBoard gameBoard){
        super(shooterColor , shooterName);
        this.weapon = weapon;
        this.opponentsColor = opponentsColor;
        this.resultString = resultString;

    }
    public Weapon getWeapon() {
        return weapon;
    }

    public ArrayList<PlayerColor> getOpponentsColor() {
        return opponentsColor;
    }

    public String getResultString() {
        return resultString;
    }

    @Override
    public String toPlayer(){

        return ("You shot to " +resultString);
    }

    @Override
    public String toOthers(){
        return(getPlayerName() +" has just shot to " +resultString);
    }
}
