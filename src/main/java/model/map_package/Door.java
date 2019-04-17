package model.map_package;




public class Door implements MapElement {
    private Square nextSquare;

    public Door(Square square){
        this.nextSquare = square;

    }
    public Square enter(){
        return(this.nextSquare);

    }

    @Override
    public String toString() {
        return "door";
    }
}
