package model;


import model.player_package.PlayerColor;


public class TurnManager {

    private static PlayerColor currentPlayerColor;

    public static void changeCurrentPlayer(PlayerColor playerColor){
        currentPlayerColor = playerColor;
    }

    public static boolean isPlayerTurn(PlayerColor playerColor){
        return currentPlayerColor == playerColor;
    }
}
