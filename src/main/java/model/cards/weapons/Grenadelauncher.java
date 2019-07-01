package model.cards.weapons;

import model.game.Ammo;
import model.game.Model;
import model.map.Square;
import model.player.Player;

import java.util.ArrayList;
/**
 * Representation of the Grenade Launcher weapon
 */
public class Grenadelauncher extends WeaponOptional1 {

    public Grenadelauncher(String name, Ammo pickUpCost, Ammo baseCost, Ammo optionalCost1, int baseDamage, int optionalDamage1, int baseMarks,
                     int optionalMarks1, int baseTargetsNumber, int optionalTargetsNumber1, Model model) {

        super(name,pickUpCost,baseCost,optionalCost1,baseDamage,optionalDamage1,baseMarks,optionalMarks1,baseTargetsNumber,
                optionalTargetsNumber1,model);
    }
    /**
     * Asks the requirements of the Base fire mode for the GrenadeLauncher
     * @param currentPlayer current player
     */
    @Override
    public void askBaseRequirements(Player currentPlayer) {
        if(getModel().getCurrent().getBaseCounter() == 0) {
            ArrayList<Player> availableTargets = getModel().getVisiblePlayers(currentPlayer);
            if(     availableTargets.isEmpty() &&
                    !this.getWeaponTree().getRoot().getChildren().contains(this.getWeaponTree().getLastAction())){
                getModel().getGameNotifier().notifyPlayer("No available targets for this base Fire Mode",
                        currentPlayer.getPlayerColor());
                this.getWeaponTree().resetAction();
                getModel().notifyShoot(currentPlayer);
                return;
            }
            if(     availableTargets.isEmpty() &&
                    this.getWeaponTree().getRoot().getChildren().contains(this.getWeaponTree().getLastAction())){
                getModel().getGameNotifier().notifyPlayer("No available targets for this base Fire Mode",
                        currentPlayer.getPlayerColor());
                this.getWeaponTree().resetAction();
                getModel().resetCurrent();
                getModel().getCurrent().setSelectedWeapon(this);
                getModel().showFireModes(currentPlayer.getPlayerColor(),this);
                return;
            }
            endAskTargets(currentPlayer,availableTargets,this,this.getWeaponTree().getLastAction().getData().getType());
            return;
        }
        if(getModel().getCurrent().getBaseCounter() == 1) {
            ArrayList<Square> squares = Model.runnableSquare(1, getModel().getCurrent().getSelectedBaseTargets().get(0).getPosition());
            endAskSquares(currentPlayer,squares,this.getWeaponTree().getLastAction().getData().getType());
        }
        else
            useBaseFireMode(currentPlayer,getModel().getCurrent().getSelectedBaseTargets());
    }
    /**
     * Asks the requirements of the first optional fire mode for the GrenadeLauncher
     * @param currentPlayer current player
     */
    @Override
    public void askOptionalRequirements1(Player currentPlayer){
        if(getModel().getCurrent().getOptionalCounter1()== 0) {
            ArrayList<Square> squares = new ArrayList<>();
            ArrayList<Square> squaresBase = getModel().getVisibleSquares(currentPlayer);
            for(Square square : squaresBase){
                for(Player player : getModel().getAllSpawnedPlayers())
                    if(player.getPosition() == square && !squares.contains(square))
                        squares.add(square);
            }
            squares.remove(currentPlayer.getPosition());
            if(squares.isEmpty()){
                getModel().getGameNotifier().notifyPlayer("No available squares you missed the shot!!!",
                        currentPlayer.getPlayerColor());
                getModel().payFireMode(currentPlayer,this);
                getModel().getCurrent().incrementOptionalCounter1();
                getModel().checkNextWeaponAction(this,currentPlayer);
                return;
            }
            endAskSquares(currentPlayer,squares,this.getWeaponTree().getLastAction().getData().getType());
        }
        else
            useOptionalFireMode1(currentPlayer,getModel().getCurrent().getSelectedOptionalTargets1());
    }

    /**
     * Uses the Base fire Mode for the GrenadeLauncher
     * @param currentPlayer current player
     * @param selectedTargets targets chosen for the second optional fire Mode
     */
    @Override
    public void useBaseFireMode(Player currentPlayer, ArrayList<Player> selectedTargets){
        generalUseWithMove(currentPlayer,selectedTargets,this,this.getWeaponTree().getLastAction().getData().getType());
    }

    /**
     * Uses the first optional fire Mode for the GrenadeLauncher
     * @param currentPlayer current player
     * @param selectedTargets targets chosen for the second optional fire Mode
     */
    @Override
    public void useOptionalFireMode1(Player currentPlayer, ArrayList<Player> selectedTargets){
        for(Player player : getModel().getAllSpawnedPlayers()){
            if(player != currentPlayer && getModel().getCurrent().getSelectedWeaponSquare() == player.getPosition()){
                getModel().addDamage(currentPlayer.getPlayerColor(), player.getPlayerColor(), this.getOptionalDamage1());
                getModel().addMark(currentPlayer.getPlayerColor(), player.getPlayerColor(), this.getOptionalMarks1());
            }
        }
        getModel().payFireMode(currentPlayer,this);
        getModel().checkNextWeaponAction(this,currentPlayer);
    }

}
