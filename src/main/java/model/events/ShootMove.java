package model.events;

import model.Cards.PowerUp;
import model.Cards.Weapon;
import model.Model;
import model.player_package.PlayerColor;
import view.View;

public class ShootMove extends PlayerMove{

    private PlayerColor opponentColor;
    private PowerUp powerUp;
    private Weapon weapon;

    public ShootMove(View view , PlayerColor opponentColor , PowerUp powerUp , Weapon weapon){
        super(view);
        this.weapon = weapon;
        this.powerUp = powerUp;
        this.opponentColor = opponentColor;
    }

    public PlayerColor getOpponentColor(){
        return opponentColor;
    }

    public Weapon getWeapon(){
        return weapon;
    }

    public PowerUp getPowerUp(){
        return powerUp;
    }

    @Override
    public void performMove(Model mode){
    }
}
