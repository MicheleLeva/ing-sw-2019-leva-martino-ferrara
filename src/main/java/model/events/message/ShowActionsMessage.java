package model.events.message;

import model.player_package.PlayerColor;

public class ShowActionsMessage extends Message{

    private final String actions;

    public ShowActionsMessage(PlayerColor playerColor, String name, String actions){
        super(playerColor, name);
        this.actions = actions;
    }

    public String getActions() {
        return actions;
    }

    @Override
    public String toPlayer(){
        return ("Your actions are:\n"+ getActions());
    }

    @Override
    public String toOthers(){
        return (getPlayerName() + "is choosing a move");
    }





}
