package model.notifiers;

import model.exchanges.messages.*;
import model.player.PlayerColor;
import utils.ViewObservable;


public class PowerUpNotifier extends ViewObservable<PlayerMessage> {
    public void choosePowerUp(PlayerColor playerColor , String availablePowerUp){
        String message = "Choose a PowerUp:\n";
        message = message +availablePowerUp +"\n";

        PlayerMessage playerMessage = new ChoosePowerUpMessage(message);
        notify(playerMessage, playerColor);
    }
    public void chooseTeleporterSquare(PlayerColor playerColor){
        String message = "Choose a square to teleport to:\n";

        PlayerMessage playerMessage = new ChooseTeleporterSquareMessage(message);
        notify(playerMessage, playerColor);
    }
    public void chooseNewtonOpponent(PlayerColor playerColor , String opponentList){
        String message = "Choose an opponent: \n";
        message = message +opponentList +"\n";

        PlayerMessage playerMessage = new ChooseNewtonOpponentMessage(message);
        notify(playerMessage, playerColor);
    }

    public void chooseNewtonSquare(PlayerColor playerColor , String squareList){
        String message = "Choose a square: \n";
        message = message +squareList +"\n";

        PlayerMessage playerMessage = new ChooseNewtonSquareMessage(message);
        notify(playerMessage, playerColor);
    }
}
