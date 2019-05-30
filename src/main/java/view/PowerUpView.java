package view;

import model.cards.powerups.TargetingScope;
import model.exchanges.events.*;
import model.exchanges.messages.*;
import model.player.PlayerColor;
import utils.Observable;
import utils.update.PowerUpUpdate;

public class PowerUpView extends Observable<Event> implements PowerUpUpdate {
    private final PlayerColor playerColor;
    private final View view;

    public PowerUpView(PlayerColor playerColor , View view){
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
    public void update(ChoosePowerUpMessage choosePowerUpMessage){
        view.printMessage(choosePowerUpMessage.getMessage());

        Event event = new ChoosePowerUpEvent(view , view.inputInt());
        notify(event);

    }
    @Override
    public void update(ChooseTeleporterSquareMessage chooseTeleporterSquareMessage){
        view.printMessage(chooseTeleporterSquareMessage.getMessage());

        Event event = new ChooseTeleporterSquareEvent(view , view.inputInt());
        notify(event);
    }

    @Override
    public void update(ChooseNewtonOpponentMessage chooseNewtonOpponentMessage){
        view.printMessage(chooseNewtonOpponentMessage.getMessage());

        Event event = new ChooseNewtonOpponentEvent(view , view.inputInt());
        notify(event);
    }
    @Override
    public void update(ChooseNewtonSquareMessage chooseNewtonSquareMessage){
        view.printMessage(chooseNewtonSquareMessage.getMessage());

        Event event = new ChooseNewtonSquareEvent(view , view.inputInt());
        notify(event);
    }

    @Override
    public void update(TargetingScopeMessage targetingScopeMessage){
        view.printMessage(targetingScopeMessage.getMessage());

        Event event = new TargetingScopeEvent(view , view.inputChar());
        notify(event);
    }
    @Override
    public void update(TargetingScopeSelectionMessage targetingScopeSelectionMessage){
        view.printMessage(targetingScopeSelectionMessage.getMessage());

        Event event = new TargetingScopeSelectionEvent(view, view.inputInt());
        notify(event);
    }

    @Override
    public void update(TagbackGrenadeMessage tagbackGrenadeMessage) {
        view.printMessage(tagbackGrenadeMessage.getMessage());

        Event event = new TagbackGrenadeEvent(view, view.inputChar());
        notify(event);
    }
}
