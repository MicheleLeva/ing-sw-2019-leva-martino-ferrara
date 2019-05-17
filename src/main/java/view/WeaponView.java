package view;

import model.events.*;
import model.player_package.PlayerColor;
import utils.observable.WeaponObservable;
import utils.update.WeaponUpdate;

import java.util.ArrayList;

public class WeaponView extends WeaponObservable implements WeaponUpdate {
    private final PlayerColor playerColor;
    private final PlayerView view;

    public WeaponView(PlayerColor playerColor , PlayerView view){
        this.playerColor = playerColor;
        this.view = view;
    }

    public PlayerColor getPlayerColor(){
        return playerColor;
    }

    public PlayerView getView(){
        return view;
    }

    @Override
    public void update(ShowWeaponCardsMessage message){
        view.printMessage(message.getMessage());
        listeners.forEach(l -> l.update(new WeaponSelectionEvent(view , view.inputInt())));

    }

    public void update(TargetsSelectionMessage message){
        ArrayList<Integer> selectedTargets = new ArrayList<>();
        int counter = message.getTargetsNumber();
        int selection;
        String temp = message.getMessage();
        if(temp.equals("Choose a target: \n")){
            view.printMessage("No available targets for this fire mode \n");
            selectedTargets.add(-1);
            listeners.forEach(l -> l.update(new TargetsSelectionEvent(view,selectedTargets)));}

        else{
            view.printMessage(message.getMessage());
            while(counter>0){
                selection = view.inputInt();
                selectedTargets.add(selection);
                counter--;
            }
            listeners.forEach(l -> l.update(new TargetsSelectionEvent(view,selectedTargets)));
        }
    }

    @Override
    public void update(AskReloadMessage askReloadMessage){
        view.printMessage(askReloadMessage.getMessage());
        listeners.forEach(l -> l.update(new ReloadEndTurnEvent(view , view.inputChar())));
    }

    @Override
    public void update(WeaponReloadMessage weaponReloadMessage){
        view.printMessage(weaponReloadMessage.getMessage());
        listeners.forEach((l -> l.update(new WeaponReloadEvent(view , view.inputInt()))));
    }

    public void update(AskFireModesMessage message){
        listeners.forEach(l -> l.update(new OptionalFireModesEvent(view,view.inputInt())));
    }

    public void update(ChooseWeaponSquareMessage message){
        view.printMessage(message.getMessage());
        listeners.forEach(l -> l.update(new ChooseWeaponSquareEvent(view , view.inputInt())));
    }
}
