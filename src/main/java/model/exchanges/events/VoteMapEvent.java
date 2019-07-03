package model.exchanges.events;

import view.View;

/**
 * Event sent by the Action View and received from the Action Controller to choose which map to vote for
 * @author Michele Leva, Stefano Martino, Marco Maria Ferrara
 */
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
