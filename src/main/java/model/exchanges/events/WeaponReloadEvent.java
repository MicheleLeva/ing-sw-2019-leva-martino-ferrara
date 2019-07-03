package model.exchanges.events;

import view.View;

/**
 * Event sent by the Weapon View and received from the Weapon Controller to choose which of the unloaded weapons
 * in the current player's hand to reload
 * @author Michele Leva, Stefano Martino, Marco Maria Ferrara
 */
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
