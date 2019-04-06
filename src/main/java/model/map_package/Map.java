package model.map_package;

public class Map {

    private Square[][] map;

    public Map(Square[][] rightSide, Square[][] leftSide){
        mergeTableSide(rightSide, leftSide);

    }
    //not yet ready
    public void mergeTableSide(Square[][] rightSide, Square[][] leftSide){
        map = new Square[rightSide.length + leftSide.length][rightSide[0].length];
        System.arraycopy(leftSide, 0, map, 0, leftSide.length);

        System.arraycopy(rightSide, 0, map, leftSide.length, rightSide.length);

        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[0].length; j++){
                if (map[i][j].getSide(Direction.EAST) == null){
                    if(map[i+1][j].getSide(Direction.WEST) == null){
                        map[i][j].setSide(Direction.EAST, map[i+1][j]);
                        map[i][j].setSide(Direction.WEST, map[i][j]);
                    }
                    else{
                        map[i][j].setSide(Direction.EAST, new Door(map[i][j],map[i][j+1]));
                    }
                }
            }
       }
    }



    public Square[][] getMap(){
        return map;
    }
}
