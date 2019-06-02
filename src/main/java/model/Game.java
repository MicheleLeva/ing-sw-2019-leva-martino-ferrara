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
    }

    public void run(){
        while(!model.getTurnManager().isGameOver()){
            currentTurn = new Turn(model,model.getTurnManager().isFrenzy());
            currentTurn.notifyTurn();
            currentTurn.startTurn();
            //endturn?
        }
        //manda punteggi
        //clear game dal server
    }



}
