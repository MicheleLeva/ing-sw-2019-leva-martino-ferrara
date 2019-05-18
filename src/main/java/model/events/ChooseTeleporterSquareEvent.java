package model.events;

import view.PlayerView;

public class ChooseTeleporterSquareEvent extends Event {
    private final int input;
    public ChooseTeleporterSquareEvent(PlayerView view , int input){
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
