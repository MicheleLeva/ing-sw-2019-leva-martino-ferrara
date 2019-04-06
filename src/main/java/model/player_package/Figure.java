package model.player_package;

import model.map_package.Direction;
import model.map_package.Square;

public class Figure {

    private Square position;

    public Square getPosition(){
        return position;
    }

    public void setPosition(Square position) {
        this.position = position;
    }

    public Figure(Square square){
        this.position = square;
    }
}
