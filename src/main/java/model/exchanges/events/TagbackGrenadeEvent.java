package model.exchanges.events;

import view.View;

public class TagbackGrenadeEvent extends Event {

    private char input;
    public TagbackGrenadeEvent(View view, char input) {
        super(view);
        this.input = input;
    }

    public char getInput(){
        return this.input;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "," + getInput();
    }
}
