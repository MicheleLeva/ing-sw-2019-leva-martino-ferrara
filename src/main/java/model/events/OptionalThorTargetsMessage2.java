package model.events;

public class OptionalThorTargetsMessage2 extends PlayerMessage {
    private final int targetsNumber;

    public OptionalThorTargetsMessage2(String message, int targetsNumber) {
        super(message);
        this.targetsNumber = targetsNumber;
    }

    public int getTargetsNumber(){
        return this.targetsNumber;
    }
}

