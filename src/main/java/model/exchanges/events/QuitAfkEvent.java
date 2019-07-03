package model.exchanges.events;

import view.View;

/**
 * Event sent by the Action View and received from the Action Controller to choose which one of the AFK
 * players to wake up
 * @author Michele Leva, Stefano Martino, Marco Maria Ferrara
 */
public class QuitAfkEvent extends Event {
    private char input;

    public QuitAfkEvent(View view, char input){
        super(view);
        this.input = input;
    }

    private char getInput(){
        return input;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "," + getInput();
    }
}
