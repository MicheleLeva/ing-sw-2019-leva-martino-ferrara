package model.player_package;

public class PlayerBoard {

    private final DamageCounter  damageCounter;
    private final MarkCounter markCounter;
    private final Points points;

    public PlayerBoard(DamageCounter damageCounter, MarkCounter markCounter, Points points ){
        this.damageCounter = damageCounter;
        this.markCounter = markCounter;
        this.points = points;
    }

    public DamageCounter getDamageCounter(){
        return damageCounter;
    }

    public MarkCounter getMarkCounter(){
        return markCounter;
    }
}
