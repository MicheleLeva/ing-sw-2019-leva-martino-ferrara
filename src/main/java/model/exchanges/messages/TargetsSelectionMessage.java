package model.exchanges.messages;

/**
 * Message sent by the Weapon Notifier and received from the Weapon View to let the player choose which of the available
 * targets he wants to hit
 * @author Michele Leva, Stefano Martino, Marco Maria Ferrara
 */
public class TargetsSelectionMessage extends PlayerMessage{

    private final int targetsNumber;

    public TargetsSelectionMessage(String message, int targetsNumber) {
        super(message);
        this.targetsNumber = targetsNumber;
    }

    public int getTargetsNumber(){
        return this.targetsNumber;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "," + getMessage() + "," +getTargetsNumber();
    }
}
