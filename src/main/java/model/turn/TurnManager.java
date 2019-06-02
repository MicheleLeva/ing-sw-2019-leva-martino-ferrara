package model.turn;


import model.player.Player;
import model.player.PlayerColor;

import java.util.ArrayList;


public class TurnManager {

    private  ArrayList<Player> allPlayers; //Lista di tutti i giocatori in ordine di turno

    private  static PlayerColor currentPlayerColor;

    private  int currentPlayerIndex;

    private PlayerColor lastPlayerColor; //Ultimo giocatore della partita (usato per il turno frenesia)

    private boolean gameOver = false;

    public static boolean isPlayerTurn(PlayerColor playerColor){
        return (currentPlayerColor == playerColor);
    }

    private int currentTurnNumber;

    private boolean isFirstTurnEnded;

    private boolean isTurnEnded;

    private boolean killshot;

    private boolean frenzy;

    private ArrayList<PlayerColor> currentTurnKillShots;

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
        currentTurnKillShots = new ArrayList<>();
    }

    public synchronized void update(){
        //todo da fare nell'end turn allPlayers.get(currentPlayerIndex).getActionTree().resetPerformedAction();

        if (currentPlayerIndex == allPlayers.size() - 1) //Ultimo giocatore in elenco
        {
            updateCurrentTurnNumber();
            currentPlayerIndex = 0;
            while (getCurrentPlayer().isAfk()){
                currentPlayerIndex ++;
            }
        }

        else
        {
            currentPlayerIndex++;
            while (getCurrentPlayer().isAfk()){
                currentPlayerIndex ++;
            }

        }
        currentPlayerColor = allPlayers.get(currentPlayerIndex).getPlayerColor();
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

    public PlayerColor getLastPlayerColor(){
        return lastPlayerColor;
    }

    public void setGameOver(boolean gameOver){
        this.gameOver = gameOver;
    }

    public boolean isFirstTurnEnded(){
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
    }

    public void addKillShot(PlayerColor playerColor){
        currentTurnKillShots.add(playerColor);
    }

    public void resetKillShot(){
        currentTurnKillShots.clear();
    }

    public int numOfKillShot(){
        return currentTurnKillShots.size();
    }

    public boolean isFrenzy(){
        return this.frenzy;
    }

    public void setFrenzy(boolean frenzy){
        this.frenzy = frenzy;
    }
}