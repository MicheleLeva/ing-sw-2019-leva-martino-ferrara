package model.player;


import controller.Checks;
import model.game.CLI;

import java.util.ArrayList;

/**
 * Representation of the player's received damage
 */
public class DamageCounter {

    private ArrayList<PlayerColor> damage;


    /**
     * Constructor for the Damage Counter class
     */
    public DamageCounter(){
        this.damage = new ArrayList<>();

    }


    /**
     * Adds the given amount of damage to the DamageCounter with the color of 'playerColor'
     * @param playerColor color of the player that gave the damage
     * @param damage damage to add to the counter
     */
    public void addDamage(PlayerColor playerColor , int damage){
        for (int i = 0; i < damage; i++){
            this.damage.add(playerColor);
        }
    }

    /**
     * Clears the damage received
     */
    public void clearDamage(){
        damage.clear();
    }

    /**
     * Returns the damage counter
     * @return the damage counter
     */
    public ArrayList<PlayerColor> getDamageCounter(){
        return this.damage;
    }

    /**
     * Returns the amount of damage received
     * @return the amount of damage received
     */
    public int getDamage(){
        return damage.size();
    }

    /**
     * Returns the damage received from a player with a specific color
     * @param playerColor of the given player.
     * @return the damage received from a player with a specific color
     */
    public int getDamageFromColor(PlayerColor playerColor){
        int result = 0;
        for (PlayerColor color : damage) {
            if (color.equals(playerColor)) {
                result++;
            }
        }
        return result;
    }

    /**
     * Outputs the amount of damage received
     * @return a string representing the received damage
     */
    public String printDamageCounter(){
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder damageBuilder = new StringBuilder();
        for (PlayerColor playerColor : damage) {
            damageBuilder.append(CLI.getColor(playerColor));
            damageBuilder.append(CLI.getDamage());
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
