package Model;

public class Ammo {

    private int red;
    private int blue;
    private int yellow;

    public void setRed(int red) {
        this.red = red;
    }

    public int getRed() {
        return red;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    public int getBlue() {
        return blue;
    }

    public void setYellow(int yellow) {
        this.yellow = yellow;
    }

    public int getYellow() {
        return yellow;
    }

    public Ammo(int red , int blue , int yello){
        setRed(red);
        setBlue(blue);
        setYellow(yellow);
    }

    public Ammo(){
        this(0,0,0);
    }
}
