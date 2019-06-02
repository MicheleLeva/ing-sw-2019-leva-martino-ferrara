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

    public void askTargetingScope(PlayerColor playerColor){
        String message = "Want to use the targeting Scope?";
        PlayerMessage playerMessage = new TargetingScopeMessage(message);
        notify(playerMessage,playerColor);
    }

    public void targetingScopeTargets(PlayerColor playerColor, String message){
        PlayerMessage playerMessage = new TargetingScopeSelectionMessage(message);
        notify(playerMessage,playerColor);
    }

    public void askTagbackGrenade(PlayerColor playerColor, PlayerColor opponent, String powerUps){
        String message = opponent.toString() + "hit you. Do you want to use a TagbackGrenade on them?\n";
        message = message + "If you want to use it, insert the index of the grenade you want to use from the ones below";
        message = message + powerUps;
        message = message + "If you don't want to use it, insert 0";
        PlayerMessage playerMessage = new TagbackGrenadeMessage(message);
        notify(playerMessage, playerColor);
    }

    public void requestPowerUpDiscard(PlayerColor playerColor, String powerUps){
        String message = "Choose powerUp to discard: ";
        message = message + powerUps;
        PlayerMessage playerMessage = new DiscardPowerUpMessage(message);
        notify(playerMessage, playerColor);
    }
}
