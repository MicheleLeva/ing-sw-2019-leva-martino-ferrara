package controller;

import model.Current;
import model.Model;
import model.cards.*;
import model.events.*;
import model.map_package.Square;
import model.player_package.Player;
import model.player_package.PlayerColor;
import utils.observer.WeaponObserver;

import java.util.ArrayList;

public class WeaponController extends Controller implements WeaponObserver {

    private static final String BASE = "base";
    private static final String ALTERNATIVE = "alternative";
    private static final String OPTIONAL1 = "optional1";
    private static final String OPTIONAL2 = "optional2";


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
            getModel().getCurrent().setSelectedWeapon(selectedWeapon);
            getModel().showFireModes(event.getPlayerColor(),selectedWeapon);
        /*if(selectedWeapon.getType() == "alternative")
            getModel().askAlternativeEffect(selectedWeapon);
        else
            selectedWeapon.askBaseRequirements();*/
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

    public void update(OptionalFireModesEvent event){
        Player currentPlayer = getModel().getPlayer(event.getPlayerColor());
        Weapon weapon = getModel().getCurrent().getSelectedWeapon();
        int input = event.getInput();
        if (input < 0 || input >= getModel().getCurrent().getAvailableFireModes().size()){
            String error;
            error = "Invalid input.\n";
            event.getView().reportError(error);
            getModel().showFireModes(event.getPlayerColor(),weapon); //chiede di reinserire input
            return;
        }

        String effectType = weapon.getWeaponTree().getLastActionPerformed().getChildren().get(input).getData().getType();
        weapon.getWeaponTree().updateLastAction(event.getInput());

        if(effectType.equals("end")){
            if(weapon.getWeaponTree().getRoot().getChildren().contains(weapon.getWeaponTree().getLastAction())){
                weapon.getWeaponTree().resetAction();
                getModel().getCurrent().resetCurrent();
                getModel().getCurrent().setSelectedWeapon(weapon);
                getModel().showFireModes(event.getPlayerColor(),weapon);
            }
            else
                getModel().notifyShoot(currentPlayer,getModel().getCurrent().getSelectedBaseTargets());
            return;
        }
        if(effectType.equals(BASE)) {
            weapon.askBaseRequirements(currentPlayer);
            return;
        }
        if(effectType.equals(ALTERNATIVE)) {
            ((WeaponAlternative) weapon).askAlternativeRequirements(currentPlayer);
            return;
        }
        if(effectType.equals(OPTIONAL1)) {
            ((WeaponOptional1) weapon).askOptionalRequirements1(currentPlayer);
            return;
        }
        if(effectType.equals(OPTIONAL2)) {
            ((WeaponOptional2) weapon).askOptionalRequirements2(currentPlayer);

        }
    }

    public void update(TargetsSelectionEvent event){
        Player currentPlayer = getModel().getPlayer(event.getPlayerColor());
        Weapon weapon = getModel().getCurrent().getSelectedWeapon();
        String type = weapon.getWeaponTree().getLastAction().getData().getType();
        Current current = getModel().getCurrent();
        ArrayList<Integer> support = event.getSelectedTargets();
        if(weapon.getWeaponTree().getRoot().getChildren().contains(weapon.getWeaponTree().getLastAction()) &&
                support.size() == 1 && support.get(0) == -1){
            weapon.getWeaponTree().resetAction();
            getModel().getCurrent().resetCurrent();
            getModel().getCurrent().setSelectedWeapon(weapon);
            getModel().showFireModes(event.getPlayerColor(),weapon);
            return;
        }
        if(type.equals(BASE)) {
            checkBaseTargets(event,weapon,current,currentPlayer);
            return;
        }
        if(type.equals(ALTERNATIVE)) {
            checkAlternativeTargets(event,weapon,current,currentPlayer);
            return;
        }
        if(type.equals(OPTIONAL1)) {
            checkOptionalTargets1(event,weapon,current,currentPlayer);
            return;
        }
        if(type.equals(OPTIONAL2)) {
            checkOptionalTargets2(event,weapon,current,currentPlayer);
        }
    }

    public void update(ChooseWeaponSquareEvent event){
        Player currentPlayer = getModel().getPlayer(event.getPlayerColor());
        int input = event.getInput();
        Square square = getModel().getCurrent().getWeaponSquareFromID(input);
        if(square == null){
            event.getView().reportError("Invalid square.\n");
            getModel().chooseWeaponSquare(currentPlayer.getPlayerColor() ,getModel().getCurrent().getAvailableWeaponSquares());
        }
        else{
            getModel().getCurrent().setSelectedWeaponSquare(square);
            Weapon weapon = getModel().getCurrent().getSelectedWeapon();
            String effectType = weapon.getWeaponTree().getLastAction().getData().getType();

            if(effectType.equals(BASE)) {
                weapon.askBaseRequirements(currentPlayer);
                return;
            }
            if(effectType.equals(ALTERNATIVE)){
                ((WeaponAlternative)weapon).askAlternativeRequirements(currentPlayer);
                return;
            }
            if(effectType.equals(OPTIONAL1)) {
                ((WeaponOptional1) weapon).askOptionalRequirements1(currentPlayer);
                return;
            }
            if(effectType.equals(OPTIONAL2)) {
                ((WeaponOptional2) weapon).askOptionalRequirements2(currentPlayer);
            }
        }
    }

