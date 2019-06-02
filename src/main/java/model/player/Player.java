package model.player;

import model.CLI;
import model.cards.powerups.PowerUp;
import model.cards.powerups.TagbackGrenade;
import model.map.Square;
import model.player.action.ActionTree;

import java.util.ArrayList;

public class Player {

    private final String playerName;
    private final PlayerColor playerColor;
    private final Resources resources;
    private final PlayerBoard playerBoard;

    private Score score;
    private Square position;
    private boolean isFirst;
    private ActionTree actionTree;
    private boolean afk = false;

    private boolean isDead;
    private boolean isKillShot;

    public Player(String name, PlayerColor color){
        playerName = name;
        playerColor = color;
        resources = new Resources();
        playerBoard = new PlayerBoard();
        score = new Score();
        this.actionTree = new ActionTree(1);
    }

    public Score getScore() {
        return score;
    }

    //todo completare
    public void drawPowerUp(ArrayList<PowerUp> powerUps){

    }

    public void addScore(int score){
        this.score.addScore(score);
    }

    public PlayerBoard getPlayerBoard(){
        return playerBoard;
    }

    public Resources getResources(){
        return resources;
    }

    public PlayerColor getPlayerColor(){
        return playerColor;
    }

    public Square getPosition(){
        return position;
    }

    public void setPosition(Square square){
        position = square;
    }

    public String getPlayerName() {
        return playerName;
    }

    public boolean isFirst(){
        return this.isFirst;
    }

    public ActionTree getActionTree(){
        return this.actionTree;
    }

    public void setActionTree(int ID){
        actionTree = new ActionTree(ID);
    }

    public boolean isDead(){
        return isDead;
    }

    public void setDead(){
        isDead = true;
    }

    public void setAlive(){
        isDead = false;
        isKillShot = false;
    }

    public void setAfk(boolean afk) {
        this.afk = afk;
    }

    public boolean isAfk() {
        return afk;
    }

    public void setKillshot(){
        isKillShot = true;
    }

    public boolean isKillShot(){
        return isKillShot;
    }

    public String getColoredName(){
        StringBuilder stringBuilder = new StringBuilder();
        String color = CLI.getColor(getPlayerColor());
        stringBuilder.append(color);
        stringBuilder.append(getPlayerName());
        stringBuilder.append(CLI.getResetString());
        return stringBuilder.toString();
    }

    public String printPlayerInfo(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getColoredName());
        stringBuilder.append("\n");
        stringBuilder.append("Position: ");
        stringBuilder.append(getPosition().getID());
        stringBuilder.append("\n");
        stringBuilder.append(getPlayerBoard().getDamageCounter().printDamageCounter());
        stringBuilder.append("\n");
        stringBuilder.append(getPlayerBoard().getMarkCounter().printMarkCounter());
        stringBuilder.append("\n");
        stringBuilder.append("Points: ");
        stringBuilder.append(getPlayerBoard().getPoints().printPoints());
        stringBuilder.append("\n");
        stringBuilder.append(getResources().showAmmo());
        stringBuilder.append("\n");
        stringBuilder.append("Unloaded weapons: ");
        stringBuilder.append(getResources().printUnloadedWeapons());
        stringBuilder.append("\n");

        return stringBuilder.toString();
    }

    public boolean hasTagBackGrenade(){
        for (PowerUp powerUp : getResources().getPowerUp()){
            if (powerUp instanceof TagbackGrenade){
                return true;
            }
        }
        return false;
    }

}
