package model.events.message;

import model.player_package.PlayerColor;

public class StartMessage extends Message {

    private final boolean powerUps;

    public boolean hasPowerUps(){
        return powerUps;
    }

    public StartMessage(PlayerColor playerColor, String name, boolean powerUps){
        super(playerColor, name);
        this.powerUps = powerUps;
    }


    @Override
    public String toPlayer(){
        return ("Your possible actions are:\n1. See your cards\n2.Make an action\n3.Use a Power Up");
        //il giocatore può sempre vedere le sue carte e scegliere un azione
        //Serve il controllo sul tipo di power a sua disposizione
    }

    @Override
    public String toOthers(){
        return (getPlayerName() +"is choosing what to do");
    }
}
