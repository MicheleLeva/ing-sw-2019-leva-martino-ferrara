package model.notifiers;

import model.exchanges.messages.GenericMessage;
import model.exchanges.messages.PlayerMessage;
import model.player.Player;
import model.player.PlayerColor;
import utils.ViewObservable;

import java.util.ArrayList;

public class GameNotifier extends ViewObservable<PlayerMessage> {


    /**
     * Notifies all players that the current player has used the Run action and tells them where he moved.
     * @param playerColor of the player that run.
     * @param playerName of the player that run.
     * @param newSquare position of the player.
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
     * Notifies all players that the current player has used the teleporter and tells them where he moved.
     * @param playerColor of the player that teleported.
     * @param playerName of the player that teleported.
     * @param newSquare position of the player that teleported.
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
     * Notifies all players that the current player has used the Newton powerUp also telling them how it was used.
     * @param playerName of the player that used the Newton.
     * @param opponentName of the target of the Newton.
     * @param playerColor of the player that used the Newton.
     * @param opponentColor of the target of the Newton.
     * @param newSquare position of the target that has been moved.
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
     * Notifies all players that the current player has drawn a number of powerUp.
     * @param playerColor of the player that drew the PowerUp.
     * @param playerName of the player that drew the PowerUp.
     * @param powerUpList list of PowerUp drawn (shown to the player that drew them).
     * @param num the number of PowerUp drawn (shown to the other players).
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
     * Notifies the players that the current player drew ammo from a square and shows what ammo he picked up.
     * @param playerColor of the player that drew the ammo.
     * @param playerName of the player that drew the ammo.
     * @param ammo picked up by the player.
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
     * Notifies all players that the Shoot Action is ended and sends them the list of damage the players have received.
     * @param currentPlayer player that shoot.
     * @param targets hit by the player.
     * @param allPlayers list of all the player (used to send the correct messages to the hit players).
     */
    public void notifyShoot(Player currentPlayer, ArrayList<Player> targets, ArrayList<Player> allPlayers){
        ArrayList<PlayerColor> targetsColor = new ArrayList<>();
        String toPlayer ="You shot: ";
        String toOpponent = currentPlayer.getColoredName() +" shot: ";
        String toOthers = "";
        if(targets.isEmpty()) {
            toPlayer = toPlayer + "nobody";
            toOpponent = toOpponent + "nobody";
        }
        for(Player player : targets){
            toPlayer = toPlayer+player.getColoredName() + " | ";
            toOpponent = toOpponent+player.getColoredName() + " | ";
            targetsColor.add(player.getPlayerColor());
            }
        for(Player player : allPlayers){
            toOthers = toOthers+player.getColoredName();
            //int damage = player.getPlayerBoard().getDamageCounter().getDamageCounter().size();
            if(player.getPosition()!=null)
                toOthers = toOthers + " Position: " + player.getPosition().getID() + " |";
            else
                toOthers = toOthers + " Position: Not yet respawned |";
            toOthers = toOthers + " Damage: " + player.getPlayerBoard().getDamageCounter().printDamageCounter() + " |";
            toOthers = toOthers + " Marks: " + player.getPlayerBoard().getMarkCounter().printMarkCounter() + " |";
            toOthers = toOthers + " " + player.getResources().showAmmo() + " |";
            toOthers = toOthers+"\n";
        }



        PlayerMessage msgToPlayer = new GenericMessage(toPlayer+"\n"+toOthers);
        notify(msgToPlayer, currentPlayer.getPlayerColor());

        PlayerMessage msgToOpponent = new GenericMessage("You have been shot!!!\n"+toOpponent+"\n"+toOthers);
        PlayerMessage msgToOthers = new GenericMessage(toOpponent+"\n"+toOthers);

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

    }

    /**
     * Notifies a message to all the player.
     * @param message to be notified.
     */
    public void notifyGeneric(String message){
        PlayerMessage playerMessage = new GenericMessage(message);
        notify(playerMessage);
    }

    /**
     * Notifies a message to the given player.
     * @param message to be notified.
     * @param playerColor of the player to be notified.
     */
    public void notifyPlayer(String message, PlayerColor playerColor){
        PlayerMessage playerMessage = new GenericMessage(message);
        notify(playerMessage, playerColor);
    }

    /**
     * Notifies a message to all but the chosen player.
     * @param message to be notified.
     * @param playerColor of the player not to be notified.
     */
    public void notifyOtherPlayers(String message, PlayerColor playerColor){
        PlayerMessage playerMessage = new GenericMessage(message);
        notifyOthers(playerMessage, playerColor);
    }

    /**
     * Notifies all players that a TagBack grenade was used and how it was used.
     * @param user of the grenade.
     * @param victim of the grenade.
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

    /**
     * Notifies a message to a player and another message to the others.
     * @param playerColor of the player that receives the first message.
     * @param toPlayer to be sent to the player.
     * @param toOthers to be sent to the other players.
     */
    public void notifyMessages(PlayerColor playerColor, String toPlayer, String toOthers){
        PlayerMessage messageToPlayer = new GenericMessage(toPlayer);
        PlayerMessage messageToOthers = new GenericMessage(toOthers);

        notify(messageToPlayer, playerColor);
        notifyOthers(messageToOthers, playerColor);

    }

}
