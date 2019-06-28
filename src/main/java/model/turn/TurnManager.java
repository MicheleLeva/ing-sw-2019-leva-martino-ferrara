package model.turn;


import model.Model;
import model.player.Player;
import model.player.PlayerColor;

import java.util.ArrayList;

/**
 * Class that manages the turn counters.
 */
public class TurnManager {

    private final Model model;
    private  ArrayList<Player> allPlayers; //Lista di tutti i giocatori in ordine di turno

    private static PlayerColor currentPlayerColor;

    private  int currentPlayerIndex;

    private int lastPlayerIndex; //Ultimo giocatore della partita (usato per il turno frenesia)

    private boolean gameOver = false;

    private boolean gameOverByAfk = false;

    public static boolean isPlayerTurn(PlayerColor playerColor){
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
    public Player getPlayerFromColor(PlayerColor playerColor){ //Dato il colore, ritorna il giocatore corrispondente
        Player result = null;

        for (int i = 0; i < allPlayers.size(); i++){
            if(allPlayers.get(i).getPlayerColor() == playerColor)
                result = allPlayers.get(i);
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
    public void getNextPlayer(){

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

    private void updateCurrentTurnNumber(){
        currentTurnNumber++;
    }

    public Player getCurrentPlayer(){
        return allPlayers.get(currentPlayerIndex);
    }

    public void setGameOver(boolean gameOver){
        this.gameOver = gameOver;
    }

    public void addKillShot(){
        currentTurnKillshots++;
    }

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

        for (int i = 0; i < allPlayers.size(); i++)
        {
            Player currentPlayer = allPlayers.get(i);

            if(     currentPlayer.getPlayerBoard().getDamageCounter().getDamage() == 0 ||
                    currentPlayer.isKillShot() ||
                    currentPlayer.isDead()){
                //change player's points in frenzy points
                currentPlayer.getPlayerBoard().getPoints().setFrenzyPoints();

            }
        }
    }
}