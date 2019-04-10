package model.events;

import model.Model;
import model.adrenaline_exceptions.EmptySquareException;
import model.adrenaline_exceptions.IllegalOpponentException;
import model.adrenaline_exceptions.InsufficientAmmoException;
import model.adrenaline_exceptions.WallException;
import model.player_package.PlayerColor;
import view.View;

public abstract class PlayerMove {

    private PlayerColor playerColor;

    private View view;

    public PlayerMove(View view){
        this.playerColor = view.getPlayerColor();
        this.view = view;
    }

    public PlayerMove(PlayerColor playerColor, View view){
        this.view = view;
        this.playerColor = playerColor;
    }

    public View getView(){
        return this.view;
    }

    public abstract void performMove(Model model) throws WallException, IllegalOpponentException, EmptySquareException, InsufficientAmmoException;

}
