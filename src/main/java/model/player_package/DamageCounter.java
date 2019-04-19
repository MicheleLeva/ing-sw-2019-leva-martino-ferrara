package model.player_package;


import java.util.ArrayList;

public class DamageCounter {

    private ArrayList<PlayerColor> damage;


    public DamageCounter(){
        this.damage = new ArrayList<>();

    }

    public void addDamage(PlayerColor damage){
        this.damage.add(damage);


    }

    public void clearDamage(){
        damage.clear();
    }

    public ArrayList<PlayerColor> getDamageCounter(){
        return this.damage;
    }

    public void changeDamageCounterState(){


    }

}
