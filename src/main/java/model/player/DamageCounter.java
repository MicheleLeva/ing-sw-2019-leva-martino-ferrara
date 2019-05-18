package model.player;


import java.util.ArrayList;

public class DamageCounter {

    private ArrayList<PlayerColor> damage;


    public DamageCounter(){
        this.damage = new ArrayList<>();

    }

    public void addDamage(PlayerColor playerColor , int damage){
        for (int i = 0; i < damage; i++){
            this.damage.add(playerColor);
        }
    }

    public void clearDamage(){
        damage.clear();
    }

    public ArrayList<PlayerColor> getDamageCounter(){
        return this.damage;
    }

    public int getDamage(){
        return damage.size();
    }

    public int getDamageFromColor(PlayerColor playerColor){
        int result = 0;
        for (int i = 0; i < damage.size(); i++){
            if (damage.get(i) == playerColor){
                result++;
            }
        }
        return result;
    }


}
