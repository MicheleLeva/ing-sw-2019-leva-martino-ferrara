package model.turn;


import model.game.Model;
import model.player.Player;
import model.player.PlayerColor;

import java.util.ArrayList;

/**
 * Class that manages the turn counters.
 * @author Stefano Martino, Michele Leva, Marco Maria Ferrara
 */
public class TurnManager {

    private final Model model;
    private  ArrayList<Player> allPlayers;

    private PlayerColor currentPlayerColor;

    private  int currentPlayerIndex;

    private int lastPlayerIndex; //index of the last player (used for the frenzy turns)

    private boolean gameOver = false;

    private boolean gameOverByAfk = false;

    public boolean isPlayerTurn(PlayerColor playerColor){
        return (currentPlayerColor == playerColor);
    }

    private int currentTurnNumber;

    private boolean frenzy;

    private boolean isGameEnded = false;

    private int currentTurnKillshots;

    /**
     * @param playerColor of the player
     * @return Player of the given color.
     */
    public Player getPlayerFromColor(PlayerColor playerColor){
        Player result = null;

        for (Player allPlayer : allPlayers) {
            if (allPlayer.getPlayerColor() == playerColor)
                result = allPlayer;
        }

        return result;
    }

    public TurnManager(Model model,ArrayList<Player> allPlayers){
        this.frenzy = false;
        this.model = model;
        this.allPlayers = allPlayers;
        currentPlayerIndex = 0;
        currentPlayerColor = allPlayers.get(0).getPlayerColor();
        currentTurnNumber = 1;
        currentTurnKillshots = 0;

    }

    /**
     * Method called at the end of each turn to update the turn counter and select the next player.
     * It also ends the game if there are less than three players which aren't afk.
     */
    public synchronized void update(){
        allPlayers.get(currentPlayerIndex).getActionTree().resetAction();

        resetKillShot();

        if (isFrenzy() && currentPlayerIndex == lastPlayerIndex){
            if (isGameEnded){
                setGameOver(true);

                return;
            } else {
                isGameEnded = true;
            }
        }

        int count = 0;
        for (Player player : getAllPlayers()){
            if (player.isAfk()){
                count++;
            }
        }

        if (getAllPlayers().size() - count < 3){
            setGameOver(true);
            gameOverByAfk = true;
            return;
        }

        getNextPlayer();
        while (allPlayers.get(currentPlayerIndex).isAfk()){
            getNextPlayer();
        }

        currentPlayerColor = allPlayers.get(currentPlayerIndex).getPlayerColor();
    }

    /**
     * Sets the current player index to the next player in the list.
     */
    private void getNextPlayer(){

        if (currentPlayerIndex == allPlayers.size() - 1) //Last player in the list
        {
            updateCurrentTurnNumber();
            currentPlayerIndex = 0;
        }

        else
        {
            currentPlayerIndex++;
        }
    }

    public ArrayList<Player> getAllPlayers(){
        return allPlayers;
    }

    public boolean isGameOver(){
        return (gameOver);
    }

    public boolean isGameOverByAfk() {return gameOverByAfk;}

    public PlayerColor getCurrentPlayerColor(){
        return currentPlayerColor;
    }

    public int getCurrentTurnNumber(){
        return currentTurnNumber;
    }

    /**
     * Increments the turn number.
     */
    private void updateCurrentTurnNumber(){
        currentTurnNumber++;
    }

    public Player getCurrentPlayer(){
        return allPlayers.get(currentPlayerIndex);
    }

    public void setGameOver(boolean gameOver){
        this.gameOver = gameOver;
    }

    /**
     * Increments the killshot counter.
     */
    public void addKillShot(){
        currentTurnKillshots++;
    }

    /**
     * Sets to zero the killshot counter.
     */
    private void resetKillShot(){
        currentTurnKillshots = 0;
    }

    public int numOfKillShot(){
        return currentTurnKillshots;
    }

    public boolean isFrenzy(){
        return this.frenzy;
    }

    /**
     * Prepares the game for the frenzy turns and gives the players their frenzy action boards.
     */
    public void setFrenzy(){
        frenzy = true;
        //notify players
        String message = "Frenzy turn has started.\n";
        model.getGameNotifier().notifyGeneric(message);
        lastPlayerIndex = currentPlayerIndex;

        for (int i = 0; i < allPlayers.size(); i++){
            if (i > lastPlayerIndex){
                allPlayers.get(i).setActionTree(5);
            } else {
                allPlayers.get(i).setActionTree(4);
            }
        }

        for (Player currentPlayer : allPlayers) {
            if (currentPlayer.getPlayerBoard().getDamageCounter().getDamage() == 0 ||
                    currentPlayer.isKillShot() ||
                    currentPlayer.isDead()) {
                //change player's points in frenzy points
                currentPlayer.getPlayerBoard().getPoints().setFrenzyPoints();

            }
        }
    }
}