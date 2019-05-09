package model.events;

import model.player_package.PlayerColor;
import view.PlayerView;

public abstract class Event {
    private final PlayerColor playerColor;

    private final PlayerView view;

    public Event(PlayerView view){
        this.view = view;
        playerColor = view.getPlayerColor();
    }

    public PlayerView getView(){
        return view;
    }

    public PlayerColor getPlayerColor(){
        return playerColor;
    }
}
