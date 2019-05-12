package model.events;

import view.PlayerView;

public class OptionalLockRifleEvent1 extends Event {
    private final int input;
    public OptionalLockRifleEvent1(PlayerView view, int input) {
        super(view);
        this.input = input;
    }

    public int getInput(){
        return this.input;
    }

}
