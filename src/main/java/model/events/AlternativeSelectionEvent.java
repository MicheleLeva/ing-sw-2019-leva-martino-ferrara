package model.events;

import view.PlayerView;

public class AlternativeSelectionEvent extends Event {
    private final int index;
    public AlternativeSelectionEvent(PlayerView view, int index) {
        super(view);
        this.index = index;
    }

    public int getIndex(){
        return this.index;
    }

    @Override
    public String toString() {
        return ("AlternativeSelectionEvent," + getIndex());
    }
}
