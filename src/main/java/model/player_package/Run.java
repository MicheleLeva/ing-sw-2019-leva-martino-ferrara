package model.player_package;

import model.Model;
import model.adrenaline_exceptions.WallException;
import model.map_package.Direction;
import model.map_package.MapElement;
import model.map_package.Square;
import model.player_package.action.KeyMap;

public class Run extends Action {

    private int steps;
    private char direction;

    public Run(char direction){
        this.direction = direction;
    }

    @Override
    public void showAction(){

    }

    public Run(){
        steps = 3;
    }

    public int getSteps(){
        return steps;
    }

    public void setSteps(int steps){
        this.steps = steps;
    }

    public void decreaseSteps(){
        this.steps = this.steps - 1;
    }

    public char getDirection(){
        return direction;
    }

    public void perform(Model model , PlayerColor playerColor) throws WallException{
        Player currentPlayer = model.getPlayer(playerColor);
        Square currentSquare = currentPlayer.getPosition();
        MapElement mapElement;
        Direction dir;

        if(KeyMap.isRunUp(direction)){
            dir = Direction.NORTH;
        }

        else if (KeyMap.isRunLeft(direction)){
            dir = Direction.EAST;
        }

        else if(KeyMap.isRunDown(direction)){
            dir = Direction.SOUTH;
        }

        else{
            dir = Direction.WEST;
        }

        MapElement newSquare = currentSquare.getSide(dir).enter();

        if (newSquare == null) throw new WallException();

        currentPlayer.setPosition((Square)newSquare);
        //Stampa messaggi
        String toPlayer = "You moved to " +newSquare.toString();
        String toOthers = currentPlayer.getName() +" moved to " +newSquare.toString();
        model.printMessage(playerColor , toPlayer , toOthers);


    }
}
