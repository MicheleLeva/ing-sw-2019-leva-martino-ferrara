package model.events;

import view.PlayerView;

public class OptionalFireModesEvent extends Event {
    private final int input;

    public OptionalFireModesEvent(PlayerView view, int input) {
        super(view);
        this.input = input;
    }

    public int getInput(){
        return this.input;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "," + getInput();
    }
}
