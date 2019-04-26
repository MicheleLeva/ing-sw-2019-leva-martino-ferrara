package controller;

import model.Model;
import model.TurnManager;
import model.adrenaline_exceptions.EmptySquareException;
import model.adrenaline_exceptions.IllegalOpponentException;
import model.adrenaline_exceptions.InsufficientAmmoException;
import model.events.playermove.*;
import model.player_package.Player;
import utils.Observer;

public class Controller implements Observer<PlayerMove> {

    private final Model model;

    public Controller(Model model){
        this.model = model;
    }

    public void update(PlayerMove move){
        //this method will never be called because of overloading
    }

   public void update(DiscardPowerUpMove move){
        Player player = model.getTurnManager().getPlayerFromColor(move.getView().getPlayerColor());
        if (move.getNum() > player.getResources().getPowerUp().size() || move.getNum() <= 0){
            move.getView().reportError("Not valid PowerUp. \nThe number must be between 1 and " +player.getResources().getPowerUp().size() +"\n");
            move.getView().discardPowerUp();
        }
        else {
            model.discardPowerUp(player , move.getNum()-1);
        }

   }

    public void showPowerUp(ShowPowerUpMove move){
        model.showPowerUp(move.getPlayerColor());
    }
    public void update(StartMove move){
        if (move.getIndex() == 1){
            model.performShowCards(move.getPlayerColor());
        }
        if (move.getIndex() == 2){

        }
        if (move.getIndex() == 3){

        }
    }

    public void update(RunMove move){
        if (!TurnManager.isPlayerTurn(move.getView().getPlayerColor())){
            move.getView().reportError("It's not your turn");
            return;
        }

        model.performRun(move.getPlayerColor(), move.getX(), move.getY());
    }

    public void update(GrabMove move){
        if (!TurnManager.isPlayerTurn(move.getView().getPlayerColor())){
            move.getView().reportError("It's not your turn");
            return;
        }

        try{
            model.performGrab(move.getPlayerColor());
        }
        catch(EmptySquareException e){
            move.getView().reportError("This square is empty");
        }
    }

    public void update(ShootMove move){
        if (!TurnManager.isPlayerTurn(move.getView().getPlayerColor())){
            move.getView().reportError("It's not your turn");
            return;
        }

        try{
            model.performShoot(move.getPlayerColor(), move.getOpponentColor(), move.getWeapon(), move.getPowerUp());
        }
        catch(IllegalOpponentException e){
            move.getView().reportError("You cannot shoot to the player");
        }
    }

    public void update(ReloadMove move){
        if (!TurnManager.isPlayerTurn(move.getView().getPlayerColor())){
            move.getView().reportError("It's not your turn");
            return;
        }

        try{
            model.performReload(move.getPlayerColor(), move.getIndex());
        }
        catch(InsufficientAmmoException e){
            move.getView().reportError("Insufficient Ammo");
        }
    }

    public void update(ShowCardsMove move){
        model.performShowCards(move.getPlayerColor());
    }

    public void update(PowerUpMove move){
        if (!TurnManager.isPlayerTurn(move.getView().getPlayerColor())){
            move.getView().reportError("It's not your turn");
            return;
        }

        model.performUsePowerUp(move.getPlayerColor(), move.getIndex());
    }

    /*public void update(DrawMove move){ //serve davvero?
        if (!TurnManager.isPlayerTurn(move.getView().getPlayerColor())){
            move.getView().reportError("It's not your turn");
            return;
        }

        model.performDraw(move.getPlayerColor());
    }*/


}
