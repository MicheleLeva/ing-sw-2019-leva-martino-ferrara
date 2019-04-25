package model;


import model.player_package.Player;
import model.player_package.PlayerColor;


public class TurnManager {

    private static PlayerColor currentPlayerColor;

    public static void changeCurrentPlayer(PlayerColor playerColor){
        currentPlayerColor = playerColor;
    }

    public static boolean isPlayerTurn(PlayerColor playerColor){
        return currentPlayerColor == playerColor;
    }

    public void newTurn(PlayerColor playerColor){
        //model crea un turno
        //manda alla view dello stesso colore le azioni possibili
    }

    public void firstTurn(PlayerColor playerColor) {
        //nuovo message primo turno a tutti
        //non posso sparare
        //pesco e basta
    }

    public void frenzyTurn(PlayerColor playerColor){
        //turno frenesia, nuovo message
    }

}