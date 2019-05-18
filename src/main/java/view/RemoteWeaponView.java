package view;

import model.events.PlayerMessage;
import model.events.*;
import network.ClientConnection;
import utils.Observer;
import utils.observable.WeaponObservable;

import java.util.ArrayList;

public class RemoteWeaponView extends WeaponObservable implements Observer<PlayerMessage> {

    private ClientConnection clientConnection;
    private PlayerView view;

    public RemoteWeaponView(ClientConnection clientConnection, PlayerView view){
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
        }
    }

    @Override
    public void update(PlayerMessage message) {
        clientConnection.asyncSend("WEAPON," + message.toString());
    }
}
