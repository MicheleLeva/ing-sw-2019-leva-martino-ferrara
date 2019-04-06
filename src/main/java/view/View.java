package view;

import model.events.*;
import model.player_package.PlayerColor;
import utils.Observable;
import utils.Observer;

public class View extends Observable<PlayerMove> implements Observer<Message>{

    private final PlayerColor playerColor;

    public View (PlayerColor playerColor){
        this.playerColor = playerColor;
    }

    @Override
    public void update(Message message){

        if (this.playerColor == message.getPlayerColor()){
            System.out.println(message.toPlayer());
        }

        else{
            System.out.println(message.toOthers());
        }
    }

    public PlayerColor getPlayerColor(){
        return playerColor;
    }

    public void showAvailableActions(){
        
    }

    public void chooseMove(PlayerMove playerMove){

    }

    public void reportError(String error){

    }


}
