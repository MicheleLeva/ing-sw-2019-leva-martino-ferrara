package model.events;

import model.GameBoard;
import model.player_package.PlayerColor;
import model.player_package.PlayerBoard;

public abstract class Message {

    private GameBoard gameBoard;
    private PlayerColor playerColor;
    private String playerName;

    public Message (PlayerColor playerColor , String playerName){
        this.playerColor = playerColor;
        this.playerName = playerName;
    }

    public Message(PlayerColor playerColor, String playerName , GameBoard gameBoard){
        this(playerColor , playerName);
        this.gameBoard = gameBoard;
    }

    public PlayerColor getPlayerColor() {
        return playerColor;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public String getPlayerName(){
        return playerName;
    }

    public abstract String toPlayer();

    public abstract String toOthers();

}
