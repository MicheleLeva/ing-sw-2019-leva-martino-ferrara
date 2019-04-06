package model.events;

import model.cards.Weapon;

public class ReloadMessage extends Message {

    private Weapon weapon;

    public Weapon getWeapon() {
        return weapon;
    }
}
