package model.exchanges.events;

import view.View;

/**
 * Event sent by the PowerUp View and received from the PowerUp Controller to choose the target
 * to use the Newton PowerUp on
 */
public class ChooseNewtonOpponentEvent extends Event {
    private final int input;
    public ChooseNewtonOpponentEvent(View view , int input){
        super(view);
        this.input = input;
    }

    public int getInput() {
        return input;
    }

    @Override
    public String toString() {
        return "ChooseNewtonOpponentEvent," + getInput();
    }
}
