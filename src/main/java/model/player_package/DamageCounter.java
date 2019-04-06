package model.player_package;


import java.util.ArrayList;

public class DamageCounter {

    private ArrayList<PlayerColor> damage;
    private Player player;

    public DamageCounter(ArrayList<PlayerColor> damage, Player player){
        this.damage = damage;
        this.player = player;
    }

    public void addDamage(int damage){

    }

    public Player getPlayer() {
        return player;
    }

    public void clearDamage(){
        damage.clear();
    }

    public ArrayList<PlayerColor> getDamageCounter(){
        return damage;
    }

    public void changeDamageCounterState(){

    }

}
