package model.exchanges.events;

import view.View;

public class OptionalFireModesEvent extends Event {
    private final int input;

    public OptionalFireModesEvent(View view, int input) {
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
