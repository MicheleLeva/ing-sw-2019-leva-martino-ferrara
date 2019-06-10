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
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import utils.observer.WeaponObserver;

import java.util.ArrayList;
import java.util.List;

public class WeaponController extends Controller implements WeaponObserver {

    private static final String BASE = "base";
    private static final String ALTERNATIVE = "alternative";
    private static final String OPTIONAL1 = "optional1";
    private static final String OPTIONAL2 = "optional2";


    public WeaponController(Model model){
        super(model);
    }
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
        /*if(selectedWeapon.getType() == "alternative")
            getModel().askAlternativeEffect(selectedWeapon);
        else
            selectedWeapon.askBaseRequirements();*/
        }

    }

    @Override
    public void update(ReloadEndTurnEvent reloadEndTurnEvent){
        if (getModel().getPlayer(reloadEndTurnEvent.getPlayerColor()).isAfk()){
            return;
        }
        char input = reloadEndTurnEvent.getInput();
        PlayerColor currentPlayerColor = reloadEndTurnEvent.getPlayerColor();

        if(input != 'Y' && input != 'N'){
            reloadEndTurnEvent.getView().reportError("Invalid input.\n");
            getModel().askReloadEndTurn(currentPlayerColor);
            return;
        }

        if (input == 'N'){
            getModel().getTurnCurrent().setFinishedReloading(true);
        }
        else{
            getModel().requestWeaponReload(currentPlayerColor);
        }
    }

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
    @Override
    public void update(OptionalFireModesEvent event){
        if (getModel().getPlayer(event.getPlayerColor()).isAfk()){
            return;
        }
        Player currentPlayer = getModel().getPlayer(event.getPlayerColor());
        Weapon weapon = getModel().getCurrent().getSelectedWeapon();
        int input = event.getInput();
        System.out.println("input in weaponcontroller  " + input);
        if (input < 1 || input > getModel().getCurrent().getAvailableFireModes().size()){
            String error;
            error = "Invalid input.\n";
            event.getView().reportError(error);
            getModel().showFireModes(event.getPlayerColor(),weapon); //chiede di reinserire input
            return;
        }

        String effectType = getModel().getCurrent().getAvailableFireModes().get(input-1).getData().getType();

        System.out.println("sjbvldfvd" + effectType);
        System.out.println("akjfbvlkdjfv" +  weapon.getWeaponTree().getLastActionPerformed().getData().getType());
        System.out.println("weapon" + weapon.getWeaponName());
        System.out.println(weapon.getWeaponTree().getLastActionPerformed().getChildren().size());

        for(int i = 0; i<weapon.getWeaponTree().getLastActionPerformed().getChildren().size(); i++){
            System.out.println(i);
            System.out.println("asdf"+weapon.getWeaponTree().getLastActionPerformed().getChildren().get(i).getData().getType());
            if(weapon.getWeaponTree().getLastActionPerformed().getChildren().get(i).getData().getType().equals(effectType)) {
                weapon.getWeaponTree().updateLastAction(i);
                break;
            }
        }

        System.out.println("updated last action in weapon controller" + weapon.getWeaponTree().getLastAction().getData().getType());

        if(effectType.equals("return")){
            weapon.getWeaponTree().resetAction();
            getModel().resetCurrent();
            getModel().chooseAction(event.getPlayerColor());
            return;
        }

        if(effectType.equals("end")){
            for(PowerUp powerUp : currentPlayer.getResources().getPowerUp()){
                System.out.println(powerUp.toString());
                if(powerUp instanceof TargetingScope){
                    getModel().getPowerUpNotifier().askTargetingScope(currentPlayer.getPlayerColor());
                    return;
                }
            }
            System.out.println("checknextweaponactionnotifyshoot");
            getModel().notifyShoot(currentPlayer);
            return;
        }
        if(effectType.equals(BASE)) {
            weapon.askBaseRequirements(currentPlayer);
            return;
        }
        if(effectType.equals(ALTERNATIVE)||effectType.equals(OPTIONAL1)||effectType.equals(OPTIONAL2)) {
            getModel().askFireModePayment(currentPlayer,weapon,effectType);
            return;
        }

    }
    @Override
    public void update(TargetsSelectionEvent event){
        if (getModel().getPlayer(event.getPlayerColor()).isAfk()){
            return;
        }
        Player currentPlayer = getModel().getPlayer(event.getPlayerColor());
        Weapon weapon = getModel().getCurrent().getSelectedWeapon();
        String type = weapon.getWeaponTree().getLastAction().getData().getType();
        Current current = getModel().getCurrent();
        System.out.println("targetsselectionevent" + type);

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
            System.out.println("invalidsquare in controller");
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


    @Override
    public void update(WeaponPaymentEvent event){
        if (getModel().getPlayer(event.getPlayerColor()).isAfk()){
            return;
        }
        Player currentPlayer = getModel().getPlayer(event.getPlayerColor());
        Weapon weapon = getModel().getCurrent().getSelectedWeapon();
        String error;
        String effectType = weapon.getWeaponTree().getLastAction().getData().getType();


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


    public void addSelectedPaymentPowerUps(Player currentPlayer, ArrayList<Integer> selectedPowerUps){
        ArrayList<PowerUp> available = getModel().getCurrent().getAvailablePaymentPowerUps();
        for(int i : selectedPowerUps){
            getModel().getCurrent().addSelectedPaymentPowerUps(available.get(i-1));
        }
    }


    @Override
    public void update(ReloadPaymentEvent event){
        if (getModel().getPlayer(event.getPlayerColor()).isAfk()){
            return;
        }
        Player currentPlayer = getModel().getPlayer(event.getPlayerColor());
        Weapon weapon = getModel().getCurrent().getSelectedWeapon();
        String error;


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
            getModel().askReloadPayment(currentPlayer, weapon);
        }
        else{
            addSelectedPaymentPowerUps(currentPlayer, event.getSelectedPowerUps());
            getModel().payReload(currentPlayer, weapon);
        }
    }
    @Override
    public void update(PickUpPaymentEvent event){
        if (getModel().getPlayer(event.getPlayerColor()).isAfk()){
            return;
        }
        Player currentPlayer = getModel().getPlayer(event.getPlayerColor());
        Weapon weapon = getModel().getCurrent().getSelectedPickUpWeapon();
        String error;

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

    public void checkAlternativeTargets(TargetsSelectionEvent event,Weapon weapon,Current current,Player currentPlayer) {
        ArrayList<Player> selectedPlayers = new ArrayList<>();
        System.out.println("checkAT " + event.getSelectedTargets());
        System.out.println("checkAT 2 " + current.getAvailableAlternativeTargets().get(0).getPlayerName());
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
        System.out.println("weaponcontroller alternativecheck" + getModel().getCurrent().getSelectedAlternativeTargets());
        System.out.println("weaponcontroller alternativecheck" + getModel().getCurrent().getSelectedAlternativeTargets().get(0).getPlayerName());
        ((WeaponAlternative)weapon).askAlternativeRequirements(currentPlayer);
    }

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

    public void checkOptionalTargets2(TargetsSelectionEvent event, Weapon weapon, Current current, Player currentPlayer){
        ArrayList<Player> selectedPlayers = new ArrayList<>();
        for (Integer target : event.getSelectedTargets()) {
            int temp = target;


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
                    selectedPlayers.add(getModel().getCurrent().getAvailableOptionalTargets2().get(temp));
            }
        }
        getModel().getCurrent().setSelectedOptionalTargets2(selectedPlayers);
        ((WeaponOptional2)weapon).askOptionalRequirements2(currentPlayer);
    }


}
