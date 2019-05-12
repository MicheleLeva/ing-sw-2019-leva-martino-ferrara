package network.client;

import model.events.*;
import utils.Observer;
import utils.observer.ActionObserver;
import utils.observer.PowerUpObserver;
import utils.observer.WeaponObserver;
import utils.update.ActionUpdate;
import utils.update.GameUpdate;
import utils.update.PowerUpUpdate;
import utils.update.WeaponUpdate;
import view.PlayerView;

import java.util.ArrayList;
import java.util.List;

public class Translator implements ActionObserver, PowerUpObserver, WeaponObserver, Observer<String> {

    //emulate Controller(Observer) and Model(Notify)

    Client client;

    PlayerView playerView;

    public final List<ActionUpdate> actionListeners = new ArrayList();

    public final List<GameUpdate> gameListeners = new ArrayList();

    public final List<PowerUpUpdate> powerUpListeners = new ArrayList();

    public final List<WeaponUpdate> weaponListeners = new ArrayList();

    public void register(WeaponUpdate observer){
        synchronized (weaponListeners){
            weaponListeners.add(observer);
        }
    }

    public void deregister(WeaponUpdate observer){
        synchronized (weaponListeners){
            weaponListeners.remove(observer);
        }
    }

    public void register(PowerUpUpdate observer){
        synchronized (powerUpListeners){
            powerUpListeners.add(observer);
        }
    }

    public void deregister(PowerUpUpdate observer){
        synchronized (powerUpListeners){
            powerUpListeners.remove(observer);
        }
    }

    public void register(GameUpdate observer){
        synchronized (gameListeners){
            gameListeners.add(observer);
        }
    }

    public void deregister(GameUpdate observer){
        synchronized (gameListeners){
            gameListeners.remove(observer);
        }
    }

    public void register(ActionUpdate observer) {
        synchronized (actionListeners) {
            actionListeners.add(observer);
        }
    }

    public void deregister(ActionUpdate observer) {
        synchronized (actionListeners) {
            actionListeners.remove(observer);
        }
    }

    @Override
    public void update(String message) {
        try{
            String[] inputs = message.split(",");
            //a seconda della stringa che arriva ricreo il messaggio
            //un case per ogni messaggio
            switch(inputs[0]){
                case "NameSetMessage":
                    NameSetMessage nameSetMessage = new NameSetMessage(inputs[1]);
                    gameListeners.forEach(l -> l.update(nameSetMessage));
                    break;
                case "RunMessage":
                    RunMessage runMessage = new RunMessage(inputs[1]);
                    gameListeners.forEach(l -> l.update(runMessage));
                    break;
                case "ChooseActionMessage":
                    ChooseActionMessage chooseActionMessage = new ChooseActionMessage(inputs[1]);
                    actionListeners.forEach(l -> l.update(chooseActionMessage));
                    break;
                case "GenericMessage":
                    GenericMessage genericMessage = new GenericMessage(inputs[1]);
                    gameListeners.forEach(l -> l.update(genericMessage));
                    break;
                case "ChoosePowerUpMessage":
                    ChoosePowerUpMessage choosePowerUpMessage = new ChoosePowerUpMessage(inputs[1]);
                    powerUpListeners.forEach(l -> l.update(choosePowerUpMessage));
                    break;
                case "ChooseTeleporterSquareMessage":
                    ChooseTeleporterSquareMessage chooseTeleporterSquareMessage = new ChooseTeleporterSquareMessage(inputs[1]);
                    powerUpListeners.forEach(l -> l.update(chooseTeleporterSquareMessage));
                    break;
                case "ChooseNewtonOpponentMessage":
                    ChooseNewtonOpponentMessage chooseNewtonOpponentMessage = new ChooseNewtonOpponentMessage(inputs[1]);
                    powerUpListeners.forEach(l -> l.update(chooseNewtonOpponentMessage));
                    break;
                case "ChooseNewtonSquareMessage":
                    ChooseNewtonSquareMessage chooseNewtonSquareMessage = new ChooseNewtonSquareMessage(inputs[1]);
                    powerUpListeners.forEach((l -> l.update(chooseNewtonSquareMessage)));
                    break;
                case "ShootMessage":
                    ShootMessage shootMessage = new ShootMessage(inputs[1]);
                    gameListeners.forEach(l -> l.update(shootMessage));
                    break;
                case "ShowWeaponCardsMessage":
                    ShowWeaponCardsMessage showWeaponCardsMessage = new ShowWeaponCardsMessage(inputs[1]);
                    weaponListeners.forEach(l -> l.update(showWeaponCardsMessage));
                    break;
                case "AskAlternativeMessage":
                    AskAlternativeMessage askAlternativeMessage = new AskAlternativeMessage(inputs[1]);
                    weaponListeners.forEach(l -> l.update(askAlternativeMessage));
                    break;
                case "BaseLockRifleTargetsMessage":
                    BaseLockRifleTargetsMessage baseLockRifleTargetsMessage = new BaseLockRifleTargetsMessage(inputs[1], Integer.parseInt(inputs[2]));
                    weaponListeners.forEach(l -> l.update(baseLockRifleTargetsMessage));
                    break;
                case "OptionalLockRifleMessage1":
                    OptionalLockRifleMessage1 optionalLockRifleMessage1 = new OptionalLockRifleMessage1(inputs[1]);
                    weaponListeners.forEach(l -> l.update(optionalLockRifleMessage1));
                    break;
                case "OptionalLockRifleTargetsMessage1":
                    OptionalLockRifleTargetsMessage1 optionalLockRifleTargetsMessage1 = new OptionalLockRifleTargetsMessage1(inputs[1], Integer.parseInt(inputs[2]));
                    weaponListeners.forEach(l -> l.update(optionalLockRifleTargetsMessage1));
                    break;
            }
        }catch(IllegalArgumentException e){
            System.out.println("Error reading from stream!");
        }
    }

