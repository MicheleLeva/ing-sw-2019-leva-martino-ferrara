package model.events;

import view.PlayerView;

public class ChooseNewtonOpponentEvent extends Event {
    private final int input;
    public ChooseNewtonOpponentEvent(PlayerView view , int input){
        super(view);
        this.input = input;
    }

    public int getInput() {
        return input;
    }
}
