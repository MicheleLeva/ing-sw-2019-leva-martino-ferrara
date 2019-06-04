package model.exchanges.messages;

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
