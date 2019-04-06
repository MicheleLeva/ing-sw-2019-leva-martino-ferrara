package model.events;

import model.Cards.PowerUp;
import model.Model;
import view.View;

public class UsePowerUp extends PlayerMove{

    private final PowerUp powerUp;

    public UsePowerUp(View view , PowerUp powerUp){
        super(view);
        this.powerUp = powerUp;
    }

    public PowerUp getPowerUp(){
        return powerUp;
    }

    @Override
    public void performMove(Model model){
    }
}
