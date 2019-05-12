package model.events;

import view.PlayerView;

public class WeaponSelectionEvent extends Event {
    private int index;

    public WeaponSelectionEvent(PlayerView view, int index) {
        super(view);
    }

    public int getIndex() {
        return index;
    }
}
