package model.events;

import model.cards.PowerUp;
import model.cards.Weapon;
import model.player_package.Player;

public class ShootMove extends PlayerMove{

    private Player opponent;
    private PowerUp powerUp;
    private Weapon weapon;

    public Player getOpponent(){
        return opponent;
    }

    public Weapon getWeapon(){
        return weapon;
    }

    public PowerUp getPowerUp(){
        return powerUp;
    }
}
