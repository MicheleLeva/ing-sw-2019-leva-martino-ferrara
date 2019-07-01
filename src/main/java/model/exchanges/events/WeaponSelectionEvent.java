package model.exchanges.events;

import view.View;

/**
 * Event sent by the Weapon View and received from the Weapon Controller to choose which of the loaded weapons
 * in the current player's resources to shoot with
 */
public class WeaponSelectionEvent extends Event {
    private final int index;

    public WeaponSelectionEvent(View view, int index) {
        super(view);
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "," + getIndex();
    }
}
