package model.events.message;

import model.player_package.PlayerColor;

public class PowerUpDiscardMessage extends Message {
    private final String powerUpList;
    private final int num;

    public PowerUpDiscardMessage(PlayerColor playerColor , String playerName , String powerUpList , int num){
        super(playerColor , playerName);
        this.powerUpList = powerUpList;
        this.num = num;
    }

    @Override
    public String toPlayer() {
        String toPlayer = null;
        toPlayer = "Choose one of the following PowerUp to be discarded ";
        toPlayer = toPlayer + "by enterin a number between 1 and " +num +"\n";
        toPlayer = toPlayer + powerUpList +"\n";
        toPlayer = toPlayer + "That PowerUp will be shown to the other players ";
        toPlayer = toPlayer + "and your position will be set to the generation point of the PowerUp's color.\n";
        return toPlayer;
    }

    @Override
    public String toOthers(){
        return getPlayerName() +" is choosing the PowerUp to discard";
    }
}
