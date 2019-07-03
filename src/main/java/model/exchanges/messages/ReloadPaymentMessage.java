package model.exchanges.messages;

/**
 * Message sent by the Weapon Notifier and received from the Weapon View to let the player choose which powerUps
 * he wants to pay the selected unloaded weapon with
 * @author Michele Leva, Stefano Martino, Marco Maria Ferrara
 */
public class ReloadPaymentMessage extends PlayerMessage {
    private final int size;

    public ReloadPaymentMessage(String message, int size) {
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
