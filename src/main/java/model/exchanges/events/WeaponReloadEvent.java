package model.exchanges.events;

import view.View;

public class WeaponReloadEvent extends Event {
    private final int input;
    public WeaponReloadEvent(View view , int input){
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
