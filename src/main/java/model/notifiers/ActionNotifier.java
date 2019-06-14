package model.notifiers;

import model.exchanges.messages.*;
import model.player.PlayerColor;
import utils.ViewObservable;

public class ActionNotifier extends ViewObservable<PlayerMessage> {

    /**
     * Sends a list of available actions to the current player's view
     * @param playerColor current player color
     * @param availableAction list of the actions the current player can select
     */
    public void chooseAction(PlayerColor playerColor , String availableAction){
        String message = "Choose an action\n";
        message = message + availableAction;
        PlayerMessage playerMessage = new ChooseActionMessage(message);
        notify(playerMessage, playerColor);
    }

    /**
     * Notifies the player that he is AFK
     * @param playerColor current player color
     */
    public void setPlayerAfk(PlayerColor playerColor){
        String message = "You're afk. If you want to go back to the game insert a character and press enter";
        PlayerMessage playerMessage = new SetAfkMessage(message);

        notify(playerMessage, playerColor);
    }


    /**
     * Asks all players to vote the map they want to use
     * @param playerColor current player color
     */
    public void mapVote(PlayerColor playerColor){
        String toPlayer = "In which map would you like to play?\n";
        toPlayer = toPlayer + "Map 1: suggested for 3 players\n";
        toPlayer = toPlayer + "Map 2: suggested for 4 players\n";
        toPlayer = toPlayer + "Map 3: suggested for 4 players\n";
        toPlayer = toPlayer + "Map 4: suggested for 5 players\n";
        toPlayer = toPlayer + "Insert the number of the chosen map:";

        PlayerMessage playerMessage = new VoteMapMessage(toPlayer);
        notify(playerMessage, playerColor);
    }
}

