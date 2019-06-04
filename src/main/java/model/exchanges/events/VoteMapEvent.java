package model.exchanges.events;

import view.View;

public class VoteMapEvent extends Event {
    private char input;

    public VoteMapEvent(View view, char input){
        super(view);
        this.input = input;
    }

    public char getInput(){
        return input;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "," + getInput();
    }
}
