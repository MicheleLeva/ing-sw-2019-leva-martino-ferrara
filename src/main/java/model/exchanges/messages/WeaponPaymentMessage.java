package model.exchanges.messages;

/**
 * Message sent by the Weapon Notifier and received from the Weapon View to let the player choose the powerUps
 * to pay the fire mode of the current weapon with
 */
public class WeaponPaymentMessage extends PlayerMessage {
    private final int size;

    public WeaponPaymentMessage(String message, int size) {
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
