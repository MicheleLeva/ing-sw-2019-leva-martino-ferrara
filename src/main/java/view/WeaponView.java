package view;

import model.exchanges.events.*;
import model.exchanges.events.Event;
import model.exchanges.messages.*;
import model.player.PlayerColor;
import utils.Observable;
import utils.update.WeaponUpdate;

import java.util.ArrayList;


/**
 * View which manages the Weapon Exchanges
 */
public class WeaponView extends Observable<Event> implements WeaponUpdate {
    private final PlayerColor playerColor;
    private final View view;

    public WeaponView(PlayerColor playerColor , View view){
        this.playerColor = playerColor;
        this.view = view;
    }

    public PlayerColor getPlayerColor(){
        return playerColor;
    }

    public View getView(){
        return view;
    }

    @Override
    public void update(ShowWeaponCardsMessage message){
        view.printMessage(message.getMessage());

        Event event = new WeaponSelectionEvent(view, view.inputInt());
        notify(event);
    }

    @Override
    public void update(TargetsSelectionMessage message){
        ArrayList<Integer> selectedTargets = new ArrayList<>();
        int counter = message.getTargetsNumber();
        int selection = 1;
            System.out.println("targetsselection message  in weaponview");
            view.printMessage(message.getMessage());
            while(counter>0 && selection !=0){
                selection = view.inputInt();
                selectedTargets.add(selection);
                counter--;
            }
            if(selectedTargets.contains(0))
                selectedTargets.remove(Integer.valueOf(0));
            Event event = new TargetsSelectionEvent(view,selectedTargets);
            notify(event);

    }

    @Override
    public void update(AskReloadMessage askReloadMessage){
        view.printMessage(askReloadMessage.getMessage());

        Event event = new ReloadEndTurnEvent(view , view.inputChar());
        notify(event);
    }

    @Override
    public void update(WeaponReloadMessage weaponReloadMessage){
        view.printMessage(weaponReloadMessage.getMessage());

        Event event = new WeaponReloadEvent(view , view.inputInt());
        notify(event);
    }

    @Override
    public void update(AskFireModesMessage message){
        view.printMessage(message.getMessage());
        Event event = new OptionalFireModesEvent(view,view.inputInt());
        notify(event);
    }
    @Override
    public void update(ChooseWeaponSquareMessage message){
        view.printMessage(message.getMessage());
        Event event = new ChooseWeaponSquareEvent(view , view.inputInt());
        notify(event);
    }
    @Override
    public void update(WeaponPaymentMessage message) {
        System.out.println("weaponpaymentmessage in weaponview");
        ArrayList<Integer> choices = new ArrayList<>();
        int counter = message.getSize();
        int selection = 1;
        view.printMessage(message.getMessage());
        while (counter>0 && selection !=0){
            selection = view.inputInt();
            choices.add(selection);
            counter--;
        }
        if(choices.contains(0))
            choices.remove(Integer.valueOf(0));
        Event event = new WeaponPaymentEvent(view, choices);
        notify(event);
    }
    @Override
    public void update(ReloadPaymentMessage message){
        ArrayList<Integer> choices = new ArrayList<>();
        int counter = message.getSize();
        int selection = 1;
        view.printMessage(message.getMessage());
        while (counter>0 && selection !=0){
            selection = view.inputInt();
            choices.add(selection);
            counter--;
        }
        if(choices.contains(0))
            choices.remove(Integer.valueOf(0));
        Event event = new ReloadPaymentEvent(view, choices);
        notify(event);
    }
    @Override
    public void update(PickUpPaymentMessage message){
        ArrayList<Integer> choices = new ArrayList<>();
        int counter = message.getSize();
        int selection = 1;
        view.printMessage(message.getMessage());
        while (counter>0 && selection !=0){
            selection = view.inputInt();

            choices.add(selection);
            counter--;
        }
        if (choices.contains(0)){
            choices.remove(Integer.valueOf(0));
        }
        Event event = new PickUpPaymentEvent(view, choices);
        notify(event);
    }
    @Override
    public void update(ShowPickUpWeaponsMessage message){
        view.printMessage(message.getMessage());

        Event event = new ChoosePickUpWeaponEvent(view,view.inputInt());
        notify(event);
    }

    @Override
    public void update(WeaponSwapMessage message){
        view.printMessage(message.getMessage());
        int input = view.inputInt();
        Event event = new WeaponSwapEvent(view,input);
        notify(event);
    }
}
