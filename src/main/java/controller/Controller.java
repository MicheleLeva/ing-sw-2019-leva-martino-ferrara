package controller;

import model.Model;
import model.TurnManager;
import model.adrenaline_exceptions.EmptySquareException;
import model.adrenaline_exceptions.IllegalOpponentException;
import model.adrenaline_exceptions.InsufficientAmmoException;
import model.events.*;
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

        if (move instanceof RunMove){
            model.performRun(move.getPlayerColor(), ((RunMove) move).getX(), ((RunMove) move).getY());
        }

        if (move instanceof GrabMove){
            try{
                model.performGrab(move.getPlayerColor());
            }
            catch(EmptySquareException e){
                move.getView().reportError("This square is empty");
            }
        }

        if (move instanceof ShootMove){
            try{
                model.performShoot(move.getPlayerColor(), ((ShootMove) move).getOpponentColor(), ((ShootMove) move).getWeapon(),((ShootMove) move).getPowerUp());
            }
            catch(IllegalOpponentException e){
                move.getView().reportError("You cannot shoot to the player");
            }
        }

        if (move instanceof ReloadMove){
            try{
                model.performReload(move.getPlayerColor(), ((ReloadMove) move).getIndex());
            }
            catch(InsufficientAmmoException e){
                move.getView().reportError("Insufficient Ammo");
            }
        }

        if (move instanceof  ShowCards){
            model.performShowCards(move.getPlayerColor());
        }

        if (move instanceof UsePowerUp){
            model.performUsePowerUp(move.getPlayerColor(), ((UsePowerUp) move).getIndex());
        }

        if (move instanceof  Draw){
            model.performDraw(move.getPlayerColor());
        }

        //more possible moves?
    }


    public void update(PlayerMove move){
        performMove(move);
    }





}
