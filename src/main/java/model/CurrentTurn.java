package model;

public class CurrentTurn {
    private final Model model;

    public CurrentTurn(Model model){
        this.model = model;
    }

    public synchronized Turn getTurn(){
        int currentTurnNumber = model.getTurnManager().getCurrentTurnNumber();

        if (currentTurnNumber == 1){
            return new FirstTurn(model);
        }

        if (model.getGameBoard().getKillShotTrack().isFrenzy()){
            return new FrenzyTurn(model);
        }

        else
            return new StandardTurn(model);
    }
}
