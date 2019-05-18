package model.exchanges.events;

import view.View;

public class ActionEvent extends Event {
    private final char input;

    public ActionEvent(View view, char input){
        super(view);
        this.input = input;
    }

    public char getInput(){
        return input;
    }

    @Override
    public String toString() {
        return "ActionEvent," + getInput();
    }
}
