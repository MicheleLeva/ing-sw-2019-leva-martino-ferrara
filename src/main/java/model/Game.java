package model;

import java.util.HashMap;

public class Game {

    private final int gameID;

    private Model model = null;

    private Turn currentTurn;

    public Game(int gameID){
        this.gameID = gameID;
        init();
    }

    private void init(){
        //Inizializzazione, creazione model, passando lista giocatori.





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
