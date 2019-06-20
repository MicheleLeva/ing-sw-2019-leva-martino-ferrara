package model.turn;


import model.player.Player;
import model.player.PlayerColor;

import java.util.ArrayList;

/**
 * Class that manages the turn counters.
 */
public class TurnManager {

    private  ArrayList<Player> allPlayers; //Lista di tutti i giocatori in ordine di turno

    private static PlayerColor currentPlayerColor;

    private  int currentPlayerIndex;

    private int lastPlayerIndex; //Ultimo giocatore della partita (usato per il turno frenesia)

    private boolean gameOver = false;

    public static boolean isPlayerTurn(PlayerColor playerColor){
        return (currentPlayerColor == playerColor);
    }

    private int currentTurnNumber;

    private boolean isFirstTurnEnded;

    private boolean isTurnEnded;

    private boolean killshot;

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

    public TurnManager(ArrayList<Player> allPlayers){
        this.frenzy = false;
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
            System.out.println(count + " AFK players!");
            System.out.println("Game over!");
            setGameOver(true);
            return;
        }

        if (count > 0) {
            System.out.println(count + " AFK players!");
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

    //todo servono?
    /*public boolean isFirstTurnEnded(){
        return isFirstTurnEnded;
    }

    public boolean isTurnEnded(){
        return isTurnEnded;
    }

    public void endFirstTurn(){
        isFirstTurnEnded = true;
    }

    public  void startFirstTurn(){
        isFirstTurnEnded = false;
    }

    public void endTurn(){
        isTurnEnded = true;
    }

    public void startTurn(){
        isTurnEnded = false;
    }

    public boolean isKillshot(){
        return killshot;
    }

    public void setKillshot(boolean killshot){
        this.killshot = killshot;
    }*/

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

            if(currentPlayer.getPlayerBoard().getDamageCounter().getDamage() == 0){
                //change player's points in frenzy points
                currentPlayer.getPlayerBoard().getPoints().setFrenzyPoints();

            }
        }
    }
}