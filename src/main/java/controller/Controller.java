package controller;

import model.Model;
import model.TurnManager;
import model.adrenaline_exceptions.EmptySquareException;
import model.adrenaline_exceptions.IllegalOpponentException;
import model.adrenaline_exceptions.InsufficientAmmoException;
import model.adrenaline_exceptions.WallException;
import model.events.*;
import view.View;
import utils.Observer;

public class Controller implements Observer<PlayerMove> {

    private final Model model;

    public Controller(Model model){
        this.model = model;
    }

    private synchronized void performMove(PlayerMove move){
        if (!TurnManager.isPlayerTurn(move.getView().getPlayerColor())){
            move.getView().reportError("It's not your turn");
            return;
        }

        try {
            move.performMove(this.model);
        }

        catch(WallException e){
            move.getView().reportError("You cannot enter a wall");
        }

        catch(IllegalOpponentException e){
                move.getView().reportError("You cannot shoot to the player");
            }

        catch(EmptySquareException e){
                move.getView().reportError("This square is empty");
            }

        catch(InsufficientAmmoException e){
                move.getView().reportError("Insufficient Ammo");
            }



    }
    public void update(PlayerMove move){
        performMove(move);
    }



}
