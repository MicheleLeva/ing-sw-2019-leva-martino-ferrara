package model.events;

public class OptionalLockRifleTargetsMessage1 extends PlayerMessage{

    private final int targetsNumber;

    public OptionalLockRifleTargetsMessage1(String message, int targetsNumber) {
        super(message);
        this.targetsNumber = targetsNumber;
    }

    public int getTargetsNumber(){
        return this.targetsNumber;
    }
}
