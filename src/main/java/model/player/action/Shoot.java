package model.player.action;

import model.Model;
import model.adrenaline_exceptions.NoReloadedWeaponsException;
import model.cards.weapons.Weapon;
import model.player.Player;
import model.player.PlayerColor;

import java.util.ArrayList;

public class Shoot extends Action {


    //todo implement method
    @Override
    public void perform (Model model, PlayerColor playerColor) throws NoReloadedWeaponsException {
        Player currentPlayer = model.getPlayer(playerColor);
        //check if he can t shoot
        if(currentPlayer.getResources().getReloadedWeapon().isEmpty())
            throw new NoReloadedWeaponsException();
        model.showWeaponCards(playerColor);
        /*for(Weapon weapon : weapons) {
            messages = messages+count+")"+weapons.get(count).getWeaponName()+"\n";
            count++;
        }*/
        //model.getWeaponNotifier().notifyShowWeaponCards(messages,currentPlayer.getPlayerName());

        //todo chiamare gli update in model (shoot, action, turn)
    }
}
