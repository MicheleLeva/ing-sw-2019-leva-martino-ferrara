package model;

import model.events.GenericMessage;
import model.player_package.Player;
import model.player_package.PlayerColor;
import utils.notify.GameNotify;
import utils.update.GameUpdate;
import model.events.ShootMessage;

import java.util.Map;

import java.util.ArrayList;
import java.util.HashMap;

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

    public void notifyShoot(Player currentPlayer, ArrayList<Player> targets, ArrayList<Player> allPlayers){
        ArrayList<PlayerColor> targetsColor = new ArrayList<>();
        String toPlayer ="You shot: ";
        String toOpponent = currentPlayer.getName() +" shot you";
        String toOthers = "";

        for(Player player : targets){
            toPlayer = toPlayer+player.getName();
            targetsColor.add(player.getPlayerColor());
            }
        for(Player player : allPlayers){
            String name = player.getName();
            toOthers = toOthers+player.getName();
            int damage = player.getPlayerBoard().getDamageCounter().getDamageCounter().size();
            while(damage>0){
                toOthers = toOthers+" *";
                damage--;
            }
            HashMap<PlayerColor,Integer> marks = new HashMap<>(player.getPlayerBoard().getMarkCounter().getMarkCounter());
            for(PlayerColor color : marks.keySet()){
                int num = marks.get(color);
                while(num>0){
                    toOthers = toOthers+color.toString().charAt(0);
                }
            }
            toOthers = toOthers+"\n";
        }
        for(Map.Entry<PlayerColor , GameUpdate> entry : listeners.entrySet()){
            PlayerColor currentPlayerColor = entry.getKey();
            if(currentPlayerColor == currentPlayer.getPlayerColor()){
                entry.getValue().update(new GenericMessage(toPlayer+"\n"+toOthers));
            }
            else{
                if(targetsColor.contains(currentPlayerColor)){
                    entry.getValue().update(new GenericMessage(toOpponent));
                }
                else{
                    entry.getValue().update(new GenericMessage(toOthers));
                }
            }
        }
    }

}
