package model.events.message;

import model.cards.Weapon;
import model.player_package.Player;

import java.util.ArrayList;


public class ShowTargetsMessage extends Message {

    private ArrayList<Player> targets;
    private Weapon weapon;
    private int targetsNumber;
    private int fireMode;


    public ShowTargetsMessage(ArrayList<Player> targets,Weapon weapon,int targetsNumber,int fireMode){
        //super();
        this.targets = new ArrayList<>(targets);
        this.weapon = weapon;
        this.targetsNumber = targetsNumber;
        this.fireMode = fireMode;
    }

    public ArrayList<Player> getTargets(){
        return this.targets;
    }

    public Weapon getWeapon(){
        return weapon;
    }

    public int getTargetsNumber() {
        return targetsNumber;
    }

    public int getFireMode() {
        return fireMode;
    }

    public String toPlayer(){

        for(Player target : targets)
            System.out.println(target.getPlayerColor());
        return"select target";
    }

    public String toOthers(){
        return "player choosing targets";
    }
}
