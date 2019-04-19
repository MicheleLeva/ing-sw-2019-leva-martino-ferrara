package model.player_package;

public class PlayerBoard {

    private final DamageCounter  damageCounter;
    private final MarkCounter markCounter;
    private final Points points;

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

  /*  public void addMarks(PlayerColor color, int marks){
        markCounter.addMarks(color, marks);
    }

    public void addDamage(PlayerColor color){
        damageCounter.addDamage(color);

    }

    public void removeHighestPoint(){
        points.removeHighestPoint();
   }*/
}
