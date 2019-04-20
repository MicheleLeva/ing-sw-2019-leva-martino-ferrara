package model.player_package;


import java.util.ArrayList;

public class DamageCounter {

    private ArrayList<PlayerColor> damage;


    public DamageCounter(){
        this.damage = new ArrayList<>();

    }

    public void addDamage(PlayerColor damage, int number){
        while(number>0) {
            this.damage.add(damage);
            number--;
        }
        if(this.damage.size() >= 11)
            if(this.damage.size() >= 12)
                System.out.println("Giocatore ucciso + marchio assegnato");
            else
                System.out.println("Giocatore ucciso");
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
