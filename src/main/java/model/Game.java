package model;

import model.turn.Turn;

public class Game implements Runnable{

    private final int gameID;

    private final Model model;

    private Turn currentTurn;

    public int getGameID() {
        return gameID;
    }

    public Game(int gameID, Model model){
        this.gameID = gameID;
        this.model = model;
        run();
        //diventa una run per farlo diventare thread
    }

    public void run(){
        while(!model.getTurnManager().isGameOver()){
            currentTurn = model.getCurrentTurn().getTurn();
            currentTurn.notifyTurn();
            currentTurn.startTurn();
            //endturn?
        }
        //manda punteggi
        //clear game dal server
    }



}
