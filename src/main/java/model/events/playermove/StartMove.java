package model.events.playermove;

import model.player_package.PlayerColor;
import view.View;

public class StartMove extends  PlayerMove {

    private int index;

    public int getIndex() {
        return index;
    }

    public StartMove(PlayerColor playerColor, View view){
        super(playerColor, view);
    }


}
