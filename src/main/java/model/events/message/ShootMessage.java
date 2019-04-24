package model.events.message;

import model.cards.Weapon;
import model.GameBoard;
import model.player_package.PlayerColor;

public class ShootMessage extends Message {

    private final Weapon weapon;
    private final PlayerColor opponentColor;
    private final String opponentName;

    public ShootMessage(PlayerColor shooterColor , String shooterName , PlayerColor opponentColor , String opponentName , Weapon weapon , GameBoard gameBoard){
        super(shooterColor , shooterName);
        this.weapon = weapon;
        this.opponentColor = opponentColor;
        this.opponentName = opponentName;
    }
    public Weapon getWeapon() {
        return weapon;
    }

    public PlayerColor getOpponentColor() {
        return opponentColor;
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
