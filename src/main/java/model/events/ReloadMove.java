package model.events;

import model.Cards.Weapon;
import model.Model;
import view.View;

public class ReloadMove extends PlayerMove {

    private final Weapon weapon;

    public ReloadMove(View view , Weapon weapon){
        super(view);
        this.weapon = weapon;
    }

    public Weapon getWeapon(){
        return weapon;
    }

    @Override
    public void performMove(Model model){

    }
}
