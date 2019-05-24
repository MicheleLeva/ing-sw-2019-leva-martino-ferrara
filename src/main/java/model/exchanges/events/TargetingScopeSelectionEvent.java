package model.exchanges.events;

import view.View;

public class TargetingScopeSelectionEvent extends Event{

    private final int input;

    public TargetingScopeSelectionEvent(View view,int input) {
        super(view);
        this.input = input;
    }

    public int getInput(){
        return this.input;
    }
}
