package model.player;


/**
 * Representation of the player's Board
 * @author Michele Leva, Stefano Martino, Marco Maria Ferrara
 */
public class PlayerBoard {

    private final DamageCounter  damageCounter;
    private final MarkCounter markCounter;
    private final Points points;

    /**
     * Constructor for the playerBoard class
     */
    public PlayerBoard(){
        this.damageCounter = new DamageCounter();
        this.markCounter = new MarkCounter();
        this.points = new Points();
    }

    /**
     * Returns the damage counter
     * @return the damage counter
     */
    public DamageCounter getDamageCounter(){
        return damageCounter;
    }
    /**
     * Returns the mark counter
     * @return the mark counter
     */
    public MarkCounter getMarkCounter(){
        return markCounter;
    }
    /**
     * Returns the points
     * @return the points
     */
    public Points getPoints() {
        return points;
    }

}
