package model.exchanges.events;

import view.View;

/**
 * Event sent by the PowerUp View and received from the PowerUp Controller to choose which target to
 * use the TargetingScope on
 */
public class TargetingScopeSelectionEvent extends Event{

    private final int input;

    public TargetingScopeSelectionEvent(View view,int input) {
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
