package model.exchanges.events;

import view.View;

/**
 * Event sent by the PowerUp View and received from the PowerUp Controller to choose which one of the
 * TagBack Grenades in the damaged player's hand to use
 * @author Michele Leva, Stefano Martino, Marco Maria Ferrara
 */
public class TagbackGrenadeEvent extends Event {

    private int input;
    public TagbackGrenadeEvent(View view, int input) {
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
