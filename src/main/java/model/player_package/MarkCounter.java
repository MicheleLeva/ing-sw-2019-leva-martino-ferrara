package model.player_package;

import java.util.ArrayList;

public class MarkCounter {

    private ArrayList<PlayerColor> mark;
    private Player player;

    public MarkCounter(ArrayList<PlayerColor> mark, Player player){

        this.mark = mark;
        this.player = player;

    }

    public Player getPlayer() {
        return player;
    }

    public void addMark(int mark){

    }

    public void clearMark(){
        mark.clear();
    }

    public ArrayList<PlayerColor> getMarkCounter(){
        return mark;
    }

    public boolean checkMarkValidity(){
        return true;
    }
}
