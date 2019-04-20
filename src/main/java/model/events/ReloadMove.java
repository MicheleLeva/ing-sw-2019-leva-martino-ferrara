package model.events;

import view.View;

public class ReloadMove extends PlayerMove {

    private final int index;

    public ReloadMove(View view , int index){
        super(view);
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
