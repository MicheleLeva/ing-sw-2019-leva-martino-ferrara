package model.events;

import view.PlayerView;

public class ChooseNewtonSquareEvent extends Event {
    private final int input;
    public ChooseNewtonSquareEvent(PlayerView view , int input){
        super(view);
        this.input = input;
    }

    public int getInput(){
        return input;
    }

    @Override
    public String toString() {
        return "ChooseNewtonSquareEvent," + getInput();
    }
}
