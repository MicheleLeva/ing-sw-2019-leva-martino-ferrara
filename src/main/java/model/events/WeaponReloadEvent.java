package model.events;

import view.PlayerView;

public class WeaponReloadEvent extends Event {
    private final int input;
    public WeaponReloadEvent(PlayerView view , int input){
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
