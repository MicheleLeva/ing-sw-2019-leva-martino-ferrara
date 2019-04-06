package model.events;

import model.player_package.PlayerColor;
import view.View;

public class ShowCards extends PlayerMove{

    public ShowCards(PlayerColor playerColor , View view){
        super(playerColor , view);
    }

}
