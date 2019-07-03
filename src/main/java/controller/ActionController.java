package controller;

import model.game.CLI;
import model.game.Model;
import model.adrenaline_exceptions.*;
import model.exchanges.events.QuitAfkEvent;
import model.exchanges.events.VoteMapEvent;
import model.exchanges.events.ActionEvent;
import model.player.action.Action;
import model.player.action.ActionCreator;
import model.player.Player;
import model.player.PlayerColor;
import model.player.action.KeyMap;
import utils.observer.ActionObserver;
import view.View;

/**
 * MVC Controller for the actions selection
 * @author Michele Leva, Stefano Martino, Marco Maria Ferrara
 */
public class ActionController extends Controller implements ActionObserver {
    /**
     * Constructor for the Action Controller class
     * @param model model class
     */
    public ActionController(Model model){
        super(model);
    }

    /**
     * Controls if the received input corresponds to a valid action and if so proceeds
     * to use the selected action
     * @param actionEvent contains the input related to a specific action
     */
    public void update(ActionEvent actionEvent){
        if (getModel().getPlayer(actionEvent.getPlayerColor()).isAfk()){
            return;
        }
        PlayerColor playerColor = actionEvent.getPlayerColor();
        View view = actionEvent.getView();
        char move = actionEvent.getInput();

        if(!getModel().getTurnManager().isPlayerTurn(playerColor)){
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

        //change tree to use powerups and 'end' command every turn

        Player player = getModel().getTurnManager().getPlayerFromColor(playerColor); //current player

        if(!player.getActionTree().checkAction(move)){
            String error = "You can't perform this move now.\n";
            view.reportError(error);
            getModel().chooseAction(playerColor);
            return;
        }
        //the valid action is possible
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
            e.printStackTrace();
        }
    }

    /**
     * Allows the player to re-enter the game after being afk
     */
    @Override
    public void update(QuitAfkEvent quitAfkEvent) {
        getModel().wakeUpPlayer(getModel().getPlayer(quitAfkEvent.getPlayerColor()));
    }

    /**
     * Controls if the received input corresponds to one of the available maps and if so adds to vote
     * of the current player to those of the other players
     * @param voteMapEvent contains the input corresponding to one of the available maps
     */
    @Override
    public void update(VoteMapEvent voteMapEvent) {
        Player player = getModel().getPlayer(voteMapEvent.getPlayerColor());
        if (player.hasVoted() || player.isAfk()){
            String error = CLI.getRed() + "Too late! Your vote has been ignored" + CLI.getResetString();
            voteMapEvent.getView().reportError(error);
            return;
        }
        int input = Character.getNumericValue(voteMapEvent.getInput());
        if (input < 1 || input > 4){
            voteMapEvent.getView().reportError("Invalid input!");
            getModel().mapVote(getModel().getPlayer(voteMapEvent.getPlayerColor()));
        } else {
            getModel().getMapVotes().add(input);
            String message = "You voted. Wait for the other players.";
            getModel().getGameNotifier().notifyPlayer(message, voteMapEvent.getPlayerColor());
            player.setVote(true);
            getModel().getTurnCurrent().setReceivedInput(true);
        }
    }
}

