package model.events;

import view.PlayerView;

public class ChoosePowerUpEvent extends Event {
    private final int input;
    public ChoosePowerUpEvent(PlayerView view , int input){
        super(view);
        this.input = input;
    }
    public int getInput(){
        return input;
    }
}
