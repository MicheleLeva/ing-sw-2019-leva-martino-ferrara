package model.player.action;

import controller.Checks;
import model.game.Ammo;
import model.game.Model;
import model.adrenaline_exceptions.CannotPayException;
import model.adrenaline_exceptions.EmptySquareException;
import model.adrenaline_exceptions.MaxAmmoException;
import model.cards.AmmoCard;
import model.cards.weapons.Weapon;
import model.map.Square;
import model.player.Player;
import model.player.PlayerColor;

import java.util.ArrayList;

public class Grab extends Action {
    /**
     * Performs the grab move
     * @param model modified by the action
     * @param playerColor the color of the player who performed the action
     * @throws EmptySquareException when the player tries to grab from an empty square
     * @throws MaxAmmoException when the player grabs an ammoCard with ammo of the color they already have the maximum of
     * @throws CannotPayException  when the player cannot pay the ammo cost of the weapon to grab
     * @author Stefano Martino
     */
    @Override
    public void perform(Model model , PlayerColor playerColor) throws EmptySquareException, MaxAmmoException,CannotPayException {
        Player currentPlayer = model.getPlayer(playerColor);
        Square currentSquare = currentPlayer.getPosition();
        if(currentSquare.isEmpty()){
            throw new EmptySquareException();
        }
        else{
            if(!currentSquare.isSpawn){
                //ammo card on the current player's square
                AmmoCard currentAmmoCard = currentSquare.getAmmoCard();
                //ammo corresponding to the ammocard drawn
                Ammo currentAmmo = currentAmmoCard.getAmmo();
                //current player's available ammo
                Ammo currentPlayerAmmo = currentPlayer.getResources().getAvailableAmmo();
                //drawable ammo, based on current player's available ammo and the drawn ammocard
                Ammo drawnAmmo = Checks.drawnAmmo(currentAmmo , currentPlayerAmmo);
                //discard the grabbed ammocard
                model.discardAmmo(currentSquare);
                //if the ammocard allows the player to draw a powerup
                if(currentAmmoCard.hasPowerUp() && !Checks.hasMaxPowerUp(currentPlayer)){
                    model.drawPowerUp(playerColor , 1);
                }
                //if the player can draw at least one ammo
                if(drawnAmmo != null){
                    model.addAmmo(playerColor , drawnAmmo);
                    model.updateAction();
                   // currentPlayer.getActionTree().updateAction();
                   // model.chooseAction(currentPlayer.getPlayerColor());
                }
                else{
                    //the player cannot draw further ammo
                    throw new MaxAmmoException();
                }
            }
            else{
                //get spawn weapons
                Weapon[] spawnWeapon = currentSquare.getWeapon();
                //weapons the current player could pay, among the spawn ammo
                ArrayList<Weapon> payableWeapon = new ArrayList<>(3);
                for (int i = 0; i < 3; i++)
                {
                    if(spawnWeapon[i] != null)
                    {
                        Ammo allAmmo = currentPlayer.getResources().getAllAmmo();
                        //checks whether the player can pay the current weapon
                        if(allAmmo.isEnough(spawnWeapon[i].getPickUpCost()))
                        {
                            payableWeapon.add(spawnWeapon[i]);
                        }
                    }
                }

                if(payableWeapon.isEmpty())
                {
                    //if the player cannot pay any of the spawn weapon
                    throw new CannotPayException();
                }
                //asks the player to select one weapon
                model.getCurrent().setPickUpableWeapon(payableWeapon);
                model.showPickUpWeapons(payableWeapon,currentPlayer.getPlayerColor());

            }
        }
    }
}
