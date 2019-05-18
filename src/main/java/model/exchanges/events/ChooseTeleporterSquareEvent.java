package model.exchanges.events;

import view.View;

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
