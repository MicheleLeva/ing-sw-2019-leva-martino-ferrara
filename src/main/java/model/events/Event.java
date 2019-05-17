package model.events;

import model.player_package.PlayerColor;
import view.PlayerView;

import java.util.List;

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

    public String arrayToString(List<Integer> array){
        String temp = "";
        for(int i = 0; i<array.size(); i++){
            temp = temp.concat(array.get(i).toString());
            if (i != array.size()-1){
                temp = temp.concat(",");
            }
        }

        return temp;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
