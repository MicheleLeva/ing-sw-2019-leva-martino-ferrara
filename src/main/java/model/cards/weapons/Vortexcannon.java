package model.cards.weapons;

import model.Ammo;
import model.Model;
import model.map.Square;
import model.player.Player;

import java.util.ArrayList;

public class Vortexcannon extends WeaponOptional1 {
    public Vortexcannon(String name, Ammo pickUpCost, Ammo baseCost, Ammo optionalCost1, int baseDamage, int optionalDamage1, int baseMarks,
                        int optionalMarks1, int baseTargetsNumber, int optionalTargetsNumber1, Model model) {

        super(name, pickUpCost, baseCost, optionalCost1, baseDamage, optionalDamage1, baseMarks, optionalMarks1, baseTargetsNumber,
                optionalTargetsNumber1, model);
    }
    @Override
    public void askBaseRequirements(Player currentPlayer) {
        if (getModel().getCurrent().getBaseCounter() == 0) {
            ArrayList<Square> availableSquares = getModel().getVisibleSquares(currentPlayer);
            ArrayList<Square> chosenSquares = new ArrayList<>();
            if (availableSquares.contains(currentPlayer.getPosition()))
                availableSquares.remove(currentPlayer.getPosition());
            for (Square square : availableSquares) {
                for (Player player : getModel().getAllPlayers())
                    if (player != currentPlayer && getModel().runnableSquare(1, square).contains(player.getPosition()))
                        chosenSquares.add(square);

            }
            //check squares vuoti
            if(chosenSquares.isEmpty()){
                getModel().getGameNotifier().notifyGeneric("No available targets with this Fire Mode choose another one");
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
            ArrayList<Player> availableTargets = getModel().getPlayersAtDistance(1, currentPlayer, square);
            getModel().getCurrent().setAvailableBaseTargets(availableTargets);
            getModel().getCurrent().incrementBaseCounter();
            getModel().selectTargets(currentPlayer.getPlayerColor(), availableTargets, this.getBaseTargetsNumber());
        } else
            useBaseFireMode(currentPlayer, getModel().getCurrent().getSelectedBaseTargets());
    }


    public void askOptionalRequirements1(Player currentPlayer) {
        if (getModel().getCurrent().getOptionalCounter1() == 0) {
            Square square = getModel().getCurrent().getSelectedWeaponSquare();
            ArrayList<Player> availableTargets = getModel().getPlayersAtDistance(1, currentPlayer, square);
            if(availableTargets.contains(getModel().getCurrent().getSelectedBaseTargets().get(0)))
                availableTargets.remove(getModel().getCurrent().getSelectedBaseTargets().get(0));
            if(availableTargets.contains(currentPlayer))
                availableTargets.remove(currentPlayer);
            getModel().getCurrent().setAvailableOptionalTargets1(availableTargets);
            getModel().getCurrent().incrementOptionalCounter1();
            getModel().selectTargets(currentPlayer.getPlayerColor(), availableTargets, this.getOptionalTargetsNumber1());
            return;
        } else
            useOptionalFireMode1(currentPlayer, getModel().getCurrent().getSelectedOptionalTargets1());
    }

    public void useBaseFireMode(Player currentPlayer, ArrayList<Player> selectedTargets) {

        for (Player target : selectedTargets) {
            target.setPosition(getModel().getCurrent().getSelectedWeaponSquare());
            getModel().addDamage(currentPlayer.getPlayerColor(), target.getPlayerColor(), this.getBaseDamage());
            getModel().addMark(currentPlayer.getPlayerColor(), target.getPlayerColor(), this.getBaseMarks());
        }

        getModel().checkNextWeaponAction(this, currentPlayer, selectedTargets);    }

    public void useOptionalFireMode1(Player currentPlayer, ArrayList<Player> selectedTargets) {
        generalUseWithMove(currentPlayer, selectedTargets, this, this.getWeaponTree().getLastAction().getData().getType());
    }
}
