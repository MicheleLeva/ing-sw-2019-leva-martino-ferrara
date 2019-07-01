package model.exchanges.events;

import view.View;

/**
 * Event sent by the PowerUp View and received from the PowerUp Controller to choose which color of cube
 * to use to pay the Targeting Scope PowerUp
 */
public class ScopePaymentEvent extends Event {
    private char choice;
    public ScopePaymentEvent(View view,char choice) {
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
