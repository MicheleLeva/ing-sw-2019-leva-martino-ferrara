package model.events.message;

import model.player_package.PlayerColor;


public abstract class Message {

    private PlayerColor playerColor;
    private String playerName;

    public Message (PlayerColor playerColor , String playerName){
        this.playerColor = playerColor;
        this.playerName = playerName;
    }

    public Message(){

    }

    public PlayerColor getPlayerColor() {
        return playerColor;
    }


    public String getPlayerName(){
        return playerName;
    }

    public abstract String toPlayer();

    public abstract String toOthers();

}
