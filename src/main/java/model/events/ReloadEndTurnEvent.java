package model.events;

import view.PlayerView;

public class ReloadEndTurnEvent extends Event {
    private final char input;
    public ReloadEndTurnEvent(PlayerView view , char input){
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
