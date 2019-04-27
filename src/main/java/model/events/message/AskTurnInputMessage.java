package model.events.message;

import model.player_package.PlayerColor;

public class AskTurnInputMessage extends Message {
    public AskTurnInputMessage(PlayerColor playerColor){
        super(playerColor);
    }

    @Override
    public String toPlayer(){
        String result;
        result = "Choose a move: ";
    }


}
