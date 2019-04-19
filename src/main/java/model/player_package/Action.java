package model.player_package;

import model.cards.Weapon;
import model.events.PlayerMove;
import model.map_package.Square;

public abstract class Action {

    private Player player;
    private PlayerMove shoot;
    private PlayerMove run;
    private PlayerMove grab;

    protected void run(int steps){

    }

    protected void shoot(Weapon weapon, Player opponent){

    }

    protected Object grab(){
        return null;
    }

    protected void showAction(){

    }
}
