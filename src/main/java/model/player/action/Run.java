package model.player.action;

import model.game.Model;
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

    /**
     * Performs the run move
     * @param model modified by the action
     * @param playerColor the color of the player who performed the action
     * @throws WallException when the player tries to move into a wall
     * @author Stefano Martino
     */
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
        //obtain the selected square
        Square newSquare = currentSquare.getSide(dir);
        //if the selected square is a wall, throw an exception
        if (newSquare == null){
            throw new WallException();
        }
        else{
            //set the new player's position
            currentPlayer.setPosition(newSquare);
            //notify all the players
            model.updateRun();
            //action ended
            model.updateAction();
        }
    }
}
