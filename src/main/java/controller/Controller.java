package controller;

import model.Model;
import model.TurnManager;
import model.adrenaline_exceptions.EmptySquareException;
import model.adrenaline_exceptions.IllegalOpponentException;
import model.adrenaline_exceptions.InsufficientAmmoException;
import model.events.playermove.TeleporterMove;
import model.events.playermove.*;
import model.player_package.Player;
import model.player_package.action.KeyMap;
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

   public void update (InputMove move){
       //Verifica input:
       //1: input = show cards -> mostra carte
       //2: input = usepowerUp -> verifica se ha powerUp, se può pagarli e se è il suo turno
       //3: input == azione -> verifica se è valida e se può farla
       char input = move.getInput();

       if(input == KeyMap.getShowCards()){
           model.showCards(move.getPlayerColor());
           return;
       }

       if (input == KeyMap.getUsePowerUp()){
           return;
       }

       if (input == KeyMap.getEnd()){

           if (model.getTurnManager().getCurrentPlayerColor() == move.getPlayerColor()) {
               Player player = model.getTurnManager().getPlayerFromColor(move.getPlayerColor());
               player.getActionTree().endAction();
           }

           else{
               move.getView().reportError("It's not your turn.\n");
           }

           return;
       }

       move.getView().reportError("Not valid move.\n");








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
            model.performShoot(move.getPlayerColor(), move.getOpponentsColor(), move.getWeapon(),move.getFireMode()/*, move.getPowerUp()*/);
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

    public void update(DrawMove move){ //serve davvero?
        if (!TurnManager.isPlayerTurn(move.getView().getPlayerColor())){
            move.getView().reportError("It's not your turn");
            return;
        }

        model.performDraw(move.getPlayerColor());
    }

    public void update(TeleporterMove move){
        if (!TurnManager.isPlayerTurn(move.getView().getPlayerColor())){
            move.getView().reportError("It's not your turn");
            return;
        }

        model.performTeleporterMove(move.getPlayerColor(),move.getRow(),move.getColumn());
    }

    public void update(ShowTargetsMove move){
        if (!TurnManager.isPlayerTurn(move.getView().getPlayerColor())){
            move.getView().reportError("It's not your turn");
            return;
        }
        model.performShowTargetsMove(move.getPlayerColor(),move.getWeaponIndex(),move.getFireModeIndex());
    }
}
