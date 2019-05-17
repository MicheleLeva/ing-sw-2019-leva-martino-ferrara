package model;

import model.events.GenericMessage;
import model.events.PlayerMessage;
import model.player_package.Player;
import model.player_package.PlayerColor;
import utils.update.GameUpdate;
import utils.update.ViewObservable;

import java.util.Map;

import java.util.ArrayList;
import java.util.HashMap;

public class GameNotifier extends ViewObservable<PlayerMessage> {

    public void notifyRun(PlayerColor playerColor , String playerName , String newSquare){
        String toPlayer = "You ";
        String toOthers = playerName +" ";
        String message = "just moved to " +newSquare +".\n";
        toPlayer = toPlayer +message;
        toOthers = toOthers +message;

        PlayerMessage msgToPlayer = new GenericMessage(toPlayer);
        notify(msgToPlayer, playerColor);

        PlayerMessage msgToOthers = new GenericMessage(toOthers);
        notifyOthers(msgToOthers, playerColor);

        /*
        for(Map.Entry<PlayerColor , GameUpdate> entry : listeners.entrySet()){
            PlayerColor currentPlayerColor = entry.getKey();
            if(currentPlayerColor == playerColor){
                entry.getValue().update(new GenericMessage(toPlayer));
            }
            else{
                entry.getValue().update(new GenericMessage(toOthers));
            }
        }
         */
    }

    public void notifyTeleporter(PlayerColor playerColor , String playerName , String newSquare){
        String toPlayer = "You ";
        String toOthers = playerName +" ";
        String message = "just teleported to " +newSquare +".\n";
        toPlayer = toPlayer +message;
        toOthers = toOthers +message;

        PlayerMessage msgToPlayer = new GenericMessage(toPlayer);
        notify(msgToPlayer, playerColor);

        PlayerMessage msgToOthers = new GenericMessage(toOthers);
        notifyOthers(msgToOthers, playerColor);

        /*for(Map.Entry<PlayerColor , GameUpdate> entry : listeners.entrySet()){
            PlayerColor currentPlayerColor = entry.getKey();
            if(currentPlayerColor == playerColor){
                entry.getValue().update(new GenericMessage(toPlayer));
            }
            else{
                entry.getValue().update(new GenericMessage(toOthers));
            }
        }*/
    }

    public void notifyNewton(String playerName , String opponentName , PlayerColor playerColor , PlayerColor opponentColor , String newSquare){
        String toPlayer ="You moved " +opponentName +" to: " +newSquare +".\n";
        String toOpponent = playerName +" moved you to: " +newSquare +".\n";
        String toOthers = playerName +" moved " +opponentName +" to: " +newSquare +".\n";

        PlayerMessage msgToPlayer = new GenericMessage(toPlayer);
        notify(msgToPlayer, playerColor);

        PlayerMessage msgToOpponent = new GenericMessage(toOpponent);
        notify(msgToOpponent, opponentColor);

        PlayerMessage msgToOthers = new GenericMessage(toOthers);
        notifyOthers(msgToOthers, playerColor, opponentColor);

        /*for(Map.Entry<PlayerColor , GameUpdate> entry : listeners.entrySet()){
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
        }*/

    }
    public void notifyDrawPowerUp(PlayerColor playerColor ,String playerName , String powerUpList , int num){
        String toPlayer = "You drew: " +powerUpList +".\n";
        String toOthers = playerName +" drew " +num +" powerup";
        if(num > 1){
            toOthers = toOthers +"s";
        }
        toOthers = toOthers +".\n";

        PlayerMessage msgToPlayer = new GenericMessage(toPlayer);
        notify(msgToPlayer, playerColor);

        PlayerMessage msgToOthers = new GenericMessage(toOthers);
        notifyOthers(msgToOthers, playerColor);

        /*for(Map.Entry<PlayerColor , GameUpdate> entry : listeners.entrySet()){
            PlayerColor currentPlayerColor = entry.getKey();
            if(currentPlayerColor == playerColor){
                entry.getValue().update(new GenericMessage(toPlayer));
            }
            else{
                entry.getValue().update(new GenericMessage(toOthers));
            }
        }*/
    }

    public void notifyDrawAmmo(PlayerColor playerColor , String playerName , String ammo){
        String toPlayer = "You drew: \n";
        String toOthers = playerName +" drew: \n";
        toPlayer = toPlayer + ammo +".\n";
        toOthers = toOthers +ammo +".\n";

        PlayerMessage msgToPlayer = new GenericMessage(toPlayer);
        notify(msgToPlayer, playerColor);

        PlayerMessage msgToOthers = new GenericMessage(toOthers);
        notifyOthers(msgToOthers, playerColor);

        /*for(Map.Entry<PlayerColor , GameUpdate> entry : listeners.entrySet()){
            PlayerColor currentPlayerColor = entry.getKey();
            if(currentPlayerColor == playerColor){
                entry.getValue().update(new GenericMessage(toPlayer));
            }
            else{
                entry.getValue().update(new GenericMessage(toOthers));
            }
        }*/
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


        PlayerMessage msgToPlayer = new GenericMessage(toPlayer+"\n"+toOthers);
        notify(msgToPlayer, currentPlayer.getPlayerColor());

        PlayerMessage msgToOpponent = new GenericMessage(toOpponent);
        PlayerMessage msgToOthers = new GenericMessage(toOthers);

        for(Player player : allPlayers){
            if(targetsColor.contains(currentPlayer.getPlayerColor())){

                notify(msgToOpponent, player.getPlayerColor());
            }
            else{
                notify(msgToOthers, player.getPlayerColor());
            }
        }

        /*for(Map.Entry<PlayerColor , GameUpdate> entry : listeners.entrySet()){
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
        }*/
    }

}
