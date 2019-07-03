package model.exchanges.events;

import view.View;

/**
 * Event sent by the Weapon View and received from the Weapon Controller to choose which fire mode to
 * select for the current weapon
 * @author Michele Leva, Stefano Martino, Marco Maria Ferrara
 */
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
