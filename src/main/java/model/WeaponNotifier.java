package model;

import model.cards.Weapon;
import model.cards.WeaponAlternative;
import model.cards.WeaponOptional1;
import model.cards.WeaponOptional2;
import model.events.*;
import model.map_package.Square;
import model.player_package.PlayerColor;
import utils.notify.WeaponNotify;

import java.util.ArrayList;

public class WeaponNotifier extends WeaponNotify {

    public void showWeaponCards(PlayerColor playerColor , String availableWeapons){
        String message = "Choose a Weapon:\n";
        message = message +availableWeapons +"\n";
        listeners.get(playerColor).update(new ShowWeaponCardsMessage(message));
    }
/*
    public void notifyAlternativeEffect(PlayerColor playerColor, Weapon weapon){
        String message = "Choose Fire Mode: "+"\n"+((WeaponAlternative)weapon).getBaseText()+" | "+((WeaponAlternative)weapon).getAlternativeText()+"\n";
        listeners.get(playerColor).update(new AskAlternativeMessage(message));
    }
*/
    public void showFireModes(PlayerColor playerColor, String fireModes){
        listeners.get(playerColor).update(new AskFireModesMessage(fireModes));
    }

    public void selectTargets(PlayerColor playerColor, String availableTargets, int targetsNumber){
        String message ="Choose a target: \n";
        message = message+availableTargets;
        listeners.get(playerColor).update(new TargetsSelectionMessage(message,targetsNumber));
    }

    public void chooseWeaponSquare(PlayerColor playerColor, ArrayList<Square> squares){
        String message = "Choose a square to move to:\n";
        for(Square square : squares)
            message = message + square.getID();
        listeners.get(playerColor).update(new ChooseWeaponSquareMessage(message));
    }

    public void askReload(PlayerColor playerColor , String reloadableWeapon){
        String message = "Reloadable weapon: \n";
        message = message +reloadableWeapon +"\n";
        message = message +"Do you want to reload? [Y/N]\n";
        listeners.get(playerColor).update(new AskReloadMessage(message));

    }

    public void requestWeaponReload(PlayerColor playerColor , String weapon , String ammo , String powerUp){
        String message;
        message = weapon;
        message = message +"Available ammo: " +ammo +".\n";
        message = message +"Available powerUp: " +powerUp +"\n";
        listeners.get(playerColor).update(new WeaponReloadMessage(message));
    }

}
