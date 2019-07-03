package model.exchanges.events;

import view.View;

/**
 * Event sent by the PowerUp View and received from the PowerUp Controller to choose whether to use
 * the Targeting scope or not
 * @author Michele Leva, Stefano Martino, Marco Maria Ferrara
 */
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
