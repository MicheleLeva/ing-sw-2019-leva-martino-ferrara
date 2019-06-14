package model.notifiers;

import model.exchanges.messages.GenericMessage;
import model.exchanges.messages.PlayerMessage;
import model.player.Player;
import model.player.PlayerColor;
import utils.ViewObservable;

import java.util.ArrayList;
import java.util.HashMap;

public class GameNotifier extends ViewObservable<PlayerMessage> {


    /**
     * Notifies all players that the current player has used the Run action and tells them where he moved
     */
    public void notifyRun(PlayerColor playerColor , String playerName , int newSquare){
        String toPlayer = "You ";
        String toOthers = playerName +" ";
        String message = "just moved to " +newSquare +".\n";
        toPlayer = toPlayer +message;
        toOthers = toOthers +message;

        PlayerMessage msgToPlayer = new GenericMessage(toPlayer);
        notify(msgToPlayer, playerColor);

        PlayerMessage msgToOthers = new GenericMessage(toOthers);
        notifyOthers(msgToOthers, playerColor);
    }


    /**
     * Notifies all players that the current payer has used the teleporter and tells them where he moved
     */
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

    }


    /**
     * Notifies all players that the current player has used the Newton powerUp also telling them how it was used
     */
    public void notifyNewton(String playerName , String opponentName , PlayerColor playerColor , PlayerColor opponentColor , int newSquare){
        String toPlayer ="You moved " +opponentName +" to: " +newSquare +".\n";
        String toOpponent = playerName +" moved you to: " +newSquare +".\n";
        String toOthers = playerName +" moved " +opponentName +" to: " +newSquare +".\n";

        PlayerMessage msgToPlayer = new GenericMessage(toPlayer);
        notify(msgToPlayer, playerColor);

        PlayerMessage msgToOpponent = new GenericMessage(toOpponent);
        notify(msgToOpponent, opponentColor);

        PlayerMessage msgToOthers = new GenericMessage(toOthers);
        notifyOthers(msgToOthers, playerColor, opponentColor);


    }

    /**
     * Notifies all players that the current player has drawn a particular powerUp
     */
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

    }

    /**
     * Notifies the players that the current player drew ammo from a square and shows what ammo he picked up
     */
    public void notifyDrawAmmo(PlayerColor playerColor , String playerName , String ammo){
        String toPlayer = "You drew: \n";
        String toOthers = playerName +" drew: \n";
        toPlayer = toPlayer + ammo +".\n";
        toOthers = toOthers +ammo +".\n";

        PlayerMessage msgToPlayer = new GenericMessage(toPlayer);
        notify(msgToPlayer, playerColor);

        PlayerMessage msgToOthers = new GenericMessage(toOthers);
        notifyOthers(msgToOthers, playerColor);

    }

    /**
     * Notifies all players that the Shoot Action is ended and sends them the list of damage the players have received
     */
    public void notifyShoot(Player currentPlayer, ArrayList<Player> targets, ArrayList<Player> allPlayers){
        System.out.println("notifyshoot 1 in gamenotifier");
        ArrayList<PlayerColor> targetsColor = new ArrayList<>();
        String toPlayer ="You shot: ";
        String toOpponent = currentPlayer.getPlayerName() +" shot you";
        String toOthers = "";
        if(targets.isEmpty())
            toPlayer = toPlayer + "nobody";
        for(Player player : targets){
            toPlayer = toPlayer+player.getPlayerName();
            targetsColor.add(player.getPlayerColor());
            }
        for(Player player : allPlayers){
            toOthers = toOthers+player.getPlayerName();
            int damage = player.getPlayerBoard().getDamageCounter().getDamageCounter().size();
            while(damage>0){
                toOthers = toOthers+"*";
                damage--;
            }
            System.out.println("notifyshoot 2 in gamenotifier");
            HashMap<PlayerColor,Integer> marks = new HashMap<>(player.getPlayerBoard().getMarkCounter().getMarkCounter());
            for(PlayerColor color : marks.keySet()){
                System.out.println("notifyshoot 3 in gamenotifier");
                int num = marks.get(color);
                System.out.println("notifyshoot 4 in gamenotifier");
                while(num>0){
                    toOthers = toOthers+color.toString().charAt(0);
                    num--;
                    System.out.println("notifyshoot 5 in gamenotifier");

                }
            }
            toOthers = toOthers+"\n";
        }

        System.out.println("notifyshoot  in gamenotifier");

        PlayerMessage msgToPlayer = new GenericMessage(toPlayer+"\n"+toOthers);
        notify(msgToPlayer, currentPlayer.getPlayerColor());

        PlayerMessage msgToOpponent = new GenericMessage(toOpponent);
        PlayerMessage msgToOthers = new GenericMessage(toOthers);

        for(Player player : allPlayers){
            if(!player.getPlayerColor().equals(currentPlayer.getPlayerColor())) {
                if (targetsColor.contains(currentPlayer.getPlayerColor())) {

                    notify(msgToOpponent, player.getPlayerColor());
                } else {
                    notify(msgToOthers, player.getPlayerColor());
                }
            }
        }

        currentPlayer.getActionTree().updateAction();

        //todo
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
//todo javadoc
    /**
     *
     */
    public void notifyGeneric(String message){
        PlayerMessage playerMessage = new GenericMessage(message);
        notify(playerMessage);
    }

    public void notifyPlayer(String message, PlayerColor playerColor){
        PlayerMessage playerMessage = new GenericMessage(message);
        notify(playerMessage, playerColor);
    }

    public void notifyOtherPlayers(String message, PlayerColor playerColor){
        PlayerMessage playerMessage = new GenericMessage(message);
        notifyOthers(playerMessage, playerColor);
    }

    /**
     * notifies all players that a tagBack grenade was used and how it was used
     */
    public void notifyGrenade(PlayerColor user, PlayerColor victim){
        String toOthers = user.toString() + "has used a grenade on " + victim.toString();
        String toPlayer = "You used a grenade on " + victim.toString();
        String toVictim = user.toString() + "has used a grenade on you";

        PlayerMessage messageToOthers = new GenericMessage(toOthers);
        PlayerMessage messageToPlayer = new GenericMessage(toPlayer);
        PlayerMessage messageToVictim = new GenericMessage(toVictim);

        notifyOthers(messageToOthers, user, victim);
        notify(messageToPlayer, user);
        notify(messageToVictim, victim);
    }

    public void notifyMessages(PlayerColor playerColor, String toPlayer, String toOthers){
        PlayerMessage messageToPlayer = new GenericMessage(toPlayer);
        PlayerMessage messageToOthers = new GenericMessage(toOthers);

        notify(messageToPlayer, playerColor);
        notifyOthers(messageToOthers, playerColor);

    }

}
