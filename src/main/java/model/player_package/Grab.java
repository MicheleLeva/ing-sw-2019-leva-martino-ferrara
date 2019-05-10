package model.player_package;

import model.Model;
import model.adrenaline_exceptions.EmptySquareException;
import model.map_package.Square;

public class Grab extends Action {
    @Override
    public void perform(Model model , PlayerColor playerColor) throws EmptySquareException {
        Square currentSquare = model.getPlayer(playerColor).getPosition();

        if(currentSquare.isEmpty()){
            throw new EmptySquareException();
        }
        else{
            if(!currentSquare.isSpawn){
                //aggiungi ammo e avvisa tutti
            }
            else{
                //richiedi pagamento/scarta arma/...
            }
        }
    }
}
