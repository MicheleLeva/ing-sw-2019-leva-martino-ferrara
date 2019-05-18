package view;

import model.events.*;
import model.player_package.PlayerColor;
import utils.Observable;
import utils.observable.WeaponObservable;
import utils.update.WeaponUpdate;

import java.util.ArrayList;

public class WeaponView extends Observable<Event> implements WeaponUpdate {
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

        Event event = new WeaponSelectionEvent(view, view.inputInt());
        notify(event);
        //listeners.forEach(l -> l.update(new WeaponSelectionEvent(view , view.inputInt())));
    }

    public void update(TargetsSelectionMessage message){
        ArrayList<Integer> selectedTargets = new ArrayList<>();
        int counter = message.getTargetsNumber();
        int selection;
        String temp = message.getMessage();
        if(temp.equals("Choose a target: \n")){
            view.printMessage("No available targets for this fire mode \n");
            selectedTargets.add(-1);

            Event event = new TargetsSelectionEvent(view,selectedTargets);
            notify(event);
            //listeners.forEach(l -> l.update(new TargetsSelectionEvent(view,selectedTargets)));
            }

        else{
            view.printMessage(message.getMessage());
            while(counter>0){
                selection = view.inputInt();
                selectedTargets.add(selection);
                counter--;
            }

            Event event = new TargetsSelectionEvent(view,selectedTargets);
            notify(event);
            //listeners.forEach(l -> l.update(new TargetsSelectionEvent(view,selectedTargets)));
        }
    }

    @Override
    public void update(AskReloadMessage askReloadMessage){
        view.printMessage(askReloadMessage.getMessage());

        Event event = new ReloadEndTurnEvent(view , view.inputChar());
        notify(event);
        //listeners.forEach(l -> l.update(new ReloadEndTurnEvent(view , view.inputChar())));
    }

    @Override
    public void update(WeaponReloadMessage weaponReloadMessage){
        view.printMessage(weaponReloadMessage.getMessage());

        Event event = new WeaponReloadEvent(view , view.inputInt());
        notify(event);
        //listeners.forEach((l -> l.update(new WeaponReloadEvent(view , view.inputInt()))));
    }

    public void update(AskFireModesMessage message){
        view.printMessage(message.getMessage());

        Event event = new OptionalFireModesEvent(view,view.inputInt());
        notify(event);
        //listeners.forEach(l -> l.update(new OptionalFireModesEvent(view,view.inputInt())));
    }

    public void update(ChooseWeaponSquareMessage message){
        view.printMessage(message.getMessage());

        Event event = new ChooseWeaponSquareEvent(view , view.inputInt());
        notify(event);
        //listeners.forEach(l -> l.update(new ChooseWeaponSquareEvent(view , view.inputInt())));
    }
}
