package model.exchanges.events;

import view.View;

public class ChoosePowerUpEvent extends Event {
    private final int input;
    public ChoosePowerUpEvent(View view , int input){
        super(view);
        this.input = input;
    }
    public int getInput(){
        return input;
    }

    @Override
    public String toString() {
        return "ChoosePowerUpEvent," + getInput();
    }
}
