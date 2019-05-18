package model.exchanges.events;

import view.View;

public class ChooseNewtonSquareEvent extends Event {
    private final int input;
    public ChooseNewtonSquareEvent(View view , int input){
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
