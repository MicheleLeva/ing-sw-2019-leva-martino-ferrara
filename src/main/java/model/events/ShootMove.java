package model.events;

import model.cards.PowerUp;
import model.cards.Weapon;
import model.player_package.PlayerColor;
import view.View;


public class ShootMove extends PlayerMove{

    private final PlayerColor opponentColor;
    private final PowerUp powerUp;
    private final Weapon weapon;

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


}
