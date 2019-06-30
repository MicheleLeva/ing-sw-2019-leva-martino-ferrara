package model;

import model.cards.powerups.Newton;
import model.cards.powerups.PowerUp;
import model.cards.powerups.TargetingScope;
import model.cards.weapons.FireMode;
import model.cards.weapons.Weapon;
import model.cards.weapons.WeaponTreeNode;
import model.map.Square;
import model.player.Player;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Support class for the turn.
 * Contains attributes used to save previous choices of a player for the creation of an action.
 */
public class Current {
    ///For PowerUps///
    private ArrayList<Player> opponent = new ArrayList<>();
    private ArrayList<Square> square = new ArrayList<>();
    private Newton selectedNewton;
    private TargetingScope lastTargetingScope;
    private ArrayList<TargetingScope> allTargetingScopes = new ArrayList<>();

    ///For Weapons///
    private int baseCounter = 0;
    private int optionalCounter1 = 0;
    private int optionalCounter2 = 0;
    private int alternativeCounter = 0;
    private ArrayList<Square> availableWeaponSquares =  new ArrayList<>();
    private Square selectedWeaponSquare = null;
    private Weapon selectedPickUpWeapon = null;
    private ArrayList<Player> selectedBaseTargets = new ArrayList<>();
    private ArrayList<Player> availableBaseTargets = new ArrayList<>();
    private ArrayList<Player> selectedAlternativeTargets = new ArrayList<>();
    private ArrayList<Player> availableAlternativeTargets = new ArrayList<>();
    private ArrayList<Player> selectedOptionalTargets1 = new ArrayList<>();
    private ArrayList<Player> availableOptionalTargets1 = new ArrayList<>();
    private ArrayList<Player> selectedOptionalTargets2 = new ArrayList<>();
    private ArrayList<Player> availableOptionalTargets2 = new ArrayList<>();
    private ArrayList<PowerUp> availablePaymentPowerUps = new ArrayList<>();
    private ArrayList<PowerUp> selectedPaymentPowerUps = new ArrayList<>();
    private Player flamethrowerSupportPlayer = null;
    private ArrayList<Player> allDamagedPlayers = new ArrayList<>();
    private ArrayList<Player> allShotPlayers = new ArrayList<>();



    public Player getFlamethrowerSupportPlayer(){
        return this.flamethrowerSupportPlayer;
    }

    public void setFlamethrowerSupportPlayer(Player player){
        flamethrowerSupportPlayer = player;
    }

    public void decreaseBaseCounter(){this.baseCounter--;}

    public ArrayList<Player> getAllDamagedPlayer() {
        /*Set<Player> set = new LinkedHashSet<Player>(getSelectedBaseTargets());
        set.addAll(getSelectedAlternativeTargets());
        set.addAll(getSelectedOptionalTargets1());
        set.addAll(getSelectedOptionalTargets2());
        ArrayList<Player> targets = new ArrayList<>(set);*/
        return allDamagedPlayers;
    }

    public void addDamagedPlayer(Player player){
        this.allDamagedPlayers.add(player);
    }

    public ArrayList<Player> getAllShotPlayer() {
        /*Set<Player> set = new LinkedHashSet<Player>(getSelectedBaseTargets());
        set.addAll(getSelectedAlternativeTargets());
        set.addAll(getSelectedOptionalTargets1());
        set.addAll(getSelectedOptionalTargets2());
        ArrayList<Player> targets = new ArrayList<>(set);*/
        return this.allShotPlayers;
    }

    public void addShotPlayer(Player player){
        this.allShotPlayers.add(player);
    }

    private Weapon selectedWeapon = null;

    public List<WeaponTreeNode<FireMode>> getAvailableFireModes() {
        return availableFireModes;
    }

    public void setAvailableFireModes(List<WeaponTreeNode<FireMode>> availableFireModes) {
        this.availableFireModes = availableFireModes;
    }

    private List<WeaponTreeNode<FireMode>> availableFireModes = new ArrayList<>();

    private ArrayList<Weapon> reloadableWeapon = new ArrayList<>();
    private ArrayList<Weapon> pickUpableWeapon = new ArrayList<>();

    public ArrayList<Player> getOpponent() {
        return opponent;
    }

    public void addOpponent(Player opponent) {
        this.opponent.add(opponent);
    }

    public void setOpponent(ArrayList<Player> opponent){
        this.opponent = opponent;
    }

    public ArrayList<Square> getSquare(){
        return square;
    }

    public void setSquare(ArrayList<Square> square) {
        this.square = square;
    }

    public Square getSquareFromID(int ID){
        for (int i = 0; i < square.size(); i++){
            if(square.get(i).getID() == ID){
                return square.get(i);
            }
        }
        return null;
    }

    public Square getWeaponSquareFromID(int ID){
        for (int i = 0; i < availableWeaponSquares.size(); i++){
            if(availableWeaponSquares.get(i).getID() == ID){
                return availableWeaponSquares.get(i);
            }
        }
        return null;
    }

    public ArrayList<Player> getSelectedBaseTargets() {
        return selectedBaseTargets;
    }

    public void setSelectedBaseTargets(ArrayList<Player> selectedBaseTargets) {
        this.selectedBaseTargets = selectedBaseTargets;
    }
    public void setSelectedPickUpWeapon(Weapon selectedPickUpWeapon){
        this.selectedPickUpWeapon = selectedPickUpWeapon;
    }
    public ArrayList<Player> getAvailableBaseTargets() {
        return availableBaseTargets;
    }

