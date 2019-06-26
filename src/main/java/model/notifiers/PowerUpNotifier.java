package model.notifiers;

import model.exchanges.messages.*;
import model.player.Player;
import model.player.PlayerColor;
import utils.ViewObservable;


public class PowerUpNotifier extends ViewObservable<PlayerMessage> {
    /**
     * Sends the list of powerUps to choose from to the players' views
     */
    public void choosePowerUp(PlayerColor playerColor , String availablePowerUp){
        String message = "Choose a PowerUp: \n";
        message = message + "0. Return ";
        message = message +availablePowerUp +"\n";

        PlayerMessage playerMessage = new ChoosePowerUpMessage(message);
        notify(playerMessage, playerColor);
    }
    /**
     * Sends the list of squares to choose from for the teleporter to the current player view
     */
    public void chooseTeleporterSquare(PlayerColor playerColor){
        String message = "Choose a square to teleport to:\n";

        PlayerMessage playerMessage = new ChooseTeleporterSquareMessage(message);
        notify(playerMessage, playerColor);
    }
    /**
     * Sends the list of the opponents to choose from for the Newton PowerUp to the current player view
     */
    public void chooseNewtonOpponent(PlayerColor playerColor , String opponentList){
        String message = "Choose an opponent: \n";
        message = message +opponentList +"\n";

        PlayerMessage playerMessage = new ChooseNewtonOpponentMessage(message);
        notify(playerMessage, playerColor);
    }

    /**
     * Sends the list of the squares to choose from for the Newton powerUp to the current player view
     */
    public void chooseNewtonSquare(PlayerColor playerColor , String squareList){
        String message = "Choose a square: \n";
        message = message +squareList +"\n";

        PlayerMessage playerMessage = new ChooseNewtonSquareMessage(message);
        notify(playerMessage, playerColor);
    }

    /**
     * Asks the current player if he wants to use the targeting scope
     */
    public void askTargetingScope(PlayerColor playerColor){
        String message = "Want to use the targeting Scope? Y/N";
        PlayerMessage playerMessage = new TargetingScopeMessage(message);
        notify(playerMessage,playerColor);
    }

    /**
     * Sends the list of targets to choose from for the targeting scope to the current player view
     */
    public void targetingScopeTargets(PlayerColor playerColor, String message){
        PlayerMessage playerMessage = new TargetingScopeSelectionMessage(message);
        notify(playerMessage,playerColor);
    }

    /**
     * Asks the damaged players if they want to use the tagback grenade
     */
    public void askTagbackGrenade(Player player, Player opponent, String powerUps){
        String message = opponent.getColoredName() + " hit you. Do you want to use a TagbackGrenade on them?\n";
        message = message + "If you want to use it insert the index of the grenade you want to use from the ones below\n";
        message = message + powerUps;
        message = message + "\nIf you don't want to use it insert 0";
        PlayerMessage playerMessage = new TagbackGrenadeMessage(message);
        notify(playerMessage, player.getPlayerColor());
    }

    /**
     * Sends the list of powerUps and asks the current player to discard one of those
     */
    public void requestPowerUpDiscard(PlayerColor playerColor, String powerUps){
        String message = "Choose powerUp to discard: ";
        message = message + powerUps;
        PlayerMessage playerMessage = new DiscardPowerUpMessage(message);
        notify(playerMessage, playerColor);
    }
}
