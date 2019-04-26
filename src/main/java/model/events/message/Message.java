package model.events.message;

import model.player_package.PlayerColor;


public class Message {

    private PlayerColor playerColor;
    private String playerName;

    private  String toPlayer;
    private  String toOthers;

    public Message(PlayerColor playerColor , String toPlayer , String toOthers){
        this.playerColor = playerColor;
        this.toPlayer = toPlayer;
        this.toOthers = toOthers;
    }

    protected Message (PlayerColor playerColor , String playerName){
        this.playerColor = playerColor;
        this.playerName = playerName;
    }

    public PlayerColor getPlayerColor() {
        return playerColor;
    }


    public String getPlayerName(){
        return playerName;
    }

    public  String toPlayer(){
        return toPlayer;
    }

    public  String toOthers(){
        return toOthers;
    }

}
