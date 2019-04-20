package model.events;

import view.View;

public class UsePowerUp extends PlayerMove{

    private final int index;

    public UsePowerUp(View view , int index){
        super(view);
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
