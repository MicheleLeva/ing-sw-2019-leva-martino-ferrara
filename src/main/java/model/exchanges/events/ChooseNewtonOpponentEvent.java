package model.exchanges.events;

import view.View;

public class ChooseNewtonOpponentEvent extends Event {
    private final int input;
    public ChooseNewtonOpponentEvent(View view , int input){
        super(view);
        this.input = input;
    }

    public int getInput() {
        return input;
    }

    @Override
    public String toString() {
        return "ChooseNewtonOpponentEvent," + getInput();
    }
}
