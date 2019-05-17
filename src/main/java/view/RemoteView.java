package view;

import model.events.*;
import model.player_package.PlayerColor;
import network.ClientConnection;
import utils.Observer;
import utils.observer.*;
import utils.update.*;

import java.util.ArrayList;
import java.util.List;

public class RemoteView extends PlayerView implements ActionUpdate, GameUpdate, PowerUpUpdate, WeaponUpdate, Observer<String> {

    public final List<ActionObserver> actionListeners = new ArrayList();

    public final List<WeaponObserver> weaponListeners = new ArrayList();

    public final List<PowerUpObserver> powerUpListeners = new ArrayList();

    public void register(PowerUpObserver observer){
        synchronized (powerUpListeners){
            powerUpListeners.add(observer);
        }
    }

    public void deregister(PowerUpObserver observer){
        synchronized (powerUpListeners){
            powerUpListeners.remove(observer);
        }
    }

    public void register(WeaponObserver observer){
        synchronized (weaponListeners){
            weaponListeners.add(observer);
        }
    }

    public void deregister(WeaponObserver observer){
        synchronized (weaponListeners){
            weaponListeners.remove(observer);
        }
    }

    public void register(ActionObserver observer) {
        synchronized (actionListeners) {
            actionListeners.add(observer);
        }
    }

    public void deregister(ActionObserver observer) {
        synchronized (actionListeners) {
            actionListeners.remove(observer);
        }
    }

    @Override
    public void update(String message) {
        //System.out.println("Received " + message);
        try{
            String[] inputs = message.split(",");
            //a seconda della stringa che arriva ricreo il messaggio
            //un case per ogni messaggio
            switch(inputs[0]){
                case "ActionEvent":
                    ActionEvent actionEvent = new ActionEvent(this, inputs[1].charAt(0));
                    actionListeners.forEach(l -> l.update(actionEvent));
                    break;
                case "ChoosePowerUpEvent":
                    ChoosePowerUpEvent choosePowerUpEvent = new ChoosePowerUpEvent(this, Integer.parseInt(inputs[1]));
                    powerUpListeners.forEach(l -> l.update(choosePowerUpEvent));
                    break;
                case "ChooseTeleporterSquareEvent":
                    ChooseTeleporterSquareEvent chooseTeleporterSquareEvent = new ChooseTeleporterSquareEvent(this, Integer.parseInt(inputs[1]));
                    powerUpListeners.forEach(l -> l.update(chooseTeleporterSquareEvent));
                    break;
                case "ChooseNewtonOpponentEvent":
                    ChooseNewtonOpponentEvent chooseNewtonOpponentEvent = new ChooseNewtonOpponentEvent(this, Integer.parseInt(inputs[1]));
                    powerUpListeners.forEach(l -> l.update(chooseNewtonOpponentEvent));
                    break;
                case "ChooseNewtonSquareEvent":
                    ChooseNewtonSquareEvent chooseNewtonSquareEvent = new ChooseNewtonSquareEvent(this, Integer.parseInt(inputs[1]));
                    powerUpListeners.forEach(l -> l.update(chooseNewtonSquareEvent));
                    break;
                case "WeaponSelectionEvent":
                    WeaponSelectionEvent weaponSelectionEvent = new WeaponSelectionEvent(this, Integer.parseInt(inputs[1]));
                    weaponListeners.forEach(l -> l.update(weaponSelectionEvent));
                    break;
                case "AlternativeSelectionEvent":
                    AlternativeSelectionEvent alternativeSelectionEvent = new AlternativeSelectionEvent(this, Integer.parseInt(inputs[1]));
                    weaponListeners.forEach(l -> l.update(alternativeSelectionEvent));
                    break;
                case "BaseLockRifleTargetsEvent":
                    ArrayList<Integer> baseTargets = new ArrayList<>();
                    for (int i = 1; i<= inputs.length; i++){
                        baseTargets.add(Integer.parseInt(inputs[i]));
                    }
                    BaseLockRifleTargetsEvent baseLockRifleTargetsEvent = new BaseLockRifleTargetsEvent(this, baseTargets);
                    weaponListeners.forEach(l -> l.update(baseLockRifleTargetsEvent));
                    break;
                case "OptionalLockRifleEvent1":
                    OptionalLockRifleEvent1 optionalLockRifleEvent1 = new OptionalLockRifleEvent1(this, Integer.parseInt(inputs[1]));
                    weaponListeners.forEach(l -> l.update(optionalLockRifleEvent1));
                    break;
                case "OptionalLockRifleTargetsEvent1":
                    ArrayList<Integer> optTargets = new ArrayList<>();
                    for (int i = 1; i<= inputs.length; i++){
                        optTargets.add(Integer.parseInt(inputs[i]));
                    }
                    TargetsSelectionEvent optionalLockRifleTargetsEvent1 = new TargetsSelectionEvent(this, optTargets);
                    weaponListeners.forEach(l -> l.update(optionalLockRifleTargetsEvent1));
                    break;
            }
        }catch(IllegalArgumentException e){
            clientConnection.asyncSend("Error!");
        }
    }


