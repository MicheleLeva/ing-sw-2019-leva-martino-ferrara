package model;

import model.turn.Turn;

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
        //diventa una run per farlo diventare thread
    }

    //todo diventerà run: thread principale del gioco
    private void startGame(){
        while(!model.getTurnManager().isGameOver()){
            currentTurn = model.getCurrentTurn().getTurn();
            currentTurn.notifyTurn();
            currentTurn.startTurn();
        }
        //manda punteggi
        //clear game dal server
    }



}
