package model.exchanges.events;

import view.View;

/**
 * Event sent by the Weapon View and received from the Weapon Controller to choose which weapon in the
 * current player's resources to swap with one on the current spawn point
 */
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
