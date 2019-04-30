package model;

import model.player_package.Player;
import model.player_package.PlayerColor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Game {

    private final int gameID;

    private Model model = null;

    private Turn currentTurn;

    public Game(int gameID, Model model){
        this.gameID = gameID;
        init(model);
    }

    private void init(Model model){
        //Inizializzazione passando solo il model
        this.model = model;

        startGame();
    }

    private void startGame(){
        while(!model.getTurnManager().isGameOver()){
            currentTurn = model.getCurrentTurn().getTurn();
            currentTurn.notifyTurn();
            currentTurn.startTurn();
        }

        //Calcolo punteggi
        model.endGame();
    }



}
