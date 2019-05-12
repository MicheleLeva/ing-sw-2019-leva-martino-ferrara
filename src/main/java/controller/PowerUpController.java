package controller;

import model.Model;
import model.adrenaline_exceptions.TagbackGrenadeException;
import model.adrenaline_exceptions.TargetingScopeException;
import model.cards.PowerUp;
import model.events.ChooseNewtonOpponentEvent;
import model.events.ChooseNewtonSquareEvent;
import model.events.ChoosePowerUpEvent;
import model.events.ChooseTeleporterSquareEvent;
import model.map_package.Square;
import model.player_package.Player;
import model.player_package.PlayerColor;
import utils.observer.PowerUpObserver;

import java.util.ArrayList;

public class PowerUpController extends Controller implements PowerUpObserver {
    public PowerUpController(Model model){
        super(model);
    }
    @Override
    public void update(ChoosePowerUpEvent choosePowerUpEvent){
        Player currentPlayer = getModel().getPlayer(choosePowerUpEvent.getPlayerColor());
        int input = choosePowerUpEvent.getInput();

        if (input < 1 || input > currentPlayer.getResources().getPowerUp().size()){
            String error;
            error = "Invalid input.\n";
            choosePowerUpEvent.getView().reportError(error);
            getModel().choosePowerUp(choosePowerUpEvent.getPlayerColor()); //chiede di reinserire il powerup
        }
        else{
            PowerUp chosenPowerUp = currentPlayer.getResources().getPowerUp().get(input - 1);
            try{
                chosenPowerUp.usePowerUp(choosePowerUpEvent.getPlayerColor());
                //scarta powerup(è stato usato)
            }
            catch(TargetingScopeException | TagbackGrenadeException e){
                choosePowerUpEvent.getView().reportError(e.getMessage());
                getModel().chooseAction(choosePowerUpEvent.getPlayerColor()); //chiede di reinserire azione
            }

            catch (Exception e){
            }
        }
    }
    @Override
    public void update(ChooseTeleporterSquareEvent chooseTeleporterSquareEvent){
        if(0 > 1){ //riempire con: se lo square non esiste, serve una legenda per gli square
            chooseTeleporterSquareEvent.getView().reportError("Invalid square.\nInsert another one.\n");
            getModel().useTeleporter(chooseTeleporterSquareEvent.getPlayerColor());
        }
        else{
            Square chosenSquare = null;
            //inizializzare lo square
            Player currentPlayer = getModel().getPlayer(chooseTeleporterSquareEvent.getPlayerColor());
            currentPlayer.setPosition(chosenSquare);
            getModel().notifyTeleporter(chooseTeleporterSquareEvent.getPlayerColor());
        }
    }

    @Override
    public void update(ChooseNewtonOpponentEvent chooseNewtonOpponentEvent){
        PlayerColor currentPlayerColor = chooseNewtonOpponentEvent.getPlayerColor();
        int input = chooseNewtonOpponentEvent.getInput();

        if(input < 1 || input > getModel().getCurrent().getOpponent().size()){
            chooseNewtonOpponentEvent.getView().reportError("Invalid player.\nInsert another one.\n");
            getModel().useNewton(chooseNewtonOpponentEvent.getPlayerColor()); //richiede di riusare il newton
        }
        else{
            ArrayList<Player> chosenPlayer = new ArrayList<>();
            chosenPlayer.add(getModel().getCurrent().getOpponent().get(input - 1));
            getModel().getCurrent().setOpponent(chosenPlayer);
            getModel().chooseNewtonSquare(currentPlayerColor , chosenPlayer.get(0));
        }
    }
    @Override
    public void update(ChooseNewtonSquareEvent chooseNewtonSquareEvent){
        PlayerColor currentPlayerColor = chooseNewtonSquareEvent.getPlayerColor();
        int input = chooseNewtonSquareEvent.getInput();
        Player opponent = getModel().getCurrent().getOpponent().get(0);
        if(getModel().getCurrent().getSquareFromID(input) == null){
            chooseNewtonSquareEvent.getView().reportError("Invalid square.\n");
            getModel().chooseNewtonSquare(currentPlayerColor , opponent);
        }
        else{
            Square newSquare = getModel().getCurrent().getSquareFromID(input);
            opponent.setPosition(newSquare);
            getModel().notifyNewton(currentPlayerColor , opponent);
        }
    }

}
