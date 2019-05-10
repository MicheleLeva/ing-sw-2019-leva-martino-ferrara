package model;

import model.player_package.Player;
import model.player_package.PlayerColor;

public class FirstTurn extends StandardTurn{

    public FirstTurn(Model model){
        super(model);
    }

    @Override
    public synchronized void startTurn(){
        getModel().getTurnManager().startFirstTurn();
        PlayerColor currentPlayerColor = getModel().getTurnManager().getCurrentPlayerColor();
        Player currentPlayer = getModel().getTurnManager().getPlayerFromColor(currentPlayerColor);


        getModel().drawPowerUp(currentPlayer , 2);
        getModel().requestPowerUpDiscard(currentPlayer);
        //getModel().getTurnManager().update();

        while(!getModel().getTurnManager().isFirstTurnEnded()){

        }


        super.startTurn();
    }


}
