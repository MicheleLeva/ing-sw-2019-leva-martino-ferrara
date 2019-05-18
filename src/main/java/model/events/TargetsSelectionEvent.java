package model.events;

import view.PlayerView;

import java.util.ArrayList;

public class TargetsSelectionEvent extends Event {
    private ArrayList<Integer> selectedTargets;

    public TargetsSelectionEvent(PlayerView view, ArrayList<Integer> selectedTargets) {
        super(view);
        this.selectedTargets = selectedTargets;
    }


    public ArrayList<Integer> getSelectedTargets() {
        return selectedTargets;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "," +  arrayToString(getSelectedTargets());
    }


}
