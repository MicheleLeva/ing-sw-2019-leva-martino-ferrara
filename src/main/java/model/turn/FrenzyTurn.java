package model.turn;

import model.Model;
import model.player.Player;
import model.player.PlayerColor;

public class FrenzyTurn extends Turn {

    public FrenzyTurn(Model model){
        super(model);
    }

    @Override
    public void startTurn(){
        PlayerColor currentPlayerColor = getModel().getTurnManager().getCurrentPlayerColor();
        Player currentPlayer = getModel().getTurnManager().getPlayerFromColor(currentPlayerColor);

        //Start turn

        getModel().endFrenzyTurn(currentPlayer);
    }

    @Override
    public void notifyTurn(){

    }
}