    private ClientConnection clientConnection;

    public RemoteView(PlayerColor playerColor, ClientConnection c) {
        super(playerColor);
        this.clientConnection=c;
        c.register(this);
        c.asyncSend("Welcome to the game! Your player color is " + playerColor.toString()); //messaggio di benvenuto?
    }

    //lista di update
    @Override
    public void update(NameSetMessage nameSetMessage){
        clientConnection.asyncSend("NameSetMessage," + nameSetMessage.getMessage());
    }

    @Override
    public void update(RunMessage runMessage){
        clientConnection.asyncSend("RunMessage," +runMessage.getMessage());
    }

    @Override
    public void update(ChooseActionMessage chooseActionMessage){
        clientConnection.asyncSend("ChooseActionMessage," + chooseActionMessage.getMessage());
    }

    @Override
    public void update(GenericMessage genericMessage){
        clientConnection.asyncSend("GenericMessage," + genericMessage.getMessage());
    }

    @Override
    public void update(ChoosePowerUpMessage choosePowerUpMessage){
        clientConnection.asyncSend("ChoosePowerUpMessage," + choosePowerUpMessage.getMessage());
    }

    @Override
    public void update(ChooseTeleporterSquareMessage chooseTeleporterSquareMessage){
        clientConnection.asyncSend("ChooseTeleporterSquareMessage," + chooseTeleporterSquareMessage.getMessage());
    }

    @Override
    public void update(ChooseNewtonOpponentMessage chooseNewtonOpponentMessage){
        clientConnection.asyncSend("ChooseNewtonOpponentMessage," + chooseNewtonOpponentMessage.getMessage());
    }

    @Override
    public void update(ChooseNewtonSquareMessage chooseNewtonSquareMessage){
        clientConnection.asyncSend("ChooseNewtonSquareMessage," + chooseNewtonSquareMessage.getMessage());
    }

    @Override
    public void update(ShootMessage shootMessage){
        clientConnection.asyncSend("ShootMessage," + shootMessage.getMessage());
    }

    @Override
    public void update(ShowWeaponCardsMessage showWeaponCardsMessage){
        clientConnection.asyncSend("ShowWeaponCardsMessage," + showWeaponCardsMessage.getMessage());
    }

    @Override
    public void update(AskAlternativeMessage askAlternativeMessage){
        clientConnection.asyncSend("AskAlternativeMessage," + askAlternativeMessage.getMessage());
    }

    @Override
    public void update(BaseLockRifleTargetsMessage baseLockRifleTargetsEvent){
        clientConnection.asyncSend("BaseLockRifleTargetsMessage," + baseLockRifleTargetsEvent.getMessage());
    }

    @Override
    public void update(OptionalLockRifleMessage1 optionalMessage1){
        clientConnection.asyncSend("OptionalLockRifleMessage1," + optionalMessage1.getMessage());
    }

    @Override
    public void update(TargetsSelectionMessage optionalLockRifleTargetsMessage1){
        clientConnection.asyncSend("OptionalLockRifleTargetsMessage1," + optionalLockRifleTargetsMessage1.getMessage());
    }



}
