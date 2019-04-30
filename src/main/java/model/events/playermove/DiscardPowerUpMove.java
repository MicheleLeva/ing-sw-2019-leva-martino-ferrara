package model.events.playermove;

import view.View;

public class DiscardPowerUpMove extends PlayerMove {
    private final int num;

    public DiscardPowerUpMove(View view , int num){
        super(view);
        this.num = num;
    }

    public int getNum() {
        return num;
    }
}
