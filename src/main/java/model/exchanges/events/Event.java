package model.exchanges.events;

import model.player.PlayerColor;
import view.View;

import java.util.List;

public abstract class Event {
    private final PlayerColor playerColor;

    private final View view;

    public Event(View view){
        this.view = view;
        playerColor = view.getPlayerColor();
    }

    public View getView(){
        return view;
    }

    public PlayerColor getPlayerColor(){
        return playerColor;
    }

    public String arrayToString(List<Integer> array){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i<array.size(); i++){
             stringBuilder.append(array.get(i).toString());
            if (i != array.size()-1){
                stringBuilder.append(",");
            }
        }
        return stringBuilder.toString();
    }
}
