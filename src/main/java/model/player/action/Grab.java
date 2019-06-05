package model.player.action;

import controller.Checks;
import model.Ammo;
import model.Model;
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
    @Override
    public void perform(Model model , PlayerColor playerColor) throws EmptySquareException, MaxAmmoException,CannotPayException {
        Player currentPlayer = model.getPlayer(playerColor);
        Square currentSquare = currentPlayer.getPosition();
        if(currentSquare.isEmpty()){
            throw new EmptySquareException();
        }
        else{
            if(!currentSquare.isSpawn){
                //aggiungi ammo e avvisa tutti
                AmmoCard currentAmmoCard = currentSquare.getAmmoCard();
                Ammo currentAmmo = currentAmmoCard.getAmmo();
                Ammo currentPlayerAmmo = currentPlayer.getResources().getAvailableAmmo();

                System.out.print("Ammo correnti: " +currentPlayerAmmo.toString());
                System.out.print("\nAmmo sullo square: " +currentAmmo.toString());

                Ammo drawnAmmo = Checks.drawnAmmo(currentAmmo , currentPlayerAmmo);

                model.discardAmmo(currentSquare);
                if(currentAmmoCard.hasPowerUp() && !Checks.hasMaxPowerUp(currentPlayer)){
                    model.drawPowerUp(playerColor , 1);
                }

                if(drawnAmmo != null){
                    model.addAmmo(playerColor , drawnAmmo);
                    model.updateAction();
                   // currentPlayer.getActionTree().updateAction();
                   // model.chooseAction(currentPlayer.getPlayerColor());
                }
                else{
                    throw new MaxAmmoException();
                }
            }
            else{
                //richiedi pagamento/scarta arma/...
                //mostra armi che può pagare tra quelle nel punto generazione
                Weapon spawnWeapon[] = currentSquare.getWeapon();
                ArrayList<Weapon> payableWeapon = new ArrayList<Weapon>(3);
                for (int i = 0; i < 3; i++)
                {
                    if(spawnWeapon[i] != null)
                    {
                        Ammo allAmmo = currentPlayer.getResources().getAllAmmo();
                        if(allAmmo.isEnough(spawnWeapon[i].getPickUpCost()))
                        {
                            payableWeapon.add(spawnWeapon[i]);
                        }
                    }
                }

                //for(Weapon weapon : payableWeapon)

                if(payableWeapon.isEmpty())
                {
                    throw new CannotPayException();
                }
                //seleziona arma
                model.getCurrent().setPickUpableWeapon(payableWeapon);
                model.showPickUpWeapons(payableWeapon,currentPlayer.getPlayerColor());
                //paga(askpICKuPpAYMENT)
                //se ha tre armi chiedi di scartare
            }
        }
    }
}
