package model.player;

import model.CLI;
import model.cards.powerups.PowerUp;
import model.cards.powerups.TagbackGrenade;
import model.map.Square;
import model.player.action.ActionTree;

/**
 * Representation of an in game player
 */
public class Player {

    private final String playerName;
    private final PlayerColor playerColor;
    private final Resources resources;
    private final PlayerBoard playerBoard;

    private Score score;
    private Square position;
    private ActionTree actionTree;
    private boolean afk = false;
    private boolean vote = false;

    private boolean isDead;
    private boolean isKillShot;

    /**
     * Constructor for the Player class
     * @param name name of the player
     * @param color color of the player
     */
    public Player(String name, PlayerColor color){
        playerName = name;
        playerColor = color;
        resources = new Resources();
        playerBoard = new PlayerBoard();
        score = new Score();
        this.actionTree = new ActionTree(1);
        this.position = null;
    }

    public Score getScore() {
        return score;
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

    public ActionTree getActionTree(){
        return this.actionTree;
    }

    public void setActionTree(int ID){
        actionTree = new ActionTree(ID);
    }

    public void setVote(boolean vote) {
        this.vote = vote;
    }

    public boolean hasVoted(){
        return vote;
    }

    /**
     * Checks if th player is still alive
     * @return true if the player has been killed, false otherwise
     */
    public boolean isDead(){
        return isDead;
    }

    public void setDead(){
        isDead = true;
        position = null;
    }

    public void setAlive(){
        isDead = false;
        isKillShot = false;
        playerBoard.getDamageCounter().clearDamage();
    }

    public void setAfk(boolean afk) {
        this.afk = afk;
    }

    public boolean isAfk() {
        return afk;
    }

    public void setKillshot(boolean killshot){
        position = null;
        isKillShot = killshot;
    }

    public boolean isKillShot(){
        return isKillShot;
    }

    /**
     * Returns the name of the player colored with the player's color
     */
    public String getColoredName(){
        StringBuilder stringBuilder = new StringBuilder();
        String color = CLI.getColor(getPlayerColor());
        stringBuilder.append(color);
        stringBuilder.append(getPlayerName());
        stringBuilder.append(CLI.getResetString());
        return stringBuilder.toString();
    }

    /**
     *Returns the Position, the damaged received, the marks received, the available points ad all the unloaded
     * weapons of the player
     */
    public String printPlayerInfo(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getColoredName());
        stringBuilder.append(" | ");
        stringBuilder.append("Position: ");
        if (getPosition() == null){
            stringBuilder.append("Not spawned yet.");
        } else {
            stringBuilder.append(getPosition().getID());
        }
        stringBuilder.append(" | ");
        stringBuilder.append("Damage: ");
        stringBuilder.append(getPlayerBoard().getDamageCounter().printDamageCounter());
        stringBuilder.append(" | ");
        stringBuilder.append("Marks: ");
        stringBuilder.append(getPlayerBoard().getMarkCounter().printMarkCounter());
        stringBuilder.append(" | ");
        stringBuilder.append("Points: ");
        stringBuilder.append(getPlayerBoard().getPoints().printPoints());
        stringBuilder.append(" | ");
        stringBuilder.append(getResources().showAmmo());
        stringBuilder.append(" | ");
        stringBuilder.append("Unloaded weapons: ");
        stringBuilder.append(getResources().printUnloadedWeapons());
        stringBuilder.append(" | ");

        return stringBuilder.toString();
    }

    /**
     * Checks if the player has the TagBackGrenade PowerUp
     * @return true if the player has the TagBackGrenade, false otherwise
     */
    public boolean hasTagBackGrenade(){
        for (PowerUp powerUp : getResources().getPowerUp()){
            if (powerUp instanceof TagbackGrenade){
                return true;
            }
        }
        return false;
    }

}
