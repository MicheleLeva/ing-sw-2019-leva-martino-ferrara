package model.exchanges.events;

import view.View;

/**
 * Event sent by the Weapon View and received from the Weapon Controller to choose the which weapon to
 * pickup from the current spawn point
 * @author Michele Leva, Stefano Martino, Marco Maria Ferrara
 */
public class ChoosePickUpWeaponEvent extends Event {
    private final int input;
    public ChoosePickUpWeaponEvent(View view,int input) {
        super(view);
        this.input = input;
    }
    public int getInput(){
        return input;
    }
    @Override
    public String toString() {
        return "ChoosePickUpWeaponEvent," + getInput();
    }
}
