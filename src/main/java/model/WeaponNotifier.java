package model;

import model.cards.Weapon;
import model.cards.WeaponAlternative;
import model.cards.WeaponOptional1;
import model.cards.WeaponOptional2;
import model.events.*;
import model.player_package.Player;
import model.player_package.PlayerColor;
import utils.notify.WeaponNotify;

import java.util.ArrayList;

public class WeaponNotifier extends WeaponNotify {

    public void showWeaponCards(PlayerColor playerColor , String availableWeapons){
        String message = "Choose a Weapon:\n";
        message = message +availableWeapons +"\n";
        listeners.get(playerColor).update(new ShowWeaponCardsMessage(message));
    }

    public void notifyAlternativeEffect(PlayerColor playerColor, Weapon weapon){
        String message = "Choose Fire Mode: "+"\n"+((WeaponAlternative)weapon).getBaseText()+" | "+((WeaponAlternative)weapon).getAlternativeText()+"\n";
        listeners.get(playerColor).update(new AskAlternativeMessage(message));
    }

    public void notifyBaseLockRifleTargets(PlayerColor playerColor,String availableTargets,int targetsNumber){
        String message ="Choose a target: \n";
        message = message+availableTargets;
        listeners.get(playerColor).update(new BaseLockRifleTargetsMessage(message,targetsNumber));
    }

    public void optionalLockRifle1(PlayerColor playerColor, Weapon weapon){
        String message = "Use Optional1?:\n"+((WeaponOptional1)weapon).getOptionalText1();
        listeners.get(playerColor).update(new OptionalLockRifleMessage1(message));
    }

    public void optionalLockRifleTargets1(PlayerColor playerColor,String availableTargets,int targetsNumber){
        String message ="Choose a target: \n";
        message = message+availableTargets;
        listeners.get(playerColor).update(new OptionalLockRifleTargetsMessage1(message,targetsNumber));
    }

    public void AlternativeHellionTargets(PlayerColor playerColor,String availableTargets,int targetsNumber){
        String message ="Choose a target: \n";
        message = message+availableTargets;
        listeners.get(playerColor).update(new AlternativeHellionTargetsMessage(message,targetsNumber));
    }

    public void optionalThor2(PlayerColor playerColor, Weapon weapon){
        String message = "Use Optional1?:\n"+((WeaponOptional2)weapon).getOptionalText2();
        listeners.get(playerColor).update(new OptionalThorMessage2(message));
    }

    public void optionalThorTargets2(PlayerColor playerColor,String availableTargets,int targetsNumber){
        String message ="Choose a target: \n";
        message = message+availableTargets;
        listeners.get(playerColor).update(new OptionalThorTargetsMessage2(message,targetsNumber));
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
