package model.player.action;

import model.Model;
import model.adrenaline_exceptions.WallException;
import model.map.Direction;
import model.map.Square;
import model.player.Player;
import model.player.PlayerColor;

public class Run extends Action {

    private char direction;

    public Run(char direction){
        this.direction = direction;
    }

    public void perform(Model model , PlayerColor playerColor) throws WallException{
        Player currentPlayer = model.getPlayer(playerColor);
        Square currentSquare = currentPlayer.getPosition();

        Direction dir;

        if(KeyMap.isRunUp(direction)){
            dir = Direction.NORTH;
        }

        else if (KeyMap.isRunLeft(direction)){
            dir = Direction.WEST;
        }

        else if(KeyMap.isRunDown(direction)){
            dir = Direction.SOUTH;
        }

        else{
            dir = Direction.EAST;
        }

        Square newSquare = currentSquare.getSide(dir);

        if (newSquare == null){
            throw new WallException();
        }
        else{
            currentPlayer.setPosition(newSquare);
            model.updateRun();
            model.updateAction();
            model.updateTurn(); //todo probabilmente implementato dal turn manager
        }
    }
}
