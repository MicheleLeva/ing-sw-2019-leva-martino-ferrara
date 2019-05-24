package model.exchanges.messages;

public class ReloadPaymentMessage extends PlayerMessage {
    private final int size;

    public ReloadPaymentMessage(String message, int size) {
        super(message);
        this.size = size;
    }

    public int getSize(){
        return this.size;
    }
}
