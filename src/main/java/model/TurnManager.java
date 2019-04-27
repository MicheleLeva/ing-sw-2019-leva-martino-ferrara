package model;


import model.player_package.Player;
import model.player_package.PlayerColor;

import java.util.ArrayList;


public class TurnManager { //cambiare nome in TurnManager

    private  ArrayList<Player> allPlayers; //In ordine

    private  static PlayerColor currentPlayerColor;

    private  int currentPlayerIndex;

    private PlayerColor lastPlayerColor; //Used for the frenzy turn

    private boolean gameOver = false;

    public static boolean isPlayerTurn(PlayerColor playerColor){
        return (currentPlayerColor == playerColor);
    }

    private int currentTurnNumber;

    public Player getPlayerFromColor(PlayerColor playerColor){
        Player result = null;

        for (int i = 0; i < allPlayers.size(); i++){
            if(allPlayers.get(i).getPlayerColor() == playerColor)
                result = allPlayers.get(i);
        }

        return result;
    }

    public TurnManager(ArrayList<Player> allPlayers){
        this.allPlayers = allPlayers;
        currentPlayerIndex = 0;
        currentPlayerColor = allPlayers.get(0).getPlayerColor();
        currentTurnNumber = 1;
    }

    public synchronized void update(){
        allPlayers.get(currentPlayerIndex).getActionTree().resetPerformedAction();
        if (currentPlayerIndex == allPlayers.size() - 1) //Ultimo giocatore in elenco
        {
            updateCurrentTurnNumber();
            currentPlayerIndex = 0;
            currentPlayerColor = allPlayers.get(0).getPlayerColor();
        }

        else
        {
            currentPlayerIndex++;
            currentPlayerColor = allPlayers.get(currentPlayerIndex).getPlayerColor();
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

    public PlayerColor getLastPlayerColor(){
        return lastPlayerColor;
    }

    public void setGameOver(boolean gameOver){
        this.gameOver = gameOver;
    }

}