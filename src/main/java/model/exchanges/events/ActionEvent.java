package model.exchanges.events;

import view.View;

/**
 * Event sent by the Action View and received from the Action Controller to choose the next action
 */
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
