package model.notifiers;

import model.cards.powerups.PowerUp;
import model.cards.weapons.Weapon;
import model.exchanges.messages.*;
import model.map.Square;
import model.player.Player;
import model.player.PlayerColor;
import utils.ViewObservable;

import java.util.ArrayList;

public class WeaponNotifier extends ViewObservable<PlayerMessage> {


    /**
     * Sends the current player a list of the weapons he can use and asks him to select one
     * @param playerColor current player color
     * @param availableWeapons list of the weapons the current player can use
     */
    public void showWeaponCards(PlayerColor playerColor , String availableWeapons){
        String message = "Choose a Weapon: \n";
        message = message +availableWeapons +"\n";

        PlayerMessage playerMessage = new ShowWeaponCardsMessage(message);
        notify(playerMessage, playerColor);
    }


    /**
     * Sends a list of the available fire modes for the chosen weapon to the current player's view
     * @param playerColor current player color
     * @param fireModes lit of the available fire modes for the selected weapon
     */
    public void showFireModes(PlayerColor playerColor, String fireModes){

        PlayerMessage playerMessage = new AskFireModesMessage(fireModes);
        notify(playerMessage, playerColor);
    }

    /**
     * Sends the player a list of targets he can choose from
     * @param playerColor current player color
     * @param availableTargets list of targets to choose from
     * @param targetsNumber maximum number of targets the player can choose
     */
    public void selectTargets(PlayerColor playerColor, String availableTargets, int targetsNumber){
        String message ="Choose a target: \n";
        message = message+availableTargets;

        PlayerMessage playerMessage = new TargetsSelectionMessage(message,targetsNumber);
        notify(playerMessage, playerColor);
    }

    /**
     * Sends a list of squares to choose from to the current player's view
     * @param playerColor current player color
     * @param squares list of available squares
     */
    public void chooseWeaponSquare(PlayerColor playerColor, ArrayList<Square> squares){
        String message = "Choose a square: \n";
        for(Square square : squares)
            message = message + square.getID() + " | ";


        PlayerMessage playerMessage = new ChooseWeaponSquareMessage(message);
        notify(playerMessage, playerColor);
    }


    /**
     * Asks the current player if he wants to reload one of the reloadable weapons
     * @param playerColor current player color
     * @param reloadableWeapon list of weapons the current player can reload
     */
    public void askReload(PlayerColor playerColor , String reloadableWeapon){
        String message = "Reloadable weapon: \n";
        message = message +reloadableWeapon +"\n";
        message = message +"Do you want to reload? [Y/N]\n";

        PlayerMessage playerMessage = new AskReloadMessage(message);
        notify(playerMessage, playerColor);

    }

    /**
     *Sends a list of the powerUps to pay the fire mode with to the current player's view
     */
    public void requestWeaponReload(PlayerColor playerColor , String weapon , String ammo , String powerUp){
        String message;
        message = weapon;
        message = message +"Available ammo: " +ammo +".\n";
        message = message +"Available powerUp: " +powerUp +"\n";

        PlayerMessage playerMessage = new WeaponReloadMessage(message);
        notify(playerMessage, playerColor);
    }
    /**
     *Sends a list of the powerUps to pay the fire mode with to the current player's view
     */
    public void askWeaponPayment(PlayerColor playerColor, ArrayList<PowerUp> powerUps){
        int size = powerUps.size();
        int i = 1;
        String message = "Select the powerUps to pay with:  \n";
        for(PowerUp powerUp : powerUps){
            message = message + i + ". " + powerUp.toString() + "\n";
            i++;
        }

        PlayerMessage playerMessage = new WeaponPaymentMessage(message,size);
        notify(playerMessage, playerColor);
    }
    /**
     *Sends a list of the powerUps to pay the reload cost with to the current player's view
     */
    public void askReloadPayment(PlayerColor playerColor, ArrayList<PowerUp> powerUps){
        int size = powerUps.size();
        int i = 1;
        String message = "Select the power-ups to pay with :   \n";
        for(PowerUp powerUp : powerUps){
            message = message + i + ". " + powerUp.toString() + "\n";
            i++;
        }

        PlayerMessage playerMessage = new ReloadPaymentMessage(message,size);
        notify(playerMessage, playerColor);
    }

    /**
     *Sends a list of the powerUps to pay the pick up cost with to the current player's view
     */
    public void askPickUpPayment(PlayerColor playerColor, ArrayList<PowerUp> powerUps){
        int size = powerUps.size();
        int i = 1;
        String message = "Select the power-ups to pay with :  \n";
        for(PowerUp powerUp : powerUps){
            message = message + i + ". " + powerUp.toString() + "\n";
            i++;
        }

        PlayerMessage playerMessage = new PickUpPaymentMessage(message,size);
        notify(playerMessage, playerColor);
    }


    /**
     * Sends a list of the weapons the player an reload to the current player's view
     * @param playerColor current player color
     * @param pickUpWeapons a list of the weapons that the player can pick up
     */
    public void showPickUpWeapons(PlayerColor playerColor,String pickUpWeapons){
        PlayerMessage playerMessage = new ShowPickUpWeaponsMessage(pickUpWeapons);
        notify(playerMessage,playerColor);

    }

    /**
     * Sends a list of the weapons available for the swap to the current player's view
     * @param player current player
     */
    public void askWeaponSwap(Player player){
        String message = "Choose weapon to swap: \n";
        message = message + player.getResources().showWeapon();
        PlayerMessage playerMessage = new WeaponSwapMessage(message);
        notify(playerMessage, player.getPlayerColor());
    }
}
