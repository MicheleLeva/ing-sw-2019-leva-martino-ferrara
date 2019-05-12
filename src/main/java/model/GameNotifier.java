package model;

import model.events.GenericMessage;
import model.player_package.PlayerColor;
import utils.notify.GameNotify;
import utils.update.GameUpdate;

import java.util.Map;

public class GameNotifier extends GameNotify {
    public void notifyRun(PlayerColor playerColor , String playerName , String newSquare){
        String toPlayer = "You ";
        String toOthers = playerName +" ";
        String message;
        message = playerName +"just moved to " +newSquare +".\n";
        for(Map.Entry<PlayerColor , GameUpdate> entry : listeners.entrySet()){
            PlayerColor currentPlayerColor = entry.getKey();
            if(currentPlayerColor == playerColor){
                entry.getValue().update(new GenericMessage(toPlayer));
            }
            else{
                entry.getValue().update(new GenericMessage(toOthers));
            }
        }
    }

    public void notifyTeleporter(PlayerColor playerColor , String playerName , String newSquare){
        String toPlayer = "You ";
        String toOthers = playerName +" ";
        String message = "just teleported to " +newSquare +".\n";
        toPlayer = toPlayer +message;
        toOthers = toOthers +message;
        for(Map.Entry<PlayerColor , GameUpdate> entry : listeners.entrySet()){
            PlayerColor currentPlayerColor = entry.getKey();
            if(currentPlayerColor == playerColor){
                entry.getValue().update(new GenericMessage(toPlayer));
            }
            else{
                entry.getValue().update(new GenericMessage(toOthers));
            }
        }
    }

    public void notifyNewton(String playerName , String opponentName , PlayerColor playerColor , PlayerColor opponentColor , String newSquare){
        String toPlayer ="You moved " +opponentName +" to: " +newSquare +".\n";
        String toOpponent = playerName +" moved you to: " +newSquare +".\n";
        String toOthers = playerName +" moved " +opponentName +" to: " +newSquare +".\n";
        for(Map.Entry<PlayerColor , GameUpdate> entry : listeners.entrySet()){
            PlayerColor currentPlayerColor = entry.getKey();
            if(currentPlayerColor == playerColor){
                entry.getValue().update(new GenericMessage(toPlayer));
            }
            else{
               if(currentPlayerColor == opponentColor){
                   entry.getValue().update(new GenericMessage(toOpponent));
               }
               else{
                   entry.getValue().update(new GenericMessage(toOthers));
               }
            }
        }

    }
    public void notifyDrawPowerUp(PlayerColor playerColor ,String playerName , String powerUpList , int num){
        String toPlayer = "You drew: " +powerUpList +".\n";
        String toOthers = playerName +" drew " +num +" powerup";
        if(num > 1){
            toOthers = toOthers +"s";
        }
        toOthers = toOthers +".\n";

        for(Map.Entry<PlayerColor , GameUpdate> entry : listeners.entrySet()){
            PlayerColor currentPlayerColor = entry.getKey();
            if(currentPlayerColor == playerColor){
                entry.getValue().update(new GenericMessage(toPlayer));
            }
            else{
                entry.getValue().update(new GenericMessage(toOthers));
            }
        }
    }

    public void notifyDrawAmmo(PlayerColor playerColor , String playerName , String ammo){
        String toPlayer = "You drew: \n";
        String toOthers = playerName +" drew: \n";
        toPlayer = toPlayer + ammo +".\n";
        toOthers = toOthers +ammo +".\n";
        for(Map.Entry<PlayerColor , GameUpdate> entry : listeners.entrySet()){
            PlayerColor currentPlayerColor = entry.getKey();
            if(currentPlayerColor == playerColor){
                entry.getValue().update(new GenericMessage(toPlayer));
            }
            else{
                entry.getValue().update(new GenericMessage(toOthers));
            }
        }
    }



}
