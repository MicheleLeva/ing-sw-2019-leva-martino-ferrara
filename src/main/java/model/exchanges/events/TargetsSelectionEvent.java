package model.exchanges.events;

import view.View;

import java.util.ArrayList;

public class TargetsSelectionEvent extends Event {
    private ArrayList<Integer> selectedTargets;

    public TargetsSelectionEvent(View view, ArrayList<Integer> selectedTargets) {
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
