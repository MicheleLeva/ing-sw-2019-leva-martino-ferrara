package model.turn;

import model.Model;

public class CurrentTurn {
    private final Model model;

    public CurrentTurn(Model model){
        this.model = model;
    }

    public synchronized Turn getTurn(){
        int currentTurnNumber = model.getTurnManager().getCurrentTurnNumber();
        return new StandardTurn(model);
    }
}
