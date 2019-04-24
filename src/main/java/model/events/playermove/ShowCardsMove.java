package model.events.playermove;

import model.player_package.PlayerColor;
import view.View;

public class ShowCardsMove extends PlayerMove{

    private final int type;

    public int getType() {
        return type;
    }

    public ShowCardsMove(PlayerColor playerColor , View view, int type){
        super(playerColor , view);
        this.type = type;
    }


}
