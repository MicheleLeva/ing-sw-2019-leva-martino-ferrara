package view;

import model.events.*;
import model.player_package.PlayerColor;
import utils.Observable;
import utils.observable.PowerUpObservable;
import utils.update.PowerUpUpdate;

public class PowerUpView extends Observable<Event> implements PowerUpUpdate {
    private final PlayerColor playerColor;
    private final PlayerView view;

    public PowerUpView(PlayerColor playerColor , PlayerView view){
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
    public void update(ChoosePowerUpMessage choosePowerUpMessage){
        view.printMessage(choosePowerUpMessage.getMessage());

        Event event = new ChoosePowerUpEvent(view , view.inputInt());
        notify(event);
        //listeners.forEach(l -> l.update(new ChoosePowerUpEvent(view , view.inputInt())));
    }
    @Override
    public void update(ChooseTeleporterSquareMessage chooseTeleporterSquareMessage){
        view.printMessage(chooseTeleporterSquareMessage.getMessage());

        Event event = new ChooseTeleporterSquareEvent(view , view.inputInt());
        notify(event);
        //listeners.forEach(l -> l.update(new ChooseTeleporterSquareEvent(view , view.inputInt())));
    }

    @Override
    public void update(ChooseNewtonOpponentMessage chooseNewtonOpponentMessage){
        view.printMessage(chooseNewtonOpponentMessage.getMessage());

        Event event = new ChooseNewtonOpponentEvent(view , view.inputInt());
        notify(event);
        //listeners.forEach(l -> l.update(new ChooseNewtonOpponentEvent(view , view.inputInt())));
    }
    @Override
    public void update(ChooseNewtonSquareMessage chooseNewtonSquareMessage){
        view.printMessage(chooseNewtonSquareMessage.getMessage());

        Event event = new ChooseNewtonSquareEvent(view , view.inputInt());
        notify(event);
        //listeners.forEach(l -> l.update(new ChooseNewtonSquareEvent(view , view.inputInt())));
    }
}
