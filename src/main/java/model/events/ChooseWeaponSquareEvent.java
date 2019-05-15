package model.events;

import view.PlayerView;

public class ChooseWeaponSquareEvent extends Event {
    private final int input;
    public ChooseWeaponSquareEvent(PlayerView view,int input) {
        super(view);
        this.input = input;
    }

    public int getInput(){
        return input;
    }
}
