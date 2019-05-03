package model.events.message;

import model.cards.Weapon;
import model.GameBoard;
import model.player_package.PlayerColor;

import java.util.ArrayList;

public class ShootMessage extends Message {

    private final Weapon weapon;
    private final ArrayList<PlayerColor> opponentsColor;
    private final String opponentName;

    public ShootMessage(PlayerColor shooterColor , String shooterName , ArrayList<PlayerColor> opponentColor , String opponentName , Weapon weapon , GameBoard gameBoard){
        super(shooterColor , shooterName);
        this.weapon = weapon;
        this.opponentsColor = opponentColor;
        this.opponentName = opponentName;
    }
    public Weapon getWeapon() {
        return weapon;
    }

    public ArrayList<PlayerColor> getOpponentColor() {
        return opponentsColor;
    }

    public String getOpponentName(){
        return opponentName;
    }

    @Override
    public String toPlayer(){
        return ("You shot to " +opponentName);
    }

    @Override
    public String toOthers(){
        return(getPlayerName() +" has just shot to " +opponentName);
    }
}
