package model.cards.weapons;

import model.game.Ammo;
import model.game.Model;
import model.map.Square;
import model.player.Player;

import java.util.ArrayList;
/**
 * Representation of the Vortex Cannon weapon
 * @author Marco Maria Ferrara
 */
public class Vortexcannon extends WeaponOptional1 {
    public Vortexcannon(String name, Ammo pickUpCost, Ammo baseCost, Ammo optionalCost1, int baseDamage,
                        int optionalDamage1, int baseMarks, int optionalMarks1, int baseTargetsNumber,
                        int optionalTargetsNumber1, Model model) {

        super(name, pickUpCost, baseCost, optionalCost1, baseDamage, optionalDamage1, baseMarks, optionalMarks1,
                baseTargetsNumber, optionalTargetsNumber1, model);
    }

    /**
     * Asks the requirements of the first optional fire mode for the VortexCannon
     * @param currentPlayer current player
     */
    @Override
    public void askBaseRequirements(Player currentPlayer) {
        if (getModel().getCurrent().getBaseCounter() == 0) {
            //gets all the visible squares by the current player and removes from the list the current player's position
            ArrayList<Square> availableSquares = getModel().getVisibleSquares(currentPlayer);
            ArrayList<Square> chosenSquares = new ArrayList<>();
            availableSquares.remove(currentPlayer.getPosition());
            for (Square square : availableSquares) {
                for (Player player : getModel().getAllSpawnedPlayers())
                    if (player != currentPlayer && Model.runnableSquare(1, square).contains(player.getPosition()) &&
                    !chosenSquares.contains(square))
                        chosenSquares.add(square);

            }
            //if there is no available square the player is granted another chance at choosing his fire modes
            if(chosenSquares.isEmpty()){
                getModel().getGameNotifier().notifyPlayer("No available targets with this Fire Mode choose " +
                                "another one", currentPlayer.getPlayerColor());
                this.getWeaponTree().resetAction();
                getModel().resetCurrent();
                getModel().getCurrent().setSelectedWeapon(this);
                getModel().showFireModes(currentPlayer.getPlayerColor(),this);
                return;
            }
            getModel().getCurrent().setAvailableWeaponSquares(chosenSquares);
            getModel().getCurrent().incrementBaseCounter();
            getModel().chooseWeaponSquare(currentPlayer.getPlayerColor(), chosenSquares);
            return;

        }


        if (getModel().getCurrent().getBaseCounter() == 1) {
            Square square = getModel().getCurrent().getSelectedWeaponSquare();
            //gets all the players at distance one from the vortex's square
            ArrayList<Player> availableTargets = getModel().getPlayersAtDistance(1, currentPlayer, square);
            getModel().getCurrent().setAvailableBaseTargets(availableTargets);
            getModel().getCurrent().incrementBaseCounter();
            getModel().selectTargets(currentPlayer.getPlayerColor(), availableTargets, this.getBaseTargetsNumber());
        } else
            useBaseFireMode(currentPlayer, getModel().getCurrent().getSelectedBaseTargets());
    }

    /**
     * Uses the Base fire Mode for the VortexCannon
     * @param currentPlayer current player
     * @param selectedTargets targets chosen for the second optional fire Mode
     */
    public void useBaseFireMode(Player currentPlayer, ArrayList<Player> selectedTargets) {

        for (Player target : selectedTargets) {
            target.setPosition(getModel().getCurrent().getSelectedWeaponSquare());
            getModel().addDamage(currentPlayer.getPlayerColor(), target.getPlayerColor(), this.getBaseDamage());
            getModel().addMark(currentPlayer.getPlayerColor(), target.getPlayerColor(), this.getBaseMarks());
        }

        getModel().checkNextWeaponAction(this, currentPlayer);
    }

    /**
     * Asks the requirements of the first optional fire mode for the VortexCannon
     * @param currentPlayer current player
     */
    @Override
    public void askOptionalRequirements1(Player currentPlayer) {
        if (getModel().getCurrent().getOptionalCounter1() == 0) {
            Square square = getModel().getCurrent().getSelectedWeaponSquare();
            //gets all players at distance 1 from the vortex
            ArrayList<Player> availableTargets = getModel().getPlayersAtDistance(1, currentPlayer, square);
            availableTargets.remove(getModel().getCurrent().getSelectedBaseTargets().get(0));
            availableTargets.remove(currentPlayer);
            getModel().getCurrent().setAvailableOptionalTargets1(availableTargets);
            getModel().getCurrent().incrementOptionalCounter1();
            getModel().selectTargets(currentPlayer.getPlayerColor(), availableTargets, this.getOptionalTargetsNumber1());

        } else
            useOptionalFireMode1(currentPlayer, getModel().getCurrent().getSelectedOptionalTargets1());
    }

    /**
     * Uses the first optional fire Mode for the VortexCannon
     * @param currentPlayer current player
     * @param selectedTargets targets chosen for the second optional fire Mode
     */
    @Override
    public void useOptionalFireMode1(Player currentPlayer, ArrayList<Player> selectedTargets) {
        generalUseWithMove(currentPlayer, selectedTargets, this, this.getWeaponTree().getLastAction().
                getData().getType());
    }
}
