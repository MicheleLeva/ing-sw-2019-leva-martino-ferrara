package model.events;

import model.Model;
import model.player_package.PlayerColor;
import view.View;

public class Draw extends PlayerMove{

    public Draw(PlayerColor playerColor , View view){
        super(playerColor , view);
    }

    @Override
    public void performMove(Model model){

    }
}
