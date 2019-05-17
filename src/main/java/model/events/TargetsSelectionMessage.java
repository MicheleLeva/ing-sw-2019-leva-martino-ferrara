package model.events;

public class TargetsSelectionMessage extends PlayerMessage{

    private final int targetsNumber;

    public TargetsSelectionMessage(String message, int targetsNumber) {
        super(message);
        this.targetsNumber = targetsNumber;
    }

    public int getTargetsNumber(){
        return this.targetsNumber;
    }
}
