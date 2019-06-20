package model.exchanges.events;

import view.View;

public class VoteMapEvent extends Event {
    private int input;

    public VoteMapEvent(View view, int input){
        super(view);
        this.input = input;
    }

    public int getInput(){
        return input;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "," + getInput();
    }
}
