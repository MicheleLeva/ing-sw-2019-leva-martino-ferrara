package model.exchanges.events;

import view.View;

/**
 * Event sent by the PowerUp View and received from the PowerUp Controller to choose the square to
 * move the chosen Newton target to
 * @author Michele Leva, Stefano Martino, Marco Maria Ferrara
 */
public class ChooseNewtonSquareEvent extends Event {
    private final int input;
    public ChooseNewtonSquareEvent(View view , int input){
        super(view);
        this.input = input;
    }

    public int getInput(){
        return input;
    }

    @Override
    public String toString() {
        return "ChooseNewtonSquareEvent," + getInput();
    }
}
