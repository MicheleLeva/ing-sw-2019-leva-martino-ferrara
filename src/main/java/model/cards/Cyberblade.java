package model.cards;

import model.Ammo;
import model.Model;
import model.map_package.Square;
import model.player_package.Player;

import java.util.ArrayList;

public class Cyberblade extends WeaponOptional2 {

    private String optionalText2;


    public Cyberblade(String name, Ammo baseCost, Ammo optionalCost1, Ammo optionalCost2, int baseDamage, int optionalDamage1, int optionalDamage2, int baseMarks,
                int optionalMarks1, int optionalMarks2, int baseTargetsNumber, int optionalTargetsNumber1,
                int optionalTargetsNumber2, String baseText, String optionalText1, String optionalText2, Model model) {

        super(name,baseCost,optionalCost1,optionalCost2,baseDamage,optionalDamage1,optionalDamage2,baseMarks,optionalMarks1,optionalMarks2,
                baseTargetsNumber,optionalTargetsNumber1,optionalTargetsNumber2,baseText,optionalText1,optionalText2,model);
    }

    @Override
    public void start(Player player) {
        getModel().showFireModes(player.getPlayerColor(), getWeaponTree().availableAction());
    }

    @Override
    public void askOptionalRequirements2(Player currentPlayer) {
        if(getModel().getCurrent().getOptionalCounter1()== 0) {
            Player oldPlayer = getModel().getCurrent().getSelectedBaseTargets().get(0);
            ArrayList<Player> availableTargets = getModel().getPlayersInSameSquare(currentPlayer);
            for(Player player : availableTargets){
                if(player.getName()==oldPlayer.getName())
                    availableTargets.remove(player);
            }
            getModel().optionalLockRifleTargets1(currentPlayer.getPlayerColor(), availableTargets, this.getBaseTargetsNumber());
            getModel().getCurrent().incrementOptionalCounter2();
        }
        else
            useOptionalFireMode2(currentPlayer,getModel().getCurrent().getSelectedOptionalTargets2());
    }

    @Override
    public void useOptionalFireMode2(Player currentPlayer, ArrayList<Player> selectedTargets) {
        for(Player target : selectedTargets){
            getModel().addDamage(currentPlayer.getPlayerColor(),target.getPlayerColor(),this.getBaseDamage());
            getModel().addMark(currentPlayer.getPlayerColor(),target.getPlayerColor(),getBaseMarks());
        }
        //sistemare il pagamento
        currentPlayer.getResources().removeFromAvailableAmmo(this.getBaseCost());
        //
        getModel().checkNextWeaponAction(this,currentPlayer,selectedTargets);
    }

    @Override
    public void askOptionalRequirements1(Player currentPlayer) {
        if(getModel().getCurrent().getOptionalCounter1()== 0) {
            ArrayList<Square> squares = getModel().runnableSquare(1, currentPlayer.getPosition());
            getModel().chooseWeaponSquare(currentPlayer.getPlayerColor(), squares);
            getModel().getCurrent().incrementOptionalCounter1();
        }
        else
            useOptionalFireMode1(currentPlayer,null);
    }

    @Override
    public void useOptionalFireMode1(Player currentPlayer, ArrayList<Player> selectedTargets) {
        //Pagamento
        currentPlayer.getResources().removeFromAvailableAmmo(this.getOptionalCost1());
        //
        getModel().checkNextWeaponAction(this,currentPlayer,selectedTargets);
    }

    @Override
    public void askBaseRequirements(Player currentPlayer) {
        if(getModel().getCurrent().getBaseCounter() == 0) {
            ArrayList<Player> availableTargets = getModel().getPlayersInSameSquare(currentPlayer);
            getModel().baseLockRifleTargets(currentPlayer.getPlayerColor(), availableTargets, this.getBaseTargetsNumber());
            getModel().getCurrent().incrementBaseCounter();
        }
        else
            useBaseFireMode(currentPlayer,getModel().getCurrent().getSelectedBaseTargets());
    }

    @Override
    public void useBaseFireMode(Player currentPlayer, ArrayList<Player> selectedTargets) {

        for(Player target : selectedTargets){
            getModel().addDamage(currentPlayer.getPlayerColor(),target.getPlayerColor(),this.getBaseDamage());
            getModel().addMark(currentPlayer.getPlayerColor(),target.getPlayerColor(),getBaseMarks());
        }
        //sistemare il pagamento
        currentPlayer.getResources().removeFromAvailableAmmo(this.getBaseCost());
        //
        getModel().checkNextWeaponAction(this,currentPlayer,selectedTargets);
    }

    @Override
    public String getOptionalText1() {
        return optionalText1;
    }

    @Override
    public String getOptionalText2(){
        return this.optionalText2;
    }

}
