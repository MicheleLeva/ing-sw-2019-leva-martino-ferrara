package model.events;

import view.PlayerView;

import java.util.ArrayList;

public class OptionalThorTargetsEvent2 extends Event {
    private ArrayList<Integer> selectedTargets;

    public OptionalThorTargetsEvent2(PlayerView view, ArrayList<Integer> selectedTargets) {
        super(view);
        this.selectedTargets = selectedTargets;
    }


    public ArrayList<Integer> getSelectedTargets() {
        return selectedTargets;
    }
}
