package model;

import model.events.*;
import model.player_package.PlayerColor;
import utils.update.ViewObservable;


public class PowerUpNotifier extends ViewObservable<PlayerMessage> {
    public void choosePowerUp(PlayerColor playerColor , String availablePowerUp){
        String message = "Choose a PowerUp:\n";
        message = message +availablePowerUp +"\n";

        PlayerMessage playerMessage = new ChoosePowerUpMessage(message);
        notify(playerMessage, playerColor);
        //listeners.get(playerColor).update(new ChoosePowerUpMessage(message));
    }
    public void chooseTeleporterSquare(PlayerColor playerColor){
        String message = "Choose a square to teleport to:\n";

        PlayerMessage playerMessage = new ChooseTeleporterSquareMessage(message);
        notify(playerMessage, playerColor);
        //listeners.get(playerColor).update(new ChooseTeleporterSquareMessage(message));
    }
    public void chooseNewtonOpponent(PlayerColor playerColor , String opponentList){
        String message = "Choose an opponent: \n";
        message = message +opponentList +"\n";

        PlayerMessage playerMessage = new ChooseNewtonOpponentMessage(message);
        notify(playerMessage, playerColor);
        //listeners.get(playerColor).update(new ChooseNewtonOpponentMessage(message));
    }

    public void chooseNewtonSquare(PlayerColor playerColor , String squareList){
        String message = "Choose a square: \n";
        message = message +squareList +"\n";

        PlayerMessage playerMessage = new ChooseNewtonSquareMessage(message);
        notify(playerMessage, playerColor);
        //listeners.get(playerColor).update(new ChooseNewtonSquareMessage(message));
    }
}
