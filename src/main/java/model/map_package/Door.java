package model.map_package;

import model.player_package.Figure;

public class Door implements MapElement {
    private Square square1;
    private Square square2;

    public Door(Square square1, Square square2){
        this.square1 = square1;
        this.square2 = square2;
    }
    public Square enter(Figure figure){
        if(figure.getPosition() == square1) return(square2);
        else return(square1);

    }
}
