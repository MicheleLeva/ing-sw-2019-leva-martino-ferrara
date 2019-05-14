package model.cards;

import model.Ammo;
import model.Model;
import model.player_package.Player;

import java.util.ArrayList;

public class Hellion extends WeaponAlternative {

    String alternativeText;
    String baseText;

    public Hellion(String name, Ammo baseCost, Ammo alternativeCost, int baseDamage, int alternativeDamage, int baseMarks,
                   int alternativeMarks, int baseTargetsNumber, int alternativeTargetsNumber,String baseText,String alternativeText, Model model){
        super(name,baseCost,alternativeCost,baseDamage,alternativeDamage,baseMarks,alternativeMarks,baseTargetsNumber,alternativeTargetsNumber,baseText,alternativeText,model);

    }

    @Override
    public String getAlternativeText() {
        return null;
    }

    @Override
    public void askAlternativeRequirements(Player currentPlayer) {
        ArrayList<Player> finalList = new ArrayList<>();
        ArrayList<Player> temp = new ArrayList<>(getModel().getPlayersAtDistanceMore(1,currentPlayer));
        ArrayList<Player> availableTargets = new ArrayList<>(getModel().getVisiblePlayers(currentPlayer));
        for(Player player : availableTargets){
            if(temp.contains(player))
                finalList.add(player);

        }
        getModel().AlternativeHellionTargets(currentPlayer.getPlayerColor(),availableTargets,this.getBaseTargetsNumber());
    }

    @Override
    public void useAlternativeFireMode(Player currentPlayer, ArrayList<Player> selectedTargets) {
            ArrayList<Player> finalList = new ArrayList<>();
            for(Player target : selectedTargets){
                target.getPlayerBoard().getDamageCounter().addDamage(currentPlayer.getPlayerColor(),getBaseDamage());
                target.getPlayerBoard().getMarkCounter().addMarks(currentPlayer.getPlayerColor(),getBaseMarks());
            }
            for(Player player : getModel().getAllPlayers()){
                if(player.getPosition()==selectedTargets.get(0).getPosition() && player!=currentPlayer && !selectedTargets.contains(player)){
                    player.getPlayerBoard().getMarkCounter().addMarks(currentPlayer.getPlayerColor(),getBaseMarks());
                    finalList.add(player);
                }

            }
            ArrayList<Player> temp = new ArrayList<>(getModel().getCurrent().getSelectedBaseTargets());
            temp.addAll(finalList);
            currentPlayer.getResources().removeFromAvailableAmmo(this.getBaseCost());
            currentPlayer.getActionTree().endAction();
            getModel().notifyShoot(currentPlayer, temp);
    }

    @Override
    public void askBaseRequirements(Player currentPlayer) {
        ArrayList<Player> finalList = new ArrayList<>();
        ArrayList<Player> temp = new ArrayList<>(getModel().getPlayersAtDistanceMore(1,currentPlayer));
        ArrayList<Player> availableTargets = new ArrayList<>(getModel().getVisiblePlayers(currentPlayer));
        for(Player player : availableTargets){
            if(temp.contains(player))
                finalList.add(player);

        }
        getModel().baseLockRifleTargets(currentPlayer.getPlayerColor(),finalList,this.getBaseTargetsNumber());
    }

    @Override
    public void useBaseFireMode(Player currentPlayer, ArrayList<Player> selectedTargets) {
        ArrayList<Player> finalList = new ArrayList<>();
        for(Player target : selectedTargets){
            target.getPlayerBoard().getDamageCounter().addDamage(currentPlayer.getPlayerColor(),getBaseDamage());
            target.getPlayerBoard().getMarkCounter().addMarks(currentPlayer.getPlayerColor(),getBaseMarks());
        }
        for(Player player : getModel().getAllPlayers()){
            if(player.getPosition()==selectedTargets.get(0).getPosition() && player!=currentPlayer && !selectedTargets.contains(player)){
                player.getPlayerBoard().getMarkCounter().addMarks(currentPlayer.getPlayerColor(),getBaseMarks());
                finalList.add(player);
            }

        }
        ArrayList<Player> temp = new ArrayList<>(getModel().getCurrent().getSelectedBaseTargets());
        temp.addAll(finalList);
        currentPlayer.getResources().removeFromAvailableAmmo(this.getBaseCost());
        currentPlayer.getActionTree().endAction();
        getModel().notifyShoot(currentPlayer, temp);
    }
}
