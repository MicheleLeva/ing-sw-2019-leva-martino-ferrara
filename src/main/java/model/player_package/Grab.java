package model.player_package;

import controller.Checks;
import model.Ammo;
import model.Model;
import model.adrenaline_exceptions.EmptySquareException;
import model.adrenaline_exceptions.MaxAmmoException;
import model.cards.AmmoCard;
import model.cards.PowerUp;
import model.map_package.Square;

public class Grab extends Action {
    @Override
    public void perform(Model model , PlayerColor playerColor) throws EmptySquareException, MaxAmmoException {
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
                Ammo drawnAmmo = Checks.drawnAmmo(currentAmmo , currentPlayerAmmo);

                model.discardAmmo(currentSquare);
                if(currentAmmoCard.hasPowerUp() && !Checks.hasMaxPowerUp(currentPlayer)){
                    model.drawPowerUp(playerColor , 1);
                }

                if(drawnAmmo != null){
                    model.addAmmo(playerColor , drawnAmmo);
                }
                else{
                    throw new MaxAmmoException();
                }
            }
            else{
                //richiedi pagamento/scarta arma/...
            }
        }
    }
}
