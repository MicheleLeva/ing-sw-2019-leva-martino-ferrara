package model.notifiers;

import model.cards.powerups.PowerUp;
import model.exchanges.messages.*;
import model.map.Square;
import model.player.Player;
import model.player.PlayerColor;
import utils.ViewObservable;

import java.util.ArrayList;

public class WeaponNotifier extends ViewObservable<PlayerMessage> {

    private final String POWERUP_PAYMENT = "Select the indexes of the power-ups to pay with. Insert 0 to end the selection:\n";

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
     * Sends a list of the weapons to reload and available ammo and powerUp to the current player's view.
     * @param playerColor of the player that has to pay.
     * @param weapon list of reloadable weapons.
     * @param ammo of the player.
     * @param powerUp of the player.
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
     * Sends a list of the powerUps to pay the fire mode with to the current player's view
     * @param playerColor of the player.
     * @param powerUps to pay the fire mode.
     */
    public void askWeaponPayment(PlayerColor playerColor, ArrayList<PowerUp> powerUps){
        int size = powerUps.size();
        int i = 1;
        StringBuilder message = new StringBuilder();
        message.append(POWERUP_PAYMENT);
        for(PowerUp powerUp : powerUps){
            message.append(i);
            message.append(". ");
            message.append(powerUp.toString());
            message.append("\n");
            i++;
        }

        PlayerMessage playerMessage = new WeaponPaymentMessage(message.toString() ,size);
        notify(playerMessage, playerColor);
    }

    /**
     * Sends a list of the powerUps to pay the reload cost with to the current player's view.
     * @param playerColor of the player.
     * @param powerUps to pay the reload.
     */
    public void askReloadPayment(PlayerColor playerColor, ArrayList<PowerUp> powerUps){
        int size = powerUps.size();
        int i = 1;
        StringBuilder message = new StringBuilder();
        message.append(POWERUP_PAYMENT);
        for(PowerUp powerUp : powerUps){
            message.append(i);
            message.append(". ");
            message.append(powerUp.toString());
            message.append("\n");
            i++;
        }

        PlayerMessage playerMessage = new ReloadPaymentMessage(message.toString() ,size);
        notify(playerMessage, playerColor);
    }

    /**
     * Sends a list of the powerUps to pay the pick up cost with to the current player's view.
     * @param playerColor of the player.
     * @param powerUps to pay the pick up of the weapon.
     */
    public void askPickUpPayment(PlayerColor playerColor, ArrayList<PowerUp> powerUps){
        int size = powerUps.size();
        int i = 1;
        StringBuilder message = new StringBuilder();
        message.append(POWERUP_PAYMENT);
        for(PowerUp powerUp : powerUps){
            message.append(i);
            message.append(". ");
            message.append(powerUp.toString());
            message.append("\n");
            i++;
        }

        PlayerMessage playerMessage = new PickUpPaymentMessage(message.toString(),size);
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
