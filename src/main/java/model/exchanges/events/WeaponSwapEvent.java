package model.exchanges.events;

import view.View;

public class WeaponSwapEvent extends Event {
    private final int input;
    public WeaponSwapEvent(View view , int input){
        super(view);
        this.input = input;
    }

    public int getInput(){
        return input;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "," + getInput();
    }
}
