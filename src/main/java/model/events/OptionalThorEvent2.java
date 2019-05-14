package model.events;

import view.PlayerView;

public class OptionalThorEvent2 extends Event{
    private final int input;
    public OptionalThorEvent2(PlayerView view, int input) {
        super(view);
        this.input = input;
    }

    public int getInput(){
        return this.input;
    }

}
