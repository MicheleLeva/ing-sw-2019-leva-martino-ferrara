package model.exchanges.events;

import view.View;

public class QuitAfkEvent extends Event {
    private char input;

    public QuitAfkEvent(View view, char input){
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
