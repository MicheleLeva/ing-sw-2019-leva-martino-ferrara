package model.exchanges.events;

import view.View;

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
