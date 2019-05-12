package model.events;

import view.PlayerView;

import java.util.ArrayList;

public class BaseLockRifleTargetsEvent extends Event {

    private ArrayList<Integer> selectedTargets;

    public BaseLockRifleTargetsEvent(PlayerView view, ArrayList<Integer> selectedTargets) {
        super(view);
        this.selectedTargets = selectedTargets;
    }

    public ArrayList<Integer> getSelectedTargets(){
        return selectedTargets;
    }
}
