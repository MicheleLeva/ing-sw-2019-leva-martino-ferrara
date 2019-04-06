package model.events;

import model.Model;
import model.player_package.PlayerColor;
import view.View;

public abstract class PlayerMove {

    private PlayerColor playerColor;

    private View view;

    public PlayerMove(View view){
        this.playerColor = view.getPlayerColor();
        this.view = view;
    }

    public View getView(){
        return this.view;
    }

    public abstract void performMove(Model model);

}
