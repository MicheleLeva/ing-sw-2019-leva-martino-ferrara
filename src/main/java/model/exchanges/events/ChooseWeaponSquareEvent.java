package model.exchanges.events;

import view.View;

/**
 * Event sent by the Weapon View and received from the Weapon Controller to choose the square to use
 * for the current weapon's requirements
 * @author Michele Leva, Stefano Martino, Marco Maria Ferrara
 */
public class ChooseWeaponSquareEvent extends Event {
    private final int input;
    public ChooseWeaponSquareEvent(View view, int input) {
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
