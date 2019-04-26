package model;


import model.player_package.Player;
import model.player_package.PlayerColor;

import java.util.ArrayList;


public class GameManager { //cambiare nome in GameManager

    private  ArrayList<Player> allPlayers; //In ordine

    private  static PlayerColor currentPlayerColor;

    private  int currentPlayerColorIndex;

    private boolean gameOver = false;

    public static boolean isPlayerTurn(PlayerColor playerColor){
        return (currentPlayerColor == playerColor);
    }

    public Player getPlayerFromColor(PlayerColor playerColor){
        Player result = null;

        for (int i = 0; i < allPlayers.size(); i++){
            if(allPlayers.get(i).getPlayerColor() == playerColor)
                result = allPlayers.get(i);
        }

        return result;
    }

    public GameManager(ArrayList<Player> allPlayers){
        this.allPlayers = allPlayers;
        currentPlayerColorIndex = 0;
        currentPlayerColor = allPlayers.get(0).getPlayerColor();
    }

    public  void changeCurrentPlayer(){
        if (currentPlayerColorIndex == allPlayers.size()) //Ultimo giocatore in elenco
        {
            currentPlayerColorIndex = 0;
            currentPlayerColor = allPlayers.get(0).getPlayerColor();
        }

        else
        {
            currentPlayerColorIndex++;
            currentPlayerColor = allPlayers.get(currentPlayerColorIndex).getPlayerColor();
        }
    }

    public ArrayList<Player> getAllPlayers(){
        return allPlayers;
    }

    public boolean isGameOver(){
        return (gameOver);
    }
}