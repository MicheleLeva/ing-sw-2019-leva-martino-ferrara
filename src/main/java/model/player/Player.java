package model.player;

import model.game.CLI;
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

    private int scoreFromKillShotTrack;

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
        this.scoreFromKillShotTrack = 0;
    }

    /**
     * Returns the player's score
     * @return the player's score
     */
    public Score getScore() {
        return score;
    }

    /**
     * Adds score
     * @param score to add
     */
    public void addScore(int score){
        this.score.addScore(score);
    }
    /**
     * Returns the playerboard
     * @return the playerboard
     */
    public PlayerBoard getPlayerBoard(){
        return playerBoard;
    }
    /**
     * Returns player's resources
     * @return player's resources
     */
    public Resources getResources(){
        return resources;
    }
    /**
     * Returns player's color
     * @return player's color
     */
    public PlayerColor getPlayerColor(){
        return playerColor;
    }
    /**
     * Returns player's position
     * @return player's position
     */
    public Square getPosition(){
        return position;
    }

    /**
     * Sets player's position
     * @param square player's position
     */
    public void setPosition(Square square){
        position = square;
    }
    /**
     * Returns player's name
     * @return player's name
     */
    public String getPlayerName() {
        return playerName;
    }
    /**
     * Returns player's action tree
     * @return player0's action tree
     */
    public ActionTree getActionTree(){
        return this.actionTree;
    }

    /**
     * Stes player's action tree
     * @param ID action's tree ID
     */
    public void setActionTree(int ID){
        actionTree = new ActionTree(ID);
    }

    /**
     * Sets the player's vote
     * @param vote chosen map
     */
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

    /**
     * Sets the player dead
     */
    public void setDead(){
        isDead = true;
        position = null;
    }

    /**
     * Sets the player alive
     */
    public void setAlive(){
        isDead = false;
        isKillShot = false;
        playerBoard.getDamageCounter().clearDamage();
    }

    /**
     * Sets the player afk
     * @param afk boolean
     */
    public void setAfk(boolean afk) {
        this.afk = afk;
    }

    /**
     * Checks whether the player is or is not afk
     * @return true if the player is afk, false otherwise
     */
    public boolean isAfk() {
        return afk;
    }

    /**
     * Sets the player killshot
     * @param killshot boolean value
     */
    public void setKillshot(boolean killshot){
        position = null;
        isKillShot = killshot;
    }

    /**
     * Checks whether or not the player is killshot
     * @return true if the player is killshot, false otherwise
     */
    public boolean isKillShot(){
        return isKillShot;
    }

    /**
     * Returns the name of the player colored with the player's color
     * @return the name of the player colored with the player's color
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
     *Returns the position, the damage received, the marks received, the available points and all the unloaded
     * weapons of the player
     * @return the position, the damage received, the marks received, the available points and all the unloaded
     *         weapons of the player
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

    /**
     * Returns the number of killshottrack score
     * @return the number of killshottrack score
     */
    public int getScoreFromKillShotTrack(){
        return scoreFromKillShotTrack;
    }

    /**
     * Sets the score gained from the killshottrack
     * @param scoreFromKillShotTrack the score gained
     */
    public void setScoreFromKillShotTrack(int scoreFromKillShotTrack){
        this.scoreFromKillShotTrack = scoreFromKillShotTrack;
    }
}
