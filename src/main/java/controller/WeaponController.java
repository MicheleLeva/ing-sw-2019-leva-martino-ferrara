package controller;

import model.Model;
import model.TurnManager;
import model.cards.Electroscythe;
import model.cards.Weapon;
import model.cards.WeaponAlternative;
import model.cards.WeaponOptional1;
import model.events.*;
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
        for(Integer target : event.getSelectedTargets()){
            selectedPlayers.add(getModel().getCurrent().getAvailableBaseTargets().get(target));
        }
        getModel().getCurrent().setSelectedOptionalTargets1(selectedPlayers);
        Weapon weapon = currentPlayer.getSelectedWeapon();
        ((WeaponOptional1)weapon).useOptionalFireMode1(currentPlayer,selectedPlayers);
    }
}
