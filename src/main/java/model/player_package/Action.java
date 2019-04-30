package model.player_package;

import model.Model;
import model.cards.Weapon;
import model.events.playermove.PlayerMove;

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

    //Cancellare tutti i metodi sopra

    public abstract void perform(Model model , PlayerColor playerColor) throws Exception;
}
