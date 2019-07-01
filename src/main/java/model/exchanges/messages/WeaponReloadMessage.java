package model.exchanges.messages;

/**
 * Message sent by the Weapon Notifier and received from the Weapon View to let the player choose the powerUps
 * to use to pay the reload cost of the selected unloaded weapon
 */
public class WeaponReloadMessage extends PlayerMessage {
    public WeaponReloadMessage(String message){
        super(message);
    }
}
