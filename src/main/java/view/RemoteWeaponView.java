package view;

import model.exchanges.messages.PlayerMessage;
import model.exchanges.events.*;
import network.ClientConnection;
import utils.Observer;
import utils.observable.WeaponObservable;

import java.util.ArrayList;

/**
 * Auxiliary class of the RemoteView that forwards "Weapon" messages from the Model to the client
 * and events in the opposite direction
 */
public class RemoteWeaponView extends WeaponObservable implements Observer<PlayerMessage> {

    private ClientConnection clientConnection;
    private View view;

    public RemoteWeaponView(ClientConnection clientConnection, View view){
        this.clientConnection = clientConnection;
        this.view = view;
    }

    public void setClientConnection(ClientConnection clientConnection) {
        this.clientConnection = clientConnection;
    }

    /**
     * It simulates the View notify method.
     * Once received the event object form the RemoteView the method uses the correct update
     * in the Controller through Overloading.
     * @param inputs array of strings received from the RemoteView.
     */
    public void stringToMessage(String[] inputs){

        switch(inputs[0]) {
            case "WeaponSelectionEvent":
                WeaponSelectionEvent weaponSelectionEvent = new WeaponSelectionEvent(view, Integer.parseInt(inputs[1]));
                listeners.forEach(l -> l.update(weaponSelectionEvent));
                break;
            case "WeaponReloadEvent":
                WeaponReloadEvent weaponReloadEvent = new WeaponReloadEvent(view, Integer.parseInt(inputs[1]));
                listeners.forEach(l -> l.update(weaponReloadEvent));
                break;
            case "OptionalFireModesEvent":
                OptionalFireModesEvent optionalFireModesEvent = new OptionalFireModesEvent(view, Integer.parseInt(inputs[1]));
                listeners.forEach(l -> l.update(optionalFireModesEvent));
                break;
            case "TargetsSelectionEvent":
                ArrayList<Integer> selectedTargets = new ArrayList<>();
                for (int i = 1; i<= inputs.length-1; i++){
                    selectedTargets.add(Integer.parseInt(inputs[i]));
                }
                TargetsSelectionEvent targetsSelectionEvent = new TargetsSelectionEvent(view, selectedTargets);
                listeners.forEach(l -> l.update(targetsSelectionEvent));
                break;
            case "ChooseWeaponSquareEvent":
                ChooseWeaponSquareEvent chooseWeaponSquareEvent = new ChooseWeaponSquareEvent(view, Integer.parseInt(inputs[1]));
                listeners.forEach(l -> l.update(chooseWeaponSquareEvent));
                break;
            case "ChoosePickUpWeaponEvent":
                ChoosePickUpWeaponEvent choosePickUpWeaponEvent = new ChoosePickUpWeaponEvent(view, Integer.parseInt(inputs[1]));
                listeners.forEach(l -> l.update(choosePickUpWeaponEvent));
                break;
            case "WeaponPaymentEvent":
                ArrayList<Integer> selectedWeapons = new ArrayList<>();
                for (int i = 1; i<= inputs.length-1; i++){
                    selectedWeapons.add(Integer.parseInt(inputs[i]));
                }
                WeaponPaymentEvent weaponPaymentEvent = new WeaponPaymentEvent(view, selectedWeapons);
                listeners.forEach(l -> l.update(weaponPaymentEvent));
                break;
            case "ReloadPaymentEvent":
                ArrayList<Integer> reloadChoices = new ArrayList<>();
                for (int i = 1; i<= inputs.length -1; i++){
                    reloadChoices.add(Integer.parseInt(inputs[i]));
                }
                ReloadPaymentEvent reloadPaymentEvent = new ReloadPaymentEvent(view, reloadChoices);
                listeners.forEach(l -> l.update(reloadPaymentEvent));
                break;
            case "PickUpPaymentEvent":
                ArrayList<Integer> pickupChoices = new ArrayList<>();
                for (int i = 1; i<= inputs.length -1; i++){
                    pickupChoices.add(Integer.parseInt(inputs[i]));
                }
                PickUpPaymentEvent pickUpPaymentEvent = new PickUpPaymentEvent(view, pickupChoices);
                listeners.forEach(l -> l.update(pickUpPaymentEvent));
                break;
            case "WeaponSwapEvent":
                WeaponSwapEvent weaponSwapEvent = new WeaponSwapEvent(view, Integer.parseInt(inputs[1]));
                listeners.forEach(l -> l.update(weaponSwapEvent));
                break;


        }
    }

    /**
     * Received a PlayerMessage object from the Model it serializes it adding its class tag
     * then sends the string through the socket.
     * @param message to be sent.
     */
    @Override
    public void update(PlayerMessage message) {
        clientConnection.asyncSend("WEAPON," + message.toString());
    }
}