    public void setAvailableBaseTargets(ArrayList<Player> availableBaseTargets) {
        this.availableBaseTargets = availableBaseTargets;
    }

    public ArrayList<Player> getSelectedAlternativeTargets() {
        return selectedAlternativeTargets;
    }
    public Weapon getSelectedPickUpWeapon(){return selectedPickUpWeapon;}
    public void setSelectedAlternativeTargets(ArrayList<Player> selectedAlternativeTargets) {
        this.selectedAlternativeTargets = selectedAlternativeTargets;
    }

    public ArrayList<Player> getSelectedOptionalTargets1() {
        return selectedOptionalTargets1;
    }

    public void setSelectedOptionalTargets1(ArrayList<Player> selectedOptionalTargets1) {
        this.selectedOptionalTargets1 = selectedOptionalTargets1;
    }

    public ArrayList<Player> getSelectedOptionalTargets2() {
        return selectedOptionalTargets2;
    }

    public void setSelectedOptionalTargets2(ArrayList<Player> selectedOptionalTargets2) {
        this.selectedOptionalTargets2 = selectedOptionalTargets2;
    }

    public Weapon getSelectedWeapon() {
        return selectedWeapon;
    }

    public void setSelectedWeapon(Weapon selectedWeapon) {
        this.selectedWeapon = selectedWeapon;
    }

    public ArrayList<Weapon> getReloadableWeapon(){
        return reloadableWeapon;
    }
    public ArrayList<Weapon> getPickUpableWeapon(){return pickUpableWeapon;}
    public void setReloadableWeapon(ArrayList<Weapon> reloadableWeapon){
        this.reloadableWeapon = reloadableWeapon;
    }
    public void setPickUpableWeapon(ArrayList<Weapon> pickUpableWeapon){this.pickUpableWeapon = pickUpableWeapon;}
    public ArrayList<Player> getAvailableAlternativeTargets() {
        return availableAlternativeTargets;
    }

    public void setAvailableAlternativeTargets(ArrayList<Player> availableAlternativeTargets) {
        this.availableAlternativeTargets = availableAlternativeTargets;
    }

    public ArrayList<Player> getAvailableOptionalTargets1() {
        return availableOptionalTargets1;
    }

    public void setAvailableOptionalTargets1(ArrayList<Player> availableOptionaleTargets1) {
        this.availableOptionalTargets1 = availableOptionaleTargets1;
    }

    public ArrayList<Player> getAvailableOptionalTargets2() {
        return availableOptionalTargets2;
    }

    public void setAvailableOptionalTargets2(ArrayList<Player> availableOptionalTargets2) {
        this.availableOptionalTargets2 = availableOptionalTargets2;
    }

    public ArrayList<Square> getAvailableWeaponSquares() {
        return availableWeaponSquares;
    }

    public void setAvailableWeaponSquares(ArrayList<Square> optionalAvailableSquares) {
        this.availableWeaponSquares = optionalAvailableSquares;
    }

    public void setSelectedWeaponSquare(Square square){
        this.selectedWeaponSquare=square;
    }

    public Square getSelectedWeaponSquare(){
        return selectedWeaponSquare;
    }

    public int getBaseCounter() {
        return baseCounter;
    }

    public int getOptionalCounter1() {
        return optionalCounter1;
    }

    public int getOptionalCounter2() {
        return optionalCounter2;
    }

    public int getAlternativeCounter() {
        return alternativeCounter;
    }

    public void incrementBaseCounter(){
        this.baseCounter++;
    }

    public void incrementOptionalCounter1(){
        this.optionalCounter1++;
    }

    public void incrementOptionalCounter2(){
        this.optionalCounter2++;
    }

    public void incrementAlternativeCounter(){
        this.alternativeCounter++;
    }

    public void decrementBaseCounter(){
        this.baseCounter--;
    }

    public void decrementOptionalCounter1(){
        this.optionalCounter1--;
    }

    public void decrementOptionalCounter2(){
        this.optionalCounter2--;
    }
    public void decrementAlternativeCounter(){
        this.alternativeCounter--;
    }

    public ArrayList<PowerUp> getAvailablePaymentPowerUps() { return availablePaymentPowerUps; }

    public void addAvailablePaymentPowerUps(PowerUp availablePaymentPowerUp) {
        this.availablePaymentPowerUps.add(availablePaymentPowerUp); }

    public ArrayList<PowerUp> getSelectedPaymentPowerUps() { return selectedPaymentPowerUps; }

    public void addSelectedPaymentPowerUps(PowerUp selectedPaymentPowerUp) {
        this.selectedPaymentPowerUps.add(selectedPaymentPowerUp);
    }

    public void setSelectedNewton(Newton newton){
        this.selectedNewton = newton;
    }

    public Newton getSelectedNewton(){
        return this.selectedNewton;
    }

    public void setLastTargetingScope(TargetingScope targetingScope){
        this.lastTargetingScope = targetingScope;
    }

    public TargetingScope getLastTargetingScope(){
        return this.lastTargetingScope;
    }

    public ArrayList<TargetingScope> getAllTargetingScopes() {
        return this.allTargetingScopes;
    }

    public void addAllTargetingScopes(TargetingScope targetingScope) {
        this.allTargetingScopes.add(targetingScope);
    }
}

