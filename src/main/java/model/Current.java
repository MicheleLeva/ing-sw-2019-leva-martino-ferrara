package model;

import model.map_package.Square;
import model.player_package.Player;

import java.util.ArrayList;

public class Current {
    private ArrayList<Player> opponent = new ArrayList<>();
    private ArrayList<Square> square = new ArrayList<>();

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
}
