package model.player_package;

import model.map_package.Square;
import model.cards.*;
import java.util.ArrayList;

public class Player {

    private final String playerName;
    private final PlayerColor playerColor;
    private final Resources resources;
    private final PlayerBoard playerBoard;
    private ActionBoard actionBoard;

    private Score score;
    private Square position;

    private boolean isFirst;

    public Player(String name, PlayerColor color){
        playerName = name;
        playerColor = color;
        resources = new Resources();
        playerBoard = new PlayerBoard();
        actionBoard = new ActionBoard();
        score = new Score();
        //position = new Square();
    }

    public String getName(){

        return playerName;
    }

    public Score getScore() {
        return score;
    }

    public void addScore(int score){
        this.score.addScore(score);
    }

   // public void setScore(Score score) {
    //    this.score = score;
    //}

    public PlayerBoard getPlayerBoard(){
        return playerBoard;
    }

    public ActionBoard getActionBoard(){
        return actionBoard;
    }

   // public Figure getFigure(){
   //     return figure;
    //}

    public Resources getResources(){
        return resources;
    }

    public PlayerColor getPlayerColor(){
        return playerColor;
    }

    public void setActionBoard(ActionBoard actionBoard) {
        this.actionBoard = actionBoard;
    }

    public Square getPosition(){
        return position;
    }

    public void setPosition(Square square){
        position = square;
    }

    public void drawAmmoCard(){

    }

    public void drawWeaponCard(){

    }

    public void drawPowerUp(ArrayList<PowerUp> drawnPowerUp){
        resources.addPowerUp(drawnPowerUp);
    }

    public String getPlayerName() {
        return playerName;
    }

}
