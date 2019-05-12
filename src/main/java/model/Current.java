package model;

import model.cards.Weapon;
import model.map_package.Square;
import model.player_package.Player;

import java.util.ArrayList;

public class Current {
                        ///For PowerUps///
    private ArrayList<Player> opponent = new ArrayList<>();
    private ArrayList<Square> square = new ArrayList<>();
                        ///For Weapons///
    private ArrayList<Player> selectedBaseTargets = new ArrayList<>();
    private ArrayList<Player> availableBaseTargets = new ArrayList<>();
    private ArrayList<Player> selectedAlternativeTargets = new ArrayList<>();
    private ArrayList<Player> selectedOptionalTargets1 = new ArrayList<>();
    private ArrayList<Player> selectedOptionalTargets2 = new ArrayList<>();
    private Weapon selectedWeapon = null;

    public Current(){
        resetCurrent();
    }

    public ArrayList<Player> getOpponent() {
        return opponent;
    }

    public void addOpponent(Player opponent) {
        this.opponent.add(opponent);
    }

    public void setOpponent(ArrayList<Player> opponent){
        this.opponent = opponent;
    }

    public void resetCurrent(){ //resetta
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

    public ArrayList<Player> getSelectedBaseTargets() {
        return selectedBaseTargets;
    }

    public void setSelectedBaseTargets(ArrayList<Player> selectedBaseTargets) {
        this.selectedBaseTargets = selectedBaseTargets;
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

}