    public Translator(Client client){
        this.client = client;
    }

    //lista di update

    /*
    Serve un GameObserver per il set del nome e del colore(?)

    @Override
    public void update(NameSetEvent nameSetEvent){
        network.client.asyncSend(nameSetEvent.getName());
    }
     */

    @Override
    public void update(ActionEvent actionEvent){
        client.asyncSend("ActionEvent," + actionEvent.getInput());
    }

    @Override
    public void update(ChoosePowerUpEvent choosePowerUpEvent){
        client.asyncSend("ChoosePowerUpEvent," + choosePowerUpEvent.getInput());
    }

    @Override
    public void update(ChooseTeleporterSquareEvent chooseTeleporterSquareEvent){
        client.asyncSend("ChooseTeleporterSquareEvent," + chooseTeleporterSquareEvent.getInput());
    }

    @Override
    public void update(ChooseNewtonOpponentEvent chooseNewtonOpponentEvent){
        client.asyncSend("ChooseNewtonOpponentEvent," + chooseNewtonOpponentEvent.getInput());
    }

    @Override
    public void update(ChooseNewtonSquareEvent chooseNewtonSquareEvent){
        client.asyncSend("ChooseNewtonSquareEvent," + chooseNewtonSquareEvent.getInput());
    }

    @Override
    public void update(WeaponSelectionEvent event){
        client.asyncSend("WeaponSelectionEvent," + event.getIndex());
    }

    @Override
    public void update(AlternativeSelectionEvent event){
        client.asyncSend("AlternativeSelectionEvent," + event.getIndex());
    }

    @Override
    public void update(BaseLockRifleTargetsEvent event){
        //todo fare il toString dell'array
        client.asyncSend("BaseLockRifleTargetsEvent," + event.getSelectedTargets());
    }

    @Override
    public void update(OptionalLockRifleEvent1 event){
        client.asyncSend("OptionalLockRifleEvent1," + event.getInput());
    }

    @Override
    public void update(OptionalLockRifleTargetsEvent1 event){
        //todo fare il toString dell'array
        client.asyncSend("OptionalLockRifleTargetsEvent1," + event.getSelectedTargets());
    }

}
