package model.player_package;

import model.Model;
import model.adrenaline_exceptions.NoReloadedWeaponsException;
import model.cards.Weapon;

import java.util.ArrayList;

public class Shoot extends Action {
    private boolean doneShooting;

    @Override
    public void showAction(){

    }
    //implement method
    @Override
    public void perform (Model model, PlayerColor playerColor) throws NoReloadedWeaponsException {
        int count = 0;
        String message = "";
        Player currentPlayer = model.getPlayer(playerColor);
        ArrayList<Weapon> weapons = currentPlayer.getResources().getReloadedWeapon();
        //check if he can t shoot
        if(currentPlayer.getResources().getReloadedWeapon().isEmpty())
            throw new NoReloadedWeaponsException();
        model.showWeaponCards(playerColor);
        /*for(Weapon weapon : weapons) {
            message = message+count+")"+weapons.get(count).getWeaponName()+"\n";
            count++;
        }*/
        //model.getWeaponNotifier().notifyShowWeaponCards(message,currentPlayer.getPlayerName());
    }

    public Shoot(){
        doneShooting = false;
    }

    public Boolean isDoneShooting(){
        return doneShooting;
    }

    public void setDoneShooting(Boolean flag){
        doneShooting = flag;
    }
}
