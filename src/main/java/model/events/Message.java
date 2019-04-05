package model.events;

import model.GameBoard;
import model.player_package.Player;
import model.player_package.PlayerBoard;

public abstract class Message {

    private GameBoard gameBoard;
    private Player player;

    public void moveMessage(Player player, PlayerBoard playerBoard){

    }

    public Player getPlayer() {
        return player;
    }

    public GameBoard getPlayerBoard() {
        return gameBoard;
    }
}
