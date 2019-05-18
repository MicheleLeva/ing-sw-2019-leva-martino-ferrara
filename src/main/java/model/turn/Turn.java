package model.turn;

import model.Model;

public abstract class Turn {
    private final Model model;

    public Turn(Model model){
        this.model = model;
    }

    public Model getModel(){
        return model;
    }

    public void notifyTurn(){
        String toPlayer = "";
        String toOthers = "";

        if(model.getTurnManager().getCurrentPlayer().isFirst()){
            toPlayer = toOthers = "Turn number " + model.getTurnManager().getCurrentTurnNumber() +"\n";
        }

        String currentPlayerName = model.getTurnManager().getCurrentPlayer().getPlayerName();
        toPlayer = toPlayer + "It's your turn.\n";
        toOthers = toOthers + "It's " +currentPlayerName +"'s turn";

        model.printMessage(model.getTurnManager().getCurrentPlayerColor() , toPlayer , toOthers);
    }

    public abstract void startTurn();
}
