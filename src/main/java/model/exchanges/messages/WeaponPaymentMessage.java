package model.exchanges.messages;

public class WeaponPaymentMessage extends PlayerMessage {
    private final int size;

    public WeaponPaymentMessage(String message, int size) {
        super(message);
        this.size = size;
    }

    public int getSize(){
        return this.size;
    }
}