    public void checkBaseTargets(TargetsSelectionEvent event,Weapon weapon,Current current,Player currentPlayer){
        ArrayList<Player> selectedPlayers = new ArrayList<>();
        for (Integer target : event.getSelectedTargets()) {
            int temp = event.getSelectedTargets().get(target);

            if(temp!=0) {
                temp--;
                if (temp < 0 || temp >= current.getAvailableBaseTargets().size()) {
                    current.setAvailableBaseTargets(null);
                    String error;
                    error = "Invalid input.\n";
                    event.getView().reportError(error);
                    getModel().selectTargets(event.getPlayerColor(), current.getAvailableBaseTargets(),
                            weapon.getBaseTargetsNumber());
                    return;
                }
                else
                    selectedPlayers.add(getModel().getCurrent().getAvailableBaseTargets().get(target));
            }

        }
        getModel().getCurrent().setSelectedBaseTargets(selectedPlayers);
        weapon.askBaseRequirements(currentPlayer);
    }

    public void checkAlternativeTargets(TargetsSelectionEvent event,Weapon weapon,Current current,Player currentPlayer) {
        ArrayList<Player> selectedPlayers = new ArrayList<>();
        for (Integer target : event.getSelectedTargets()) {
            int temp = event.getSelectedTargets().get(target);


            if(temp!=0) {
                temp--;
                if (temp < 0 || temp >= current.getAvailableAlternativeTargets().size()) {
                    current.setAvailableAlternativeTargets(null);
                    String error;
                    error = "Invalid input.\n";
                    event.getView().reportError(error);
                    getModel().selectTargets(event.getPlayerColor(), current.getAvailableAlternativeTargets(),
                            ((WeaponAlternative) weapon).getAlternativeTargetsNumber());
                    return;
                }
                else
                    selectedPlayers.add(getModel().getCurrent().getAvailableAlternativeTargets().get(target));
            }
        }
        getModel().getCurrent().setSelectedAlternativeTargets(selectedPlayers);
        ((WeaponAlternative)weapon).askAlternativeRequirements(currentPlayer);
    }

    public void checkOptionalTargets1(TargetsSelectionEvent event,Weapon weapon,Current current,Player currentPlayer){
        ArrayList<Player> selectedPlayers = new ArrayList<>();
        for (Integer target : event.getSelectedTargets()) {
            int temp = event.getSelectedTargets().get(target);

            if(temp!=0) {
                temp--;
                if (temp < 0 || temp >= current.getAvailableOptionalTargets1().size()) {
                    current.setAvailableOptionalTargets1(null);
                    String error;
                    error = "Invalid input.\n";
                    event.getView().reportError(error);
                    getModel().selectTargets(event.getPlayerColor(), current.getAvailableOptionalTargets1(),
                            ((WeaponOptional1) weapon).getOptionalTargetsNumber1());
                    return;
                }
                else
                    selectedPlayers.add(getModel().getCurrent().getAvailableOptionalTargets1().get(target));

            }
        }
        getModel().getCurrent().setSelectedOptionalTargets1(selectedPlayers);
        ((WeaponOptional1)weapon).askOptionalRequirements1(currentPlayer);
    }

    public void checkOptionalTargets2(TargetsSelectionEvent event,Weapon weapon,Current current,Player currentPlayer){
        ArrayList<Player> selectedPlayers = new ArrayList<>();
        for (Integer target : event.getSelectedTargets()) {
            int temp = event.getSelectedTargets().get(target);


            if(temp!=0) {
                temp--;
                if (temp < 0 || temp >= current.getAvailableOptionalTargets2().size()) {
                    current.setAvailableOptionalTargets2(null);
                    String error;
                    error = "Invalid input.\n";
                    event.getView().reportError(error);
                    getModel().selectTargets(event.getPlayerColor(), current.getAvailableOptionalTargets2(),
                            ((WeaponOptional2) weapon).getOptionalTargetsNumber2());
                    return;
                }
                else
                    selectedPlayers.add(getModel().getCurrent().getAvailableOptionalTargets2().get(target));
            }
        }
        getModel().getCurrent().setSelectedOptionalTargets2(selectedPlayers);
        ((WeaponOptional2)weapon).askOptionalRequirements2(currentPlayer);
    }

}
