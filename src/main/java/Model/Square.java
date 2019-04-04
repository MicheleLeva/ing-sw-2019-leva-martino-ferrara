package Model;

public class Square implements MapElement{
    private Color squareColor;
    private int squareIndexRow;
    private int squareIndexColumn;

    public Color getSquareColor(){
        return squareColor;

    };
    public void setSquareColor(Color col){

    }

    public MapElement getSide(Direction dir){
        return null;
    }

    public void setSide(Direction dir, MapElement elem){

    }

    public int getSquareRow(){
        return 1;
    }

    public void setSquareRow(int row){

    }

    public int getSquareColumn(){
        return 1;
    }

    public void setSquareColumn(int col){

    }

    public void enter(){

    }
}
