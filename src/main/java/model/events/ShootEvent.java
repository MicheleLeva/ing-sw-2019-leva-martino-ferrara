package model.events;

import model.cards.Weapon;
import view.PlayerView;

public class ShootEvent extends Event {
    public ShootEvent(PlayerView view, Weapon weapon, int fireMode) {
        super(view);
    }
}
