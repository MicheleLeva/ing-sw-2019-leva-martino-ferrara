package controller;

import model.Decks;
import model.Model;
import model.adrenaline_exceptions.TagbackGrenadeException;
import model.adrenaline_exceptions.TargetingScopeException;
import model.cards.powerups.PowerUp;
import model.cards.powerups.TagbackGrenade;
import model.cards.powerups.TargetingScope;
import model.exchanges.events.*;
import model.map.Square;
import model.player.Player;
import model.player.PlayerColor;
import utils.observer.PowerUpObserver;

import java.util.ArrayList;

public class PowerUpController extends Controller implements PowerUpObserver {
    public PowerUpController(Model model) {
        super(model);
    }

    @Override
    public void update(ChoosePowerUpEvent choosePowerUpEvent) {
        Player currentPlayer = getModel().getPlayer(choosePowerUpEvent.getPlayerColor());
        int input = choosePowerUpEvent.getInput();

        if(currentPlayer.getResources().getPowerUp().isEmpty()){
            String error;
            error = "You have no powerUps.\n";
            choosePowerUpEvent.getView().reportError(error);
            getModel().chooseAction(choosePowerUpEvent.getPlayerColor());
        }

        if (input < 1 || input > currentPlayer.getResources().getPowerUp().size()) {
            String error;
            error = "Invalid input.\n";
            choosePowerUpEvent.getView().reportError(error);
            getModel().choosePowerUp(choosePowerUpEvent.getPlayerColor()); //chiede di reinserire il powerup
        } else {
            PowerUp chosenPowerUp = currentPlayer.getResources().getPowerUp().get(input - 1);
            try {
                chosenPowerUp.usePowerUp(choosePowerUpEvent.getPlayerColor());
                //scarta powerup(è stato usato)
            } catch (TargetingScopeException | TagbackGrenadeException e) {
                choosePowerUpEvent.getView().reportError(e.getMessage());
                getModel().chooseAction(choosePowerUpEvent.getPlayerColor()); //chiede di reinserire azione
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void update(ChooseTeleporterSquareEvent chooseTeleporterSquareEvent) {
        int input = chooseTeleporterSquareEvent.getInput();
        ArrayList<Integer> allIDs = getModel().getGameBoard().getMap().getAllIDs();

        if (!allIDs.contains(input)) { //riempire con: se lo square non esiste, serve una legenda per gli square
            chooseTeleporterSquareEvent.getView().reportError("Invalid square.\nInsert another one.\n");
            getModel().useTeleporter(chooseTeleporterSquareEvent.getPlayerColor());
        } else {
            Square chosenSquare = getModel().getGameBoard().getMap().getSquareFromID(input);
            Player currentPlayer = getModel().getPlayer(chooseTeleporterSquareEvent.getPlayerColor());
            currentPlayer.setPosition(chosenSquare);
            getModel().notifyTeleporter(chooseTeleporterSquareEvent.getPlayerColor());
        }
    }

    @Override
    public void update(ChooseNewtonOpponentEvent chooseNewtonOpponentEvent) {
        PlayerColor currentPlayerColor = chooseNewtonOpponentEvent.getPlayerColor();
        int input = chooseNewtonOpponentEvent.getInput();

        if (input < 1 || input > getModel().getCurrent().getOpponent().size()) {
            chooseNewtonOpponentEvent.getView().reportError("Invalid player.\nInsert another one.\n");
            getModel().useNewton(chooseNewtonOpponentEvent.getPlayerColor()); //richiede di riusare il newton
        } else {
            ArrayList<Player> chosenPlayer = new ArrayList<>();
            chosenPlayer.add(getModel().getCurrent().getOpponent().get(input - 1));
            getModel().getCurrent().setOpponent(chosenPlayer);
            getModel().chooseNewtonSquare(currentPlayerColor, chosenPlayer.get(0));
        }
    }

    @Override
    public void update(ChooseNewtonSquareEvent chooseNewtonSquareEvent) {
        PlayerColor currentPlayerColor = chooseNewtonSquareEvent.getPlayerColor();
        int input = chooseNewtonSquareEvent.getInput();
        Player opponent = getModel().getCurrent().getOpponent().get(0);
        if (getModel().getCurrent().getSquareFromID(input) == null) {
            chooseNewtonSquareEvent.getView().reportError("Invalid square.\n");
            getModel().chooseNewtonSquare(currentPlayerColor, opponent);
        } else {
            Square newSquare = getModel().getCurrent().getSquareFromID(input);
            opponent.setPosition(newSquare);
            getModel().notifyNewton(currentPlayerColor, opponent);
        }
    }

    @Override
    public void update(TargetingScopeEvent event) {
        char choice = event.getChoice();
        if (choice != 'Y' && choice != 'N') {
            event.getView().reportError("Invalid input");
            getModel().getPowerUpNotifier().askTargetingScope(event.getPlayerColor());
        }
        else {

            if (choice == 'Y') {
                getModel().targetingScopeTargets(event.getPlayerColor(),getModel().getCurrent().getAllDamagedPlayer());
            } else {
                getModel().notifyShoot(getModel().getPlayer(event.getPlayerColor()));
            }
        }
    }
    @Override
    public void update(TargetingScopeSelectionEvent event) {
        int input = event.getInput();

        Player currentPlayer = getModel().getPlayer(event.getPlayerColor());
        ArrayList<PowerUp> powerUpsClone = new ArrayList<>(currentPlayer.getResources().getPowerUp());

        if (input < 1 || input > getModel().getCurrent().getAllDamagedPlayer().size()) {
            event.getView().reportError("Invalid input.\nInsert another one.\n");
            getModel().targetingScopeTargets(event.getPlayerColor(),getModel().getCurrent().getAllDamagedPlayer());

        }

        else{
            Player player = getModel().getCurrent().getAllDamagedPlayer().get(input);
            getModel().addDamage(event.getPlayerColor(),player.getPlayerColor(),1);

            for(PowerUp powerUp : powerUpsClone){
                if(powerUp instanceof TargetingScope){
                    getModel().getGameBoard().getDecks().getDiscardedPowerUpDeck().add(powerUp);
                    player.getResources().getPowerUp().remove(powerUp);
                    break;
                }

            }

            for(PowerUp powerUp : player.getResources().getPowerUp()){
                if(powerUp instanceof TargetingScope){
                    getModel().getPowerUpNotifier().askTargetingScope(event.getPlayerColor());
                    return;
                }

            }
            getModel().notifyShoot(getModel().getPlayer(event.getPlayerColor()));
        }

    }

    @Override
    public void update(TagbackGrenadeEvent tagbackGrenadeEvent) {
        Player player = getModel().getPlayer(tagbackGrenadeEvent.getPlayerColor());
        ArrayList<PowerUp> powerUps = player.getResources().getPowerUp();
        getModel().getCurrent().getGrenadePeopleArray().remove(getModel().getPlayer(tagbackGrenadeEvent.getPlayerColor()));
        if (tagbackGrenadeEvent.getInput() == '0'){
            return;
        }
        if (powerUps.get(tagbackGrenadeEvent.getInput() - 1) instanceof TagbackGrenade){
            ((TagbackGrenade)powerUps.get(tagbackGrenadeEvent.getInput() - 1)).usePowerUp(getModel().getTurnManager().getCurrentPlayerColor());
        } else {
            tagbackGrenadeEvent.getView().reportError("Invalid input!");
            getModel().tagbackGranadeRequest(tagbackGrenadeEvent.getPlayerColor(), getModel().getTurnManager().getCurrentPlayerColor());
        }
    }

    @Override
    public void update(DiscardPowerUpEvent discardPowerUpEvent){
        Player currentPlayer = getModel().getPlayer(discardPowerUpEvent.getPlayerColor());
        int input = discardPowerUpEvent.getInput();

        if (input < 1 || input > currentPlayer.getResources().getPowerUp().size()) {
            String error;
            error = "Invalid input.\n";
            discardPowerUpEvent.getView().reportError(error);
            getModel().requestPowerUpDiscard(currentPlayer); //chiede di reinserire il powerup
        } else {
                getModel().discardPowerUp(currentPlayer,input-1);
                //scarta powerup(è stato usato)
        }
    }
}
