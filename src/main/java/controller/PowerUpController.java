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

/**
 *MVC Controller for the powerUp-related actions
 */
public class PowerUpController extends Controller implements PowerUpObserver {
    public PowerUpController(Model model) {
        super(model);
    }

    /**
     * Controls if the received input is valid and in that case uses the chosen powerUp
     * @param choosePowerUpEvent class containing the player's selection for the powerUp
     */
    @Override
    public void update(ChoosePowerUpEvent choosePowerUpEvent) {
        if (getModel().getPlayer(choosePowerUpEvent.getPlayerColor()).isAfk()){
            return;
        }
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

    /**
     * Controls if the received input is valid and in that case moves the player to the
     * square selected by the teleporter powerUp
     * @param chooseTeleporterSquareEvent class containing the selected square by the player
     *                                    for the teleporter powerUP
     */
    @Override
    public void update(ChooseTeleporterSquareEvent chooseTeleporterSquareEvent) {
        if (getModel().getPlayer(chooseTeleporterSquareEvent.getPlayerColor()).isAfk()){
            return;
        }
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

    /**
     * Controls if the received input is valid and in that case proceeds to ask the current player
     * where to move the selected opponent for the Newton powerUp
     * @param chooseNewtonOpponentEvent class containing the selected opponent for the Newton powerUp
     */
    @Override
    public void update(ChooseNewtonOpponentEvent chooseNewtonOpponentEvent) {
        if (getModel().getPlayer(chooseNewtonOpponentEvent.getPlayerColor()).isAfk()){
            return;
        }
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

    /**
     * Controls if the received input is valid and in that moves the selected opponent to
     * the chosen square for the Newton powerUp
     * @param chooseNewtonSquareEvent contains the square selected for the Newton powerUp
     */
    @Override
    public void update(ChooseNewtonSquareEvent chooseNewtonSquareEvent) {
        if (getModel().getPlayer(chooseNewtonSquareEvent.getPlayerColor()).isAfk()){
            return;
        }
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

    /**
     * Controls if the input equals Y or N and if so, proceeds to either finish the shoot
     * action or use the Targeting Scope powerUp
     * @param event contains the choice of the current player(YES = Y | NO = N)
     */
    @Override
    public void update(TargetingScopeEvent event) {
        if (getModel().getPlayer(event.getPlayerColor()).isAfk()){
            return;
        }
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

    /**
     * Controls if the input is valid and in that case proceeds to add damage to the
     * selected opponent for the Targeting Scope powerUp
     * @param event contains the target selected for the Targeting Scope powerUp
     */
    @Override
    public void update(TargetingScopeSelectionEvent event) {
        if (getModel().getPlayer(event.getPlayerColor()).isAfk()){
            return;
        }
        int input = event.getInput();

        Player currentPlayer = getModel().getPlayer(event.getPlayerColor());
        ArrayList<PowerUp> powerUpsClone = new ArrayList<>(currentPlayer.getResources().getPowerUp());

        if (input < 1 || input > getModel().getCurrent().getAllDamagedPlayer().size()) {
            event.getView().reportError("Invalid input.\nInsert another one.\n");
            getModel().targetingScopeTargets(event.getPlayerColor(),getModel().getCurrent().getAllDamagedPlayer());

        }

        else{
            Player player = getModel().getCurrent().getAllDamagedPlayer().get(input-1);
            getModel().addDamage(event.getPlayerColor(),player.getPlayerColor(),1);


            for(PowerUp powerUp : powerUpsClone){
                if(powerUp instanceof TargetingScope){
                    getModel().getGameBoard().getDecks().getDiscardedPowerUpDeck().add(powerUp);
                    currentPlayer.getResources().getPowerUp().remove(powerUp);
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

    /**
     * Controls if the input is valid ( 0 or 1 ) and in that case it either uses the tagaback grenade or not.
     * If the tagback grenade timer the input is set to 0 by default
     * @param tagbackGrenadeEvent contains the choice of the player: 1 to use the Tagback Grenade, 0 otherwise
     */
    @Override
    public void update(TagbackGrenadeEvent tagbackGrenadeEvent) {
        if (getModel().getPlayer(tagbackGrenadeEvent.getPlayerColor()).isAfk()){
            return;
        }
        Player player = getModel().getPlayer(tagbackGrenadeEvent.getPlayerColor());
        if (!getModel().getTurnCurrent().getGrenadePeopleArray().contains(player)){
            return;
        }
        ArrayList<PowerUp> powerUps = player.getResources().getPowerUp();
        int input = tagbackGrenadeEvent.getInput();
        if (input == 0){
            getModel().getTurnCurrent().getGrenadePeopleArray().remove(getModel().getPlayer(tagbackGrenadeEvent.getPlayerColor()));
            return;
        }
        if (powerUps.get(input - 1) instanceof TagbackGrenade){
            try{
                ((TagbackGrenade)powerUps.get(input - 1)).usePowerUp(player.getPlayerColor());
                getModel().getTurnCurrent().getGrenadePeopleArray().remove(getModel().getPlayer(tagbackGrenadeEvent.getPlayerColor()));
            } catch (TagbackGrenadeException e){
                e.printStackTrace();
            }

        } else {
            tagbackGrenadeEvent.getView().reportError("Invalid input!");
            getModel().tagbackGranadeRequest(player, getModel().getTurnManager().getCurrentPlayer());
        }
    }

    /**
     * Controls if the powerUp selection is valid and in that case proceeds to remove the
     * chosen powerUp
     * @param discardPowerUpEvent contains the selected powerup to discard
     */
    @Override
    public void update(DiscardPowerUpEvent discardPowerUpEvent){
        if (getModel().getPlayer(discardPowerUpEvent.getPlayerColor()).isAfk()){
            return;
        }
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
