package model.cards.weapons;

import model.Ammo;
import model.Model;
import model.map.Square;
import model.player.Player;

import java.util.ArrayList;

public class Rocketlauncher extends WeaponOptional2 {
    public Rocketlauncher(String name, Ammo picUpCost, Ammo baseCost, Ammo optionalCost1, Ammo optionalCost2, int baseDamage, int optionalDamage1, int optionalDamage2, int baseMarks,
                     int optionalMarks1, int optionalMarks2, int baseTargetsNumber, int optionalTargetsNumber1,
                     int optionalTargetsNumber2, Model model) {

        super(name,picUpCost,baseCost,optionalCost1,optionalCost2,baseDamage,optionalDamage1,optionalDamage2,baseMarks,optionalMarks1,optionalMarks2,
                baseTargetsNumber,optionalTargetsNumber1,optionalTargetsNumber2,model);
    }

    private Square originalSquare;

    /**
     * Asks the requirements of the second optional fire mode for the RocketLauncher
     * @param currentPlayer current player
     */
    @Override
    public void askOptionalRequirements2(Player currentPlayer) {
        if(getModel().getCurrent().getOptionalCounter2()== 0) {
            ArrayList<Player> availableTargets = new ArrayList<>();
            for(Player player : getModel().getAllPlayers())
                if(player.getPosition() == originalSquare && player!=currentPlayer)
                    availableTargets.add(player);
            if(!availableTargets.contains(getModel().getCurrent().getSelectedBaseTargets().get(0)))
                availableTargets.add(getModel().getCurrent().getSelectedBaseTargets().get(0));
            getModel().getCurrent().setAvailableOptionalTargets2(availableTargets);
            getModel().getCurrent().incrementOptionalCounter2();
            useOptionalFireMode2(currentPlayer,availableTargets);
        }
    }

    /**
     * Uses the second optional fire Mode for the RocketLauncher
     * @param currentPlayer current player
     * @param selectedTargets targets chosen for the second optional fire Mode
     */
    @Override
    public void useOptionalFireMode2(Player currentPlayer, ArrayList<Player> selectedTargets) {
        generalUse(currentPlayer, selectedTargets, this, this.getWeaponTree().getLastAction().getData().getType());
    }

    /**
     * Asks the requirements of the first optional fire mode for the RocketLauncher
     * @param currentPlayer current player
     */
    @Override
    public void askOptionalRequirements1(Player currentPlayer) {
        if(getModel().getCurrent().getOptionalCounter1()== 0) {
            ArrayList<Square> squares = getModel().runnableSquare(2, currentPlayer.getPosition());
            endAskSquares(currentPlayer,squares,this.getWeaponTree().getLastAction().getData().getType());
        }

        else
            useOptionalFireMode1(currentPlayer,getModel().getCurrent().getSelectedOptionalTargets1());
    }

    /**
     * Uses the first optional fire Mode for the RocketLauncher
     * @param currentPlayer current player
     * @param selectedTargets targets chosen for the second optional fire Mode
     */
    @Override
    public void useOptionalFireMode1(Player currentPlayer, ArrayList<Player> selectedTargets) {
        changePlayerPositionUse(currentPlayer,selectedTargets);
    }

    /**
     * Asks the requirements of the Base fire mode for the RocketLauncher
     * @param currentPlayer current player
     */
    @Override
    public void askBaseRequirements(Player currentPlayer) {
        if(getModel().getCurrent().getBaseCounter() == 0) {
            ArrayList<Player> sameSquarePlayers = getModel().getPlayersInSameSquare(currentPlayer);
            ArrayList<Player> availableTargets = getModel().getVisiblePlayers(currentPlayer);
            if(sameSquarePlayers.contains(currentPlayer))
                sameSquarePlayers.remove(currentPlayer);
            for(Player player : sameSquarePlayers)
                if(availableTargets.contains(player))
                    availableTargets.remove(player);

            endAskTargets(currentPlayer,availableTargets,this,this.getWeaponTree().getLastAction().getData().getType());
            return;
        }
        if(getModel().getCurrent().getBaseCounter()== 1) {
            if(getModel().getCurrent().getSelectedBaseTargets().isEmpty()){
                getModel().getCurrent().incrementBaseCounter();
                askBaseRequirements(currentPlayer);
                return;
            }
            originalSquare = getModel().getCurrent().getSelectedBaseTargets().get(0).getPosition();
            ArrayList<Square> squares = getModel().runnableSquare(1, getModel().getCurrent().getSelectedBaseTargets().get(0).getPosition());
            endAskSquares(currentPlayer,squares,this.getWeaponTree().getLastAction().getData().getType());
        }
        else
            useBaseFireMode(currentPlayer,getModel().getCurrent().getSelectedBaseTargets());
    }

    /**
     * Uses the Base fire Mode for the RocketLauncher
     * @param currentPlayer current player
     * @param selectedTargets targets chosen for the second optional fire Mode
     */
    @Override
    public void useBaseFireMode(Player currentPlayer, ArrayList<Player> selectedTargets) {
        if(!getModel().getCurrent().getSelectedBaseTargets().isEmpty()) {
            for (Player target : selectedTargets) {
                target.setPosition(getModel().getCurrent().getSelectedWeaponSquare());
                getModel().addDamage(currentPlayer.getPlayerColor(), target.getPlayerColor(), this.getBaseDamage());
                getModel().addMark(currentPlayer.getPlayerColor(), target.getPlayerColor(), getBaseMarks());
            }
            getModel().checkNextWeaponAction(this, currentPlayer, selectedTargets);
        }
        else{
            this.getWeaponTree().resetAction();
            getModel().notifyShoot(currentPlayer);
        }
    }

}
