package controller;

import model.Model;
import model.adrenaline_exceptions.*;
import model.exchanges.events.QuitAfkEvent;
import model.exchanges.events.VoteMapEvent;
import model.turn.TurnManager;
import model.exchanges.events.ActionEvent;
import model.player.action.Action;
import model.player.action.ActionCreator;
import model.player.Player;
import model.player.PlayerColor;
import model.player.action.KeyMap;
import utils.observer.ActionObserver;
import view.View;

public class ActionController extends Controller implements ActionObserver {
    //override di tutti gli update
    public ActionController(Model model){
        super(model);
    }

    public void update(ActionEvent actionEvent){
        PlayerColor playerColor = actionEvent.getPlayerColor();
        View view = actionEvent.getView();
        char move = actionEvent.getInput();

        if(!TurnManager.isPlayerTurn(playerColor)){
            String error = "It's not your turn.\n";
            view.reportError(error);
            return;
        }

        if(!KeyMap.isValid(move)){
            String error = "Invalid action.\n";
            view.reportError(error);
            getModel().chooseAction(playerColor);
            return;
        }

        //modifica tree in modo che ad ogni turno si possano fare usePowerUp e end

        Player player = getModel().getTurnManager().getPlayerFromColor(playerColor); //giocatore che ha mosso

        if(!player.getActionTree().checkAction(move)){
            String error = "You can't perform this move now.\n";
            view.reportError(error);
            getModel().chooseAction(playerColor);
            return;
        }
        //l'azione è valida e possibile
        Action action = ActionCreator.createAction(move);
        try{
            action.perform(getModel() , playerColor);
        }

        catch(EmptySquareException | WallException | NoPowerUpException | CannotPayException | NoReloadableWeaponsException
                | InsufficientAmmoException  | NoReloadedWeaponsExceptions e){
            view.reportError(e.getMessage());
            getModel().chooseAction(playerColor);
        }
        catch(MaxAmmoException e){
            view.reportError(e.getMessage());
            getModel().updateAction();
        }
        catch(Exception e){
        }
    }

    @Override
    public void update(QuitAfkEvent quitAfkEvent) {
        getModel().wakeUpPlayer(getModel().getPlayer(quitAfkEvent.getPlayerColor()));
    }

    @Override
    public void update(VoteMapEvent voteMapEvent) {
        int input = Character.getNumericValue(voteMapEvent.getInput());
        if (input < 1 || input > 4){
            voteMapEvent.getView().reportError("Invalid input!");
            getModel().mapVote(getModel().getPlayer(voteMapEvent.getPlayerColor()));
        } else {
            getModel().getMapVotes().add(input);
            getModel().getCurrent().setReceivedInput(true);
        }
    }
}

