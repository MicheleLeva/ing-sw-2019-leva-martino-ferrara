package model.events;

public class BaseLockRifleTargetsMessage extends PlayerMessage {

    private final int targetsNumber;

    public BaseLockRifleTargetsMessage(String message, int targetsumber) {
        super(message);
        this.targetsNumber = targetsumber;
    }

    public int getTargetsNumber() {
        return targetsNumber;
    }
}
