package model.events.playermove;

import view.View;

public class PowerUpMove extends PlayerMove{

    private final int index;

    public PowerUpMove(View view , int index){
        super(view);
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
