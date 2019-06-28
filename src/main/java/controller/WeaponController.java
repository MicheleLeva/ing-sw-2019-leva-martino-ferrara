package controller;

import model.Current;
import model.Model;
import model.cards.powerups.PowerUp;
import model.cards.powerups.TargetingScope;
import model.cards.weapons.*;
import model.exchanges.events.*;
import model.map.Square;
import model.player.Player;
import model.player.PlayerColor;
import utils.observer.WeaponObserver;

import java.util.ArrayList;

/**
 * MVC Controller for the weapon-related actions
 */
public class WeaponController extends Controller implements WeaponObserver {

    private static final String BASE = "base";
    private static final String ALTERNATIVE = "alternative";
    private static final String OPTIONAL1 = "optional1";
    private static final String OPTIONAL2 = "optional2";


    /**
     * Constructor for the WeaponController class
     */
    public WeaponController(Model model){
        super(model);
    }

    /**
     * Controls if the received input corresponds to a valid weapon, and in that case,
     * shows the available fire modes for that weapon
     * @param event contains the index of the weapon selected by the player
     */
    @Override
    public void update(WeaponSelectionEvent event){
        if (getModel().getPlayer(event.getPlayerColor()).isAfk()){
            return;
        }
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
        }

    }

    /**
     * Controls if the received input corresponds to a reloadable weapon and in that case
     * proceeds to reload it
     * @param weaponReloadEvent contains the index of the selected weapon to reload
     */
    @Override
    public void update(WeaponReloadEvent weaponReloadEvent){
        if (getModel().getPlayer(weaponReloadEvent.getPlayerColor()).isAfk()){
            return;
        }
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
            getModel().getCurrent().setSelectedWeapon(chosenWeapon);
            getModel().askReloadPayment(currentPlayer,chosenWeapon);
        }
    }

    /**
     * Controls if the received index corresponds to a valid fire mode and in that case
     * proceeds to use the chosen fire mode
     * @param event contains the index of the selected fire mode for the current weapon
     */
    @Override
    public void update(OptionalFireModesEvent event){
        if (getModel().getPlayer(event.getPlayerColor()).isAfk()){
            return;
        }
        Player currentPlayer = getModel().getPlayer(event.getPlayerColor());
        Weapon weapon = getModel().getCurrent().getSelectedWeapon();
        int input = event.getInput();
        if (input < 1 || input > getModel().getCurrent().getAvailableFireModes().size()){
            String error;
            error = "Invalid input.\n";
            event.getView().reportError(error);
            getModel().showFireModes(event.getPlayerColor(),weapon); //chiede di reinserire input
            return;
        }

        String effectType = getModel().getCurrent().getAvailableFireModes().get(input-1).getData().getType();

        for(int i = 0; i<weapon.getWeaponTree().getLastActionPerformed().getChildren().size(); i++){
            if(weapon.getWeaponTree().getLastActionPerformed().getChildren().get(i).getData().getType().equals(effectType)) {
                weapon.getWeaponTree().updateLastAction(i);
                break;
            }
        }

        if(effectType.equals("return")){
            weapon.getWeaponTree().resetAction();
            getModel().resetCurrent();
            getModel().chooseAction(event.getPlayerColor());
            return;
        }

        if(effectType.equals("end")){
            for(PowerUp powerUp : currentPlayer.getResources().getPowerUp()){
                if(powerUp instanceof TargetingScope && !getModel().getCurrent().getAllDamagedPlayer().isEmpty()){
                    getModel().getCurrent().addAllTargetingScopes((TargetingScope) powerUp);
                }
            }
            for(PowerUp powerUp : currentPlayer.getResources().getPowerUp()){
                if(powerUp instanceof TargetingScope && !getModel().getCurrent().getAllDamagedPlayer().isEmpty()){
                    getModel().getCurrent().setLastTargetingScope((TargetingScope)powerUp);
                    getModel().getPowerUpNotifier().askTargetingScope(currentPlayer.getPlayerColor(),powerUp);
                    return;
                }
            }
            getModel().notifyShoot(currentPlayer);
            return;
        }
        if(effectType.equals(BASE)) {
            weapon.askBaseRequirements(currentPlayer);
            return;
        }
        if(effectType.equals(ALTERNATIVE)||effectType.equals(OPTIONAL1)||effectType.equals(OPTIONAL2)) {
            getModel().askFireModePayment(currentPlayer,weapon,effectType);

        }

    }

    /**
     * Controls if the received inputs correspond to valid targets and in that case
     * proceeds to ask the next requirement for the current weapon
     * @param event contains the index of the selected targets to shoot at
     */
    @Override
    public void update(TargetsSelectionEvent event){
        if (getModel().getPlayer(event.getPlayerColor()).isAfk()){
            return;
        }
        Player currentPlayer = getModel().getPlayer(event.getPlayerColor());
        Weapon weapon = getModel().getCurrent().getSelectedWeapon();
        String type = weapon.getWeaponTree().getLastAction().getData().getType();
        Current current = getModel().getCurrent();



        if((event.getSelectedTargets().isEmpty() && !(weapon instanceof Powerglove))|| hasDuplicates(event)){
            if(event.getSelectedTargets().isEmpty())
                event.getView().reportError("You didn't select any targets, try again!");
            else
                event.getView().reportError("Wrong selection, try again!");
            switch (type){
                case BASE:
                    getModel().getCurrent().decrementBaseCounter();
                    weapon.askBaseRequirements(currentPlayer);
                    return;
                case ALTERNATIVE:
                    getModel().getCurrent().decrementAlternativeCounter();
                    ((WeaponAlternative)weapon).askAlternativeRequirements(currentPlayer);
                    return;
                case OPTIONAL1:
                    getModel().getCurrent().decrementOptionalCounter1();
                    ((WeaponOptional1)weapon).askOptionalRequirements1(currentPlayer);
                    return;
                case OPTIONAL2:
                    getModel().getCurrent().decrementOptionalCounter2();
                    ((WeaponOptional2)weapon).askOptionalRequirements2(currentPlayer);
                    return;
            }
        }
        if(event.getSelectedTargets().isEmpty() && (weapon instanceof Powerglove)){
            switch (type){
                case BASE:
                    weapon.askBaseRequirements(currentPlayer);
                    return;
                case ALTERNATIVE:
                    ((WeaponAlternative)weapon).askAlternativeRequirements(currentPlayer);
                    return;
                case OPTIONAL1:
                    ((WeaponOptional1)weapon).askOptionalRequirements1(currentPlayer);
                    return;
                case OPTIONAL2:
                    ((WeaponOptional2)weapon).askOptionalRequirements2(currentPlayer);
                    return;
            }
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

    /**
     * Controls if the selected square is valid and in that case proceeds to save it
     * for later use in the selected weapon class
     * @param event contains the index of the square selected by the player
     */
    @Override
    public void update(ChooseWeaponSquareEvent event){
        if (getModel().getPlayer(event.getPlayerColor()).isAfk()){
            return;
        }
        ArrayList<Integer> IDlist = new ArrayList<>();
        Player currentPlayer = getModel().getPlayer(event.getPlayerColor());
        int input = event.getInput();
        for(Square availableSquare : getModel().getCurrent().getAvailableWeaponSquares()){
            IDlist.add(availableSquare.getID());
        }
        if(!IDlist.contains(input)){
            event.getView().reportError("Invalid square.\n");
            getModel().chooseWeaponSquare(currentPlayer.getPlayerColor() ,getModel().getCurrent().getAvailableWeaponSquares());
        }
        else{
            Square square = getModel().getCurrent().getWeaponSquareFromID(input);
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


    /**
     * Controls if the selected powerUps actually result in a valid payment, if so the cost of the weapon fire mode
     * gets paid, otherwise the player gets asked for new inputs
     * @param event contains the list of inputs corresponding to the chosen powerUps that
     *              are used to pay for the use of the selected weapon
     */
    @Override
    public void update(WeaponPaymentEvent event){
        if (getModel().getPlayer(event.getPlayerColor()).isAfk()){
            return;
        }
        Player currentPlayer = getModel().getPlayer(event.getPlayerColor());
        Weapon weapon = getModel().getCurrent().getSelectedWeapon();
        String error;
        String effectType = weapon.getWeaponTree().getLastAction().getData().getType();

        if(hasDuplicatePayment(event.getSelectedPowerUps())){
            error = "Invalid input.\n";
            event.getView().reportError(error);
            getModel().getWeaponNotifier().askWeaponPayment(currentPlayer.getPlayerColor(),
                    getModel().getCurrent().getAvailablePaymentPowerUps());
            return;
        }

        for(int i : event.getSelectedPowerUps()){
            if(!event.getSelectedPowerUps().isEmpty()) {
                if (i < 1 || i > getModel().getCurrent().getAvailablePaymentPowerUps().size()) {
                    error = "Invalid input.\n";
                    event.getView().reportError(error);
                    getModel().getWeaponNotifier().askWeaponPayment(currentPlayer.getPlayerColor(),
                            getModel().getCurrent().getAvailablePaymentPowerUps());
                    return;
                }
            }
        }


        if(!Checks.validPayment(currentPlayer,event.getSelectedPowerUps(),effectType,getModel())) {
            error = "Invalid payment selection.\n";
            event.getView().reportError(error);
            getModel().getWeaponNotifier().askWeaponPayment(currentPlayer.getPlayerColor(),
                    getModel().getCurrent().getAvailablePaymentPowerUps());
        }
        else {
            addSelectedPaymentPowerUps(currentPlayer, event.getSelectedPowerUps());
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
                return;
            }
        }
    }


    /**
     * Support method that saves the list of chosen powerups for later use
     * @param currentPlayer current player of the game
     * @param selectedPowerUps list of indexes corresponding to the selected powerups
     */
    public void addSelectedPaymentPowerUps(Player currentPlayer, ArrayList<Integer> selectedPowerUps){
        ArrayList<PowerUp> available = getModel().getCurrent().getAvailablePaymentPowerUps();
        for(int i : selectedPowerUps){
            getModel().getCurrent().addSelectedPaymentPowerUps(available.get(i-1));
        }
    }

    /**
     * Controls if the seleced powerUps actually result in a valid payment, if so the cost of the weapon Reload
     * gets paid, otherwise the player gets asked for new inputs
     * @param event contains the list of inputs corresponding to the chosen powerUps that
     *              are used to pay for the use of the selected weapon
     */
    @Override
    public void update(ReloadPaymentEvent event){
        if (getModel().getPlayer(event.getPlayerColor()).isAfk()){
            return;
        }
        Player currentPlayer = getModel().getPlayer(event.getPlayerColor());
        Weapon weapon = getModel().getCurrent().getSelectedWeapon();
        String error;

        if(hasDuplicatePayment(event.getSelectedPowerUps())){
            error = "Invalid input.\n";
            event.getView().reportError(error);
            getModel().askReloadPayment(currentPlayer,weapon);
            return;
        }

        for(int i : event.getSelectedPowerUps()){
            if(i<1 || i>getModel().getCurrent().getAvailablePaymentPowerUps().size()){
                error = "Invalid input.\n";
                event.getView().reportError(error);
                getModel().askReloadPayment(currentPlayer,weapon);
                return;
            }
        }
        if(!Checks.validPayment(currentPlayer,event.getSelectedPowerUps(),"reload",getModel())) {
            error = "Invalid input.\n";
            event.getView().reportError(error);
            getModel().getCurrent().getAvailablePaymentPowerUps().clear();
            getModel().askReloadPayment(currentPlayer, weapon);
        }
        else{
            addSelectedPaymentPowerUps(currentPlayer, event.getSelectedPowerUps());
            getModel().payReload(currentPlayer, weapon);
        }
    }

    /**
     * Controls if the seleced powerup actually result in a valid payment, if so the cost of the weapon PickUp cost
     * gets paid, otherwise the player gets asked for new inputs
     * @param event contains the list of inputs corresponding to the chosen powerUps that
     *              are used to pay for the use of the selected weapon
     */
    @Override
    public void update(PickUpPaymentEvent event){
        if (getModel().getPlayer(event.getPlayerColor()).isAfk()){
            return;
        }
        Player currentPlayer = getModel().getPlayer(event.getPlayerColor());
        Weapon weapon = getModel().getCurrent().getSelectedPickUpWeapon();
        String error;

        if(hasDuplicatePayment(event.getSelectedPowerUps())){
            error = "Invalid input: You selected the same powerUp more than once.\n";
            event.getView().reportError(error);
            getModel().getCurrent().getAvailablePaymentPowerUps().clear();
            getModel().askPickUpPayment(currentPlayer,weapon);
            return;
        }

        for(int i : event.getSelectedPowerUps()){
            if(i<1 || i>getModel().getCurrent().getAvailablePaymentPowerUps().size()){
                error = "Invalid input.\n";
                event.getView().reportError(error);
                getModel().getCurrent().getAvailablePaymentPowerUps().clear();
                getModel().askPickUpPayment(currentPlayer,weapon);
                return;
            }
        }
        if(!Checks.validPayment(currentPlayer,event.getSelectedPowerUps(),"pickup",getModel())) {
            error = "Invalid input.\n";
            event.getView().reportError(error);
            getModel().getCurrent().getAvailablePaymentPowerUps().clear();
            getModel().askPickUpPayment(currentPlayer, weapon);
        }
        else{
            addSelectedPaymentPowerUps(currentPlayer, event.getSelectedPowerUps());
            getModel().payPickUp(currentPlayer, weapon);
        }
    }

    /**
     * Controls if the received input corresponds to a valid weapon to grab, if so
     * the weapon is grabbed, otherwise the player gets asked for a new input
     * @param event contains the index of the weapon to grab from spawn square
     */
    @Override
    public void update(ChoosePickUpWeaponEvent event){
        if (getModel().getPlayer(event.getPlayerColor()).isAfk()){
            return;
        }
        int input = event.getInput();
        if(input < 1 || input > getModel().getCurrent().getPickUpableWeapon().size()){
            String error = "Invalid input.\n";
            event.getView().reportError(error);
            getModel().showPickUpWeapons(getModel().getCurrent().getPickUpableWeapon(),event.getPlayerColor());
            return;
        }
        Weapon selectedWeapon = getModel().getCurrent().getPickUpableWeapon().get(input-1);
        getModel().getCurrent().setSelectedPickUpWeapon(selectedWeapon);
        getModel().askPickUpPayment(getModel().getPlayer(event.getPlayerColor()),selectedWeapon);
    }

    /**
     * Controls if the received input actually corresponds to a weapon the player can swap,
     * if so the weapon gets swapped, otherwise the player is asked for a new input
     * @param event contains the index of the weapon of the current player to swap
     */
    @Override
    public void update(WeaponSwapEvent event){
        if (getModel().getPlayer(event.getPlayerColor()).isAfk()){
            return;
        }
        int input = event.getInput();
        if(input < 1 || input > 3){
            String error = "Invalid input.\n";
            event.getView().reportError(error);
            getModel().getWeaponNotifier().askWeaponSwap(getModel().getPlayer(event.getPlayerColor()));
            return;
        }
        getModel().swapPickUpWeapon(getModel().getPlayer(event.getPlayerColor()),input);

    }

    /**
     * Support weapon that saves the selected targets for the base fire mode
     */
    public void checkBaseTargets(TargetsSelectionEvent event,Weapon weapon,Current current,Player currentPlayer){
        ArrayList<Player> selectedPlayers = new ArrayList<>();
        for (Integer target : event.getSelectedTargets()) {
            int temp = target;

            if(temp!=0) {
                temp--;
                if (temp < 0 || temp >= current.getAvailableBaseTargets().size()) {
                    current.getAvailableBaseTargets().clear();
                    String error;
                    error = "Invalid input.\n";
                    event.getView().reportError(error);
                    getModel().selectTargets(event.getPlayerColor(), current.getAvailableBaseTargets(),
                            weapon.getBaseTargetsNumber());
                    return;
                }
                else {
                    selectedPlayers.add(getModel().getCurrent().getAvailableBaseTargets().get(temp));

                }
            }

        }
        getModel().getCurrent().setSelectedBaseTargets(selectedPlayers);
        weapon.askBaseRequirements(currentPlayer);
    }
    /**
     * Support weapon that saves the selected targets for the alternative fire mode
     */
    public void checkAlternativeTargets(TargetsSelectionEvent event,Weapon weapon,Current current,Player currentPlayer) {
        ArrayList<Player> selectedPlayers = new ArrayList<>();
        for (Integer target : event.getSelectedTargets()) {
            int temp = target;

            if(temp!=0) {
                temp--;
                if (temp < 0 || temp >= current.getAvailableAlternativeTargets().size()) {
                    current.getAvailableAlternativeTargets().clear();
                    String error;
                    error = "Invalid input.\n";
                    event.getView().reportError(error);
                    getModel().selectTargets(event.getPlayerColor(), current.getAvailableAlternativeTargets(),
                            ((WeaponAlternative) weapon).getAlternativeTargetsNumber());
                    return;
                }
                else {
                    selectedPlayers.add(getModel().getCurrent().getAvailableAlternativeTargets().get(temp));
                }
            }
        }
        getModel().getCurrent().setSelectedAlternativeTargets(selectedPlayers);
        ((WeaponAlternative)weapon).askAlternativeRequirements(currentPlayer);
    }
    /**
     * Support weapon that saves the selected targets for the first optional fire mode
     */
    public void checkOptionalTargets1(TargetsSelectionEvent event, Weapon weapon, Current current, Player currentPlayer){
        ArrayList<Player> selectedPlayers = new ArrayList<>();

        for (Integer target : event.getSelectedTargets()) {
            int temp = target;

            if(temp!=0) {
                temp--;
                if (temp < 0 || temp >= current.getAvailableOptionalTargets1().size()) {
                    current.getAvailableOptionalTargets1().clear();
                    String error;
                    error = "Invalid input.\n";
                    event.getView().reportError(error);
                    getModel().selectTargets(event.getPlayerColor(), current.getAvailableOptionalTargets1(),
                            ((WeaponOptional1) weapon).getOptionalTargetsNumber1());
                    return;
                }
                else {
                    selectedPlayers.add(getModel().getCurrent().getAvailableOptionalTargets1().get(temp));
                }

            }
        }
        getModel().getCurrent().setSelectedOptionalTargets1(selectedPlayers);
        ((WeaponOptional1)weapon).askOptionalRequirements1(currentPlayer);

    }
    /**
     * Support weapon that saves the selected targets for the second optional fire mode
     */
    public void checkOptionalTargets2(TargetsSelectionEvent event, Weapon weapon, Current current, Player currentPlayer){
        ArrayList<Player> selectedPlayers = new ArrayList<>();
        for (Integer target : event.getSelectedTargets()) {
            int temp = target;


            if(temp!=0) {
                temp--;
                if (temp < 0 || temp >= current.getAvailableOptionalTargets2().size()) {
                    current.getAvailableOptionalTargets2().clear();
                    String error;
                    error = "Invalid input.\n";
                    event.getView().reportError(error);
                    getModel().selectTargets(event.getPlayerColor(), current.getAvailableOptionalTargets2(),
                            ((WeaponOptional2) weapon).getOptionalTargetsNumber2());
                    return;
                }
                else
                    selectedPlayers.add(getModel().getCurrent().getAvailableOptionalTargets2().get(temp));
            }
        }
        getModel().getCurrent().setSelectedOptionalTargets2(selectedPlayers);
        ((WeaponOptional2)weapon).askOptionalRequirements2(currentPlayer);
    }

    public boolean hasDuplicates(TargetsSelectionEvent event){
        if(event.getSelectedTargets().size()==1)
            return false;
        else{
            for(int i = 0; i < event.getSelectedTargets().size();i++){
                for(int j = i+1; j < event.getSelectedTargets().size();j++){
                    if(event.getSelectedTargets().get(i) == event.getSelectedTargets().get(j))
                        return true;
                }
            }
            return false;
        }
    }

    public boolean hasDuplicatePayment(ArrayList<Integer> selection){
        if(selection.size()==1)
            return false;
        else{
            for(int i = 0; i < selection.size();i++){
                for(int j = i+1; j < selection.size();j++){
                    if(selection.get(i).equals(selection.get(j)))
                        return true;
                }
            }
            return false;
        }
    }


}
