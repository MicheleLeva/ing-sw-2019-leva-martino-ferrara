package model.exchanges.events;

import view.View;

public class ChoosePickUpWeaponEvent extends Event {
    private final int input;
    public ChoosePickUpWeaponEvent(View view,int input) {
        super(view);
        this.input = input;
    }
    public int getInput(){
        return input;
    }
    @Override
    public String toString() {
        return "ChoosePickUpWeaponEvent," + getInput();
    }
}
