package model.exchanges.events;

import view.View;

public class TargetingScopeEvent extends Event {
    private char choice;
    public TargetingScopeEvent(View view,char choice) {
        super(view);
        this.choice = choice;
    }

    public char getChoice(){
        return this.choice;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "," + getChoice();
    }
}
