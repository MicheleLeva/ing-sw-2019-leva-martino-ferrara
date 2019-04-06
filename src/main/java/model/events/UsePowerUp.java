package model.events;

import model.cards.PowerUp;

public class UsePowerUp extends PlayerMove{

    private PowerUp powerUp;

    public PowerUp getPowerUp(){
        return powerUp;
    }
}
