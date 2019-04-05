package model.events;

import model.Cards.Weapon;
import model.player_package.Player;

public class ShootMessage extends PlayerMove {

    private Weapon weapon;
    private Player opponent;

    public Weapon getWeapon() {
        return weapon;
    }

    public Player getOpponent() {
        return opponent;
    }
}
