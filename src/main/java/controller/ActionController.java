package controller;

import model.Model;
import model.Turn;
import model.TurnManager;
import model.adrenaline_exceptions.EmptySquareException;
import model.adrenaline_exceptions.NoPowerUpException;
import model.adrenaline_exceptions.WallException;
import model.events.ActionEvent;
import model.player_package.Action;
import model.player_package.ActionCreator;
import model.player_package.Player;
import model.player_package.PlayerColor;
import model.player_package.action.KeyMap;
import utils.observer.ActionObserver;
import view.PlayerView;

public class ActionController extends Controller implements ActionObserver {
    //override di tutti gli update
    public ActionController(Model model){
        super(model);
    }

    public void update(ActionEvent actionEvent){
        PlayerColor playerColor = actionEvent.getPlayerColor();
        PlayerView view = actionEvent.getView();
        char move = actionEvent.getInput();

        if(!TurnManager.isPlayerTurn(playerColor)){
            String error = "It's not your turn.\n";
            view.reportError(error);
            //richiedi di inserire azione dal model
            return;
        }

        if(!KeyMap.isValid(move)){
            String error = "Invalid action.\n";
            view.reportError(error);
            //richiedi di inserire azione dal model
            return;
        }

        //modifica tree in modo che ad ogni turno si possano fare usePowerUp e end

        Player player = getModel().getTurnManager().getPlayerFromColor(playerColor); //giocatore che ha mosso

        if(!player.getActionTree().checkAction(move)){
            String error = "You can't perform this move now.\n";
            view.reportError(error);
            //richiedi di inserire azione dal model
            return;
        }
        //l'azione è valida e possibile
        Action action = ActionCreator.createAction(move);
        try{
            action.perform(getModel() , playerColor);
        }

        catch(EmptySquareException e){
            view.reportError(e.getMessage());
            //richiedi di inserire azione dal model;
        }
        catch(WallException e){
            view.reportError(e.getMessage());
            //richiedi di inserire azione dal model;
        }
        catch(NoPowerUpException e){
            view.reportError(e.getMessage());
        }
        catch(Exception e){
            view.reportError(e.getMessage());
            //richiedi di inserire azione dal model;
        }
    }
}

