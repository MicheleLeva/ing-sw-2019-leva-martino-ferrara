package model.player;


import controller.Checks;
import model.CLI;

import java.util.ArrayList;

public class DamageCounter {

    private final String DAMAGE_CLI = "*";
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

    public String printDamageCounter(){
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder damageBuilder = new StringBuilder();
        for (int i = 0; i < damage.size(); i++){
            damageBuilder.append(CLI.getColor(damage.get(i)));
            damageBuilder.append(DAMAGE_CLI);
            damageBuilder.append(CLI.getResetString());
        }

        stringBuilder.append(damageBuilder);
        if(damage.size() == Checks.getKillshot()){
            stringBuilder.append(" KILLSHOT!");
        }

        if(damage.size() == Checks.getMaxDamage()){
            stringBuilder.append(" DEAD!");
        }

        return stringBuilder.toString();
    }


}
