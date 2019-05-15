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

    public void update(AskAlternativeMessage message){
        view.printMessage(message.getMessage());
        listeners.forEach(l -> l.update(new AlternativeSelectionEvent(view,view.inputInt())));
    }

    public void update(BaseLockRifleTargetsMessage message){
        ArrayList<Integer> selectedTargets = new ArrayList<>();
        int counter = message.getTargetsNumber();
        int selection;
        while(counter>0){
            selection = view.inputInt();
            selectedTargets.add(selection);
            counter--;
        }
        listeners.forEach(l -> l.update(new BaseLockRifleTargetsEvent(view,selectedTargets)));

    }

    public void update(OptionalLockRifleMessage1 message){
        view.printMessage(message.getMessage());
        listeners.forEach(l -> l.update(new OptionalLockRifleEvent1(view,view.inputInt())));
    }

    public void update(OptionalLockRifleTargetsMessage1 message){
        ArrayList<Integer> selectedTargets = new ArrayList<>();
        int counter = message.getTargetsNumber();
        int selection;
        while(counter>0){
            selection = view.inputInt();
            selectedTargets.add(selection);
            counter--;
        }
        listeners.forEach(l -> l.update(new OptionalLockRifleTargetsEvent1(view,selectedTargets)));
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

    public void update(AlternativeHellionTargetsMessage message){
        ArrayList<Integer> selectedTargets = new ArrayList<>();
        int counter = message.getTargetsNumber();
        int selection;
        while(counter>0){
            selection = view.inputInt();
            selectedTargets.add(selection);
            counter--;
        }
        listeners.forEach(l -> l.update(new AlternativeHellionTargetsEvent(view,selectedTargets)));

    }

    public void update(OptionalThorMessage2 message){
        view.printMessage(message.getMessage());
        listeners.forEach(l -> l.update(new OptionalLockRifleEvent1(view,view.inputInt())));
    }

    public void update (OptionalThorTargetsMessage2 message){
        ArrayList<Integer> selectedTargets = new ArrayList<>();
        int counter = message.getTargetsNumber();
        int selection;
        while(counter>0){
            selection = view.inputInt();
            selectedTargets.add(selection);
            counter--;
        }
        listeners.forEach(l -> l.update(new OptionalLockRifleTargetsEvent1(view,selectedTargets)));
    }

    public void update(AskFireModesMessage message){
        listeners.forEach(l -> l.update(new OptionalFireModesEvent(view,view.inputInt())));
    }

    public void update(ChooseWeaponSquareMessage message){
        view.printMessage(message.getMessage());
        listeners.forEach(l -> l.update(new ChooseWeaponSquareEvent(view , view.inputInt())));
    }
}
