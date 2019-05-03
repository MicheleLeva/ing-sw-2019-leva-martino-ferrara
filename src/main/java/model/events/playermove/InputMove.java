package model.events.playermove;

import view.View;

public class InputMove extends PlayerMove {
    private final char input;

    public InputMove(View view , char input){
        super(view);
        this.input = input;
    }

    public char getInput(){
        return input;
    }
}
