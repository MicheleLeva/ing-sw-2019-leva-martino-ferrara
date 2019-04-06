package model.events;


import model.map_package.Square;
import model.player_package.PlayerColor;

public class RunMessage extends Message {

    private final Square newPosition;

    public RunMessage(PlayerColor playerColor ,String playerName , Square newPosition){
        super(playerColor , playerName);
        this.newPosition = newPosition;
    }

    public Square getNewPosition(){
        return newPosition;
    }

    @Override
    public String toPlayer(){
        return ("You moved to " +newPosition.toString());
    }

    @Override
    public String toOthers(){
        return (getPlayerName() +" has moved to " +newPosition.toString());
    }

}
