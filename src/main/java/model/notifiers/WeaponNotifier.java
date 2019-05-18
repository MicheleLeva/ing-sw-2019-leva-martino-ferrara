package model.notifiers;

import model.exchanges.messages.*;
import model.map.Square;
import model.player.PlayerColor;
import utils.ViewObservable;

import java.util.ArrayList;

public class WeaponNotifier extends ViewObservable<PlayerMessage> {

    public void showWeaponCards(PlayerColor playerColor , String availableWeapons){
        String message = "Choose a Weapon:\n";
        message = message +availableWeapons +"\n";

        PlayerMessage playerMessage = new ShowWeaponCardsMessage(message);
        notify(playerMessage, playerColor);
    }

    public void showFireModes(PlayerColor playerColor, String fireModes){

        PlayerMessage playerMessage = new AskFireModesMessage(fireModes);
        notify(playerMessage, playerColor);
    }

    public void selectTargets(PlayerColor playerColor, String availableTargets, int targetsNumber){
        String message ="Choose a target: \n";
        message = message+availableTargets;

        PlayerMessage playerMessage = new TargetsSelectionMessage(message,targetsNumber);
        notify(playerMessage, playerColor);
    }

    public void chooseWeaponSquare(PlayerColor playerColor, ArrayList<Square> squares){
        String message = "Choose a square to move to:\n";
        for(Square square : squares)
            message = message + square.getID();

        PlayerMessage playerMessage = new ChooseWeaponSquareMessage(message);
        notify(playerMessage, playerColor);
    }

    public void askReload(PlayerColor playerColor , String reloadableWeapon){
        String message = "Reloadable weapon: \n";
        message = message +reloadableWeapon +"\n";
        message = message +"Do you want to reload? [Y/N]\n";

        PlayerMessage playerMessage = new AskReloadMessage(message);
        notify(playerMessage, playerColor);

    }

    public void requestWeaponReload(PlayerColor playerColor , String weapon , String ammo , String powerUp){
        String message;
        message = weapon;
        message = message +"Available ammo: " +ammo +".\n";
        message = message +"Available powerUp: " +powerUp +"\n";

        PlayerMessage playerMessage = new WeaponReloadMessage(message);
        notify(playerMessage, playerColor);
    }

}