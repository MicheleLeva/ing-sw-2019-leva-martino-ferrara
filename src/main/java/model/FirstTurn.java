package model;

import model.player_package.Player;
import model.player_package.PlayerColor;

public class FirstTurn extends Turn {

    public FirstTurn(Model model){
        super(model);
    }

    @Override
    public void startTurn(){

        PlayerColor currentPlayerColor = getModel().getTurnManager().getCurrentPlayerColor();
        Player currentPlayer = getModel().getTurnManager().getPlayerFromColor(currentPlayerColor);

        getModel().drawPowerUp(currentPlayer , 2);
        getModel().requestPowerUpDiscard(currentPlayer);

    }
}
