package view;

import model.exchanges.messages.PlayerMessage;
import model.exchanges.events.*;
import network.ClientConnection;
import utils.Observer;
import utils.observable.WeaponObservable;

import java.util.ArrayList;

public class RemoteWeaponView extends WeaponObservable implements Observer<PlayerMessage> {

    private ClientConnection clientConnection;
    private View view;

    public RemoteWeaponView(ClientConnection clientConnection, View view){
        this.clientConnection = clientConnection;
        this.view = view;
    }

    public void stringToMessage(String[] inputs){

        switch(inputs[0]) {
            case "WeaponSelectionEvent":
                WeaponSelectionEvent weaponSelectionEvent = new WeaponSelectionEvent(view, Integer.parseInt(inputs[1]));
                listeners.forEach(l -> l.update(weaponSelectionEvent));
                break;
            case "ReloadEndTurnEvent":
                ReloadEndTurnEvent reloadEndTurnEvent = new ReloadEndTurnEvent(view, inputs[1].charAt(0));
                listeners.forEach(l -> l.update(reloadEndTurnEvent));
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
                for (int i = 1; i<= inputs.length; i++){
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
                for (int i = 1; i<= inputs.length; i++){
                    selectedWeapons.add(Integer.parseInt(inputs[i]));
                }
                WeaponPaymentEvent weaponPaymentEvent = new WeaponPaymentEvent(view, selectedWeapons);
                listeners.forEach(l -> l.update(weaponPaymentEvent));
                break;
            case "PickUpPaymentEvent":
                ArrayList<Integer> selectedChoices = new ArrayList<>();
                System.out.println("siamo nella remoteview1");
                for (int i = 1; i<= inputs.length; i++){
                    System.out.println("siamo nella remoteview2");
                    selectedChoices.add(Integer.parseInt(inputs[i]));
                }
                System.out.println("siamo nella remoteview3");
                PickUpPaymentEvent pickUpPaymentEvent = new PickUpPaymentEvent(view, selectedChoices);
                listeners.forEach(l -> l.update(pickUpPaymentEvent));
        }
    }

    @Override
    public void update(PlayerMessage message) {
        clientConnection.asyncSend("WEAPON," + message.toString());
    }
}
