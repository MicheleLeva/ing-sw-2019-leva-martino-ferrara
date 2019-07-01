package model.exchanges.messages;

/**
 * Message sent by the Weapon Notifier and received from the Weapon View to let the player choose which one of the
 * available squares he wants to use for the current weapon's requirements
 */
public class ChooseWeaponSquareMessage extends PlayerMessage {
    public ChooseWeaponSquareMessage(String message) {
        super(message);
    }
}
