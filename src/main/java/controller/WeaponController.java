package controller;

import model.Model;
import model.TurnManager;
import model.cards.*;
import model.events.*;
import model.map_package.Square;
import model.player_package.Player;
import model.player_package.PlayerColor;
import utils.observer.WeaponObserver;
import view.PlayerView;

import java.util.ArrayList;

public class WeaponController extends Controller implements WeaponObserver {
    public WeaponController(Model model){
        super(model);
    }

    public void update(WeaponSelectionEvent event){
        int input = event.getIndex();
        Player currentPlayer = getModel().getPlayer(event.getPlayerColor());
        if (input < 1 || input > currentPlayer.getResources().getReloadedWeapon().size()){
            String error;
            error = "Invalid input.\n";
            event.getView().reportError(error);
            getModel().showWeaponCards(event.getPlayerColor()); //chiede di reinserire l'arma
        }
        else {
            Weapon selectedWeapon = currentPlayer.getResources().getReloadedWeapon().get(input - 1);
            currentPlayer.setSelectedWeapon(selectedWeapon);
            selectedWeapon.start(currentPlayer);
        /*if(selectedWeapon.getType() == "alternative")
            getModel().askAlternativeEffect(selectedWeapon);
        else
            selectedWeapon.askBaseRequirements();*/
        }

    }

    public void update(AlternativeSelectionEvent event){
        Player currentPlayer = getModel().getPlayer(event.getPlayerColor());
        Weapon weapon = currentPlayer.getSelectedWeapon();
        int input = event.getIndex();
        if (input < 1 || input > 2){
            String error;
            error = "Invalid input.\n";
            event.getView().reportError(error);
            getModel().askAlternativeEffect(event.getPlayerColor(),weapon); //chiede di reinserire input
        }
        else if(input == 1)
            ((WeaponAlternative)weapon).askBaseRequirements(currentPlayer);
        else
            ((WeaponAlternative)weapon).askAlternativeRequirements(currentPlayer);
    }

    public void update(BaseLockRifleTargetsEvent event){
        ArrayList<Player> selectedPlayers = new ArrayList<>();
        Player currentPlayer = getModel().getPlayer(event.getPlayerColor());
        for(Integer target : event.getSelectedTargets()){
            selectedPlayers.add(getModel().getCurrent().getAvailableBaseTargets().get(target));
        }
        getModel().getCurrent().setSelectedBaseTargets(selectedPlayers);
        Weapon weapon = currentPlayer.getSelectedWeapon();
        weapon.useBaseFireMode(currentPlayer,selectedPlayers);
    }

    public void update(OptionalLockRifleEvent1 event){
        Player currentPlayer = getModel().getPlayer(event.getPlayerColor());
        Weapon weapon = currentPlayer.getSelectedWeapon();
        int input = event.getInput();
        if (input < 0 || input > 1){
            String error;
            error = "Invalid input.\n";
            event.getView().reportError(error);
            getModel().askAlternativeEffect(event.getPlayerColor(),weapon); //chiede di reinserire input
        }
        else if(input == 0)
            getModel().notifyShoot(currentPlayer,getModel().getCurrent().getSelectedBaseTargets());
        else
            ((WeaponOptional1)weapon).askOptionalRequirements1(currentPlayer);
    }

    public void update(OptionalLockRifleTargetsEvent1 event){
        ArrayList<Player> selectedPlayers = new ArrayList<>();
        Player currentPlayer = getModel().getPlayer(event.getPlayerColor());
        Weapon weapon = getModel().getCurrent().getSelectedWeapon();
        String type = weapon.getWeaponTree().getLastAction().getData().getType();
        if(type.equals("base")) {
            for (Integer target : event.getSelectedTargets()) {
                selectedPlayers.add(getModel().getCurrent().getAvailableBaseTargets().get(target));
            }
            getModel().getCurrent().setSelectedBaseTargets(selectedPlayers);
            weapon.askBaseRequirements(currentPlayer);
        }
        if(type.equals("alternative")) {
            for (Integer target : event.getSelectedTargets()) {
                selectedPlayers.add(getModel().getCurrent().getAvailableAlternativeTargets().get(target));
            }
            getModel().getCurrent().setSelectedAlternativeTargets(selectedPlayers);
            ((WeaponAlternative)weapon).askAlternativeRequirements(currentPlayer);
        }
        if(type.equals("optional1")) {
            for (Integer target : event.getSelectedTargets()) {
                selectedPlayers.add(getModel().getCurrent().getAvailableOptionalTargets1().get(target));
            }
            getModel().getCurrent().setSelectedOptionalTargets1(selectedPlayers);
            ((WeaponOptional1)weapon).askOptionalRequirements1(currentPlayer);
        }
        if(type.equals("optional2")) {
            for (Integer target : event.getSelectedTargets()) {
                selectedPlayers.add(getModel().getCurrent().getAvailableOptionalTargets2().get(target));
            }
            getModel().getCurrent().setSelectedBaseTargets(selectedPlayers);
            ((WeaponOptional2)weapon).askOptionalRequirements2(currentPlayer);
        }
    }

    @Override
    public void update(ReloadEndTurnEvent reloadEndTurnEvent){
        char input = reloadEndTurnEvent.getInput();
        PlayerColor currentPlayerColor = reloadEndTurnEvent.getPlayerColor();

        if(input != 'Y' && input != 'N'){
            reloadEndTurnEvent.getView().reportError("Invalid input.\n");
            getModel().askReloadEndTurn(currentPlayerColor);
            return;
        }

        if (input == 'N'){
            getModel().endTurn();
        }
        else{
            getModel().requestWeaponReload(currentPlayerColor);
        }
    }

