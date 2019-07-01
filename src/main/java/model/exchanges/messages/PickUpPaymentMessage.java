package model.exchanges.messages;

/**
 * Message sent by the PowerUp Notifier and received from the PowerUp View to let the player choose which of his
 * powerUps to use to pay the picked-up weapon wth
 */
public class PickUpPaymentMessage extends PlayerMessage {
    private final int size;

    public PickUpPaymentMessage(String message, int size) {
        super(message);
        this.size = size;
    }

    public int getSize(){
        return this.size;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "," + getMessage() + "," + getSize();
    }
}
