package model.events.playermove;

import model.cards.PowerUp;
import model.cards.Weapon;
import model.player_package.PlayerColor;
import view.View;

import java.util.ArrayList;


public class ShootMove extends PlayerMove{

    private ArrayList<PlayerColor> opponentsColor;
    //private final PowerUp powerUp;
    private final Weapon weapon;
    private int fireMode;

    public ShootMove(View view , ArrayList<PlayerColor> opponentsColor , /*PowerUp powerUp ,*/ Weapon weapon, int fireMode){
        super(view);
        this.weapon = weapon;
        //this.powerUp = powerUp;
        this.opponentsColor = new ArrayList<>(opponentsColor);
        this.fireMode = fireMode;
    }

    public ArrayList<PlayerColor> getOpponentsColor(){
        return opponentsColor;
    }

    public Weapon getWeapon(){
        return weapon;
    }

    /*public PowerUp getPowerUp(){
        return powerUp;
    }
*/

    public int getFireMode() {
        return fireMode;
    }
}
