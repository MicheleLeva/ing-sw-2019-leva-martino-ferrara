package model.exchanges.events;

import view.View;

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
