package model.exchanges.events;

import view.View;

public class DiscardPowerUpEvent extends Event {
    private final int input;
    public DiscardPowerUpEvent(View view , int input){
        super(view);
        this.input = input;
    }
    public int getInput(){
        return input;
    }

    @Override
    public String toString() {
        return "DiscardPowerUpEvent," + getInput();
    }
}
