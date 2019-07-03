package model.exchanges.events;

import view.View;

/**
 * Event sent by the PowerUp View and received from the PowerUp Controller to choose the square to move
 * the current player to using the teleporter
 * @author Michele Leva, Stefano Martino, Marco Maria Ferrara
 */
public class ChooseTeleporterSquareEvent extends Event {
    private final int input;
    public ChooseTeleporterSquareEvent(View view , int input){
        super(view);
        this.input = input;
    }

    public int getInput() {
        return input;
    }

    @Override
    public String toString() {
        return "ChooseTeleporterSquareEvent," + getInput();
    }
}