    @Override
    public void update(WeaponReloadEvent weaponReloadEvent){
        int input = weaponReloadEvent.getInput();
        PlayerColor currentPlayerColor = weaponReloadEvent.getPlayerColor();
        Player currentPlayer = getModel().getPlayer(currentPlayerColor);
        ArrayList<Weapon> reloadableWeapon = currentPlayer.getResources().getReloadableWeapon();
        if(input < 1 || input > reloadableWeapon.size()){
            weaponReloadEvent.getView().reportError("Invalid input.\n");
            getModel().requestWeaponReload(currentPlayerColor);
        }
        else{
            Weapon chosenWeapon = reloadableWeapon.get(input - 1);
            /*if (può fare la ricarica){
                //scartare powerup
                getModel().reload...
            }
            else{
                weaponReloadEvent.getView().reportError("Insufficient Ammo.\n");
                getModel().updateTurn(); nel turno normale può scegliere se ricaricare o finire, nel frenesia ridecide l'azione
            }*/
        }
    }

    public void update(AlternativeHellionTargetsEvent event){
        ArrayList<Player> selectedPlayers = new ArrayList<>();
        Player currentPlayer = getModel().getPlayer(event.getPlayerColor());
        for(Integer target : event.getSelectedTargets()){
            selectedPlayers.add(getModel().getCurrent().getAvailableBaseTargets().get(target));
        }
        getModel().getCurrent().setSelectedBaseTargets(selectedPlayers);
        Weapon weapon = currentPlayer.getSelectedWeapon();
        ((WeaponAlternative)weapon).useAlternativeFireMode(currentPlayer,selectedPlayers);
    }

    public void update(OptionalThorEvent2 event){
        Player currentPlayer = getModel().getPlayer(event.getPlayerColor());
        Weapon weapon = currentPlayer.getSelectedWeapon();
        int input = event.getInput();
        if (input < 0 || input > 1){
            String error;
            error = "Invalid input.\n";
            event.getView().reportError(error);
            getModel().askAlternativeEffect(event.getPlayerColor(),weapon); //chiede di reinserire input
        }
        else if(input == 0)
            getModel().notifyShoot(currentPlayer,getModel().getCurrent().getSelectedBaseTargets());
        else
            ((WeaponOptional2)weapon).askOptionalRequirements2(currentPlayer);
    }

    public void update(OptionalThorTargetsEvent2 event){
        ArrayList<Player> selectedPlayers = new ArrayList<>();
        Player currentPlayer = getModel().getPlayer(event.getPlayerColor());
        for(Integer target : event.getSelectedTargets()){
            selectedPlayers.add(getModel().getCurrent().getAvailableOptionalTargets1().get(target));
        }
        getModel().getCurrent().setSelectedOptionalTargets1(selectedPlayers);
        Weapon weapon = currentPlayer.getSelectedWeapon();
        ((WeaponOptional2)weapon).useOptionalFireMode2(currentPlayer, selectedPlayers);
    }

    public void update(OptionalFireModesEvent event){
        Player currentPlayer = getModel().getPlayer(event.getPlayerColor());
        Weapon weapon = currentPlayer.getSelectedWeapon();
        int input = event.getInput();
        if (input < 0 || input > weapon.getWeaponTree().getLastAction().getChildren().size()){
            String error;
            error = "Invalid input.\n";
            event.getView().reportError(error);
            getModel().askAlternativeEffect(event.getPlayerColor(),weapon); //chiede di reinserire input
        }
        else if(Checks.canUseFireMode(weapon, weapon.getWeaponTree().getLastAction().getChildren().get(input).getData().getType()) == false) {
            String error;
            error = "Invalid input.\n";
            event.getView().reportError(error);
            getModel().showFireModes(event.getPlayerColor(), weapon.getWeaponTree().availableAction());
        }
        else {
            String effectType = weapon.getWeaponTree().getLastAction().getChildren().get(input).getData().getType();
            ////
            //
            weapon.getWeaponTree().updateLastAction(event.getInput());
            if(effectType.equals("base"))
                weapon.askBaseRequirements(currentPlayer);
            if(effectType.equals("alternative"))
                ((WeaponAlternative)weapon).askAlternativeRequirements(currentPlayer);
            if(effectType.equals("optional1"))
                ((WeaponOptional1)weapon).askOptionalRequirements1(currentPlayer);
            if(effectType.equals("optional2"))
                ((WeaponOptional2)weapon).askOptionalRequirements2(currentPlayer);
        }
    }

    public void update(ChooseWeaponSquareEvent event){
        Player currentPlayer = getModel().getPlayer(event.getPlayerColor());
        int input = event.getInput();
        if(getModel().getCurrent().getSquareFromID(input) == null){
            event.getView().reportError("Invalid square.\n");
            getModel().chooseWeaponSquare(currentPlayer.getPlayerColor() ,getModel().getCurrent().getAvailableOptionalSquares());
        }
        else{
            Square newSquare = getModel().getCurrent().getSquareFromID(input);
            currentPlayer.setPosition(newSquare);
            Weapon weapon = getModel().getCurrent().getSelectedWeapon();
            String effectType = getModel().getCurrent().getSelectedWeapon().getWeaponTree().getLastAction().getData().getType();

            if(effectType.equals("base"))
                weapon.askBaseRequirements(currentPlayer);
            if(effectType.equals("alternative"))
                ((WeaponAlternative)weapon).askAlternativeRequirements(currentPlayer);
            if(effectType.equals("optional1"))
                ((WeaponOptional1)weapon).askOptionalRequirements1(currentPlayer);
            if(effectType.equals("optional2"))
                ((WeaponOptional2)weapon).askOptionalRequirements2(currentPlayer);
        }
    }


}
