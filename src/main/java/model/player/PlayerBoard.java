package model.player;


/**
 * Representation of the player's Board
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

    public DamageCounter getDamageCounter(){
        return damageCounter;
    }

    public MarkCounter getMarkCounter(){
        return markCounter;
    }

    public Points getPoints() {
        return points;
    }

}
