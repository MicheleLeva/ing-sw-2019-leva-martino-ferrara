package model.exchanges.events;

import view.View;

/**
 * Event sent by the PowerUp View and received from the PowerUp Controller to choose which powerUp
 * in the player's resources to use
 * @author Michele Leva, Stefano Martino, Marco Maria Ferrara
 */
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
