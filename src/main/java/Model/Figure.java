package Model;

public class Figure {

    private final Color color;

    private Square location;

    public Figure(Color color){
        this.color = color;
    }

    public Color getColor(){
        return color;
    }

    public Square getLocation() {
        return location;
    }

    public void setLocation(Square location){
        this.location = location;
    }


}
