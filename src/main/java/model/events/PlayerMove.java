package model.events;

import model.player_package.Player;
import view.View;

public abstract class PlayerMove {

    private Player player;

    private View view;

    public void PlayerMove(Player player, View view){

    }

    protected Player getPlayer(){
        return null;
    }

    protected View getView(){
        return null;
    }
}
