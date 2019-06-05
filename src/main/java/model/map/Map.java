package model.map;



import model.Decks;
import model.CLI;
import model.cards.AmmoColor;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class
Map {

    private Square[][] map;
    private String mapCLI;

    public Map(int chosenMap) {
        String JSONpath;
        String CLIMapPath;
        if(chosenMap==1) {
            JSONpath = "src/resources/map1.json";
            CLIMapPath = "src/resources/map1.txt";
        }
        else if(chosenMap==2) {
            JSONpath = "src/resources/map2.json";
            CLIMapPath = "src/resources/map2.txt";
        }
        else if(chosenMap==3) {
            JSONpath = "src/resources/map3.json";
            CLIMapPath = "src/resources/map3.txt";
        }
        else if(chosenMap==4) {
            JSONpath = "src/resources/map4.json";
            CLIMapPath = "src/resources/map4.txt";
        }
        else {
            JSONpath = "/src/resources/map2.json";
            CLIMapPath = "src/resources/map2.txt";
        }

        map = new Square[3][4];
        for(int i = 0; i < 3; i++){
            for(int j = 0 ; j < 4; j++){
                map[i][j] = new Square(i,j);
            }
        }
        int ID = 0;

        int k=0;
        JSONParser parser = new JSONParser();

        try {
            mapCLI = CLI.buildCLIMap(CLIMapPath);
            Object obj = parser.parse(new FileReader(JSONpath));
            JSONObject myJo = (JSONObject) obj;
            //System.out.println(myJo.get("map"));
            JSONArray myArray = (JSONArray) myJo.get("map");
            //System.out.println(myArray.get(4));
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 4; j++) {

                    JSONObject result1 = (JSONObject) myArray.get(k);
                    if(result1.get("color").equals("null")){
                        map[i][j] = null;
                        k++;
                        ID++;
                        continue;
                    }

                    map[i][j].setID(ID);
                    ID++;
                    //System.out.println(result1+"/n");
                    if (result1.get("NORTH").equals("wall")) map[i][j].setSide(Direction.NORTH, null);
                    if (result1.get("NORTH").equals("door")) map[i][j].setSide(Direction.NORTH, map[i-1][j]);
                    if (result1.get("NORTH").equals("square")) map[i][j].setSide(Direction.NORTH, map[i - 1][j]);

                    if (result1.get("SOUTH").equals("wall")) map[i][j].setSide(Direction.SOUTH, null);
                    if (result1.get("SOUTH").equals("door")) map[i][j].setSide(Direction.SOUTH, map[i + 1][j]);
                    if (result1.get("SOUTH").equals("square")) map[i][j].setSide(Direction.SOUTH, map[i + 1][j]);

                    if (result1.get("WEST").equals("wall")) map[i][j].setSide(Direction.WEST, null);
                    if (result1.get("WEST").equals("door")) map[i][j].setSide(Direction.WEST, map[i][j - 1]);
                    if (result1.get("WEST").equals("square")) map[i][j].setSide(Direction.WEST, map[i][j - 1]);

                    if (result1.get("EAST").equals("wall")) map[i][j].setSide(Direction.EAST, null);
                    if (result1.get("EAST").equals("door")) map[i][j].setSide(Direction.EAST, map[i][j + 1]);
                    if (result1.get("EAST").equals("square")) map[i][j].setSide(Direction.EAST, map[i][j + 1]);

                    if(result1.get("color").equals("null"));
                    else
                        map[i][j].setColor(SquareColor.valueOf((String)result1.get("color")));
                    map[i][j].isSpawn = (Boolean) result1.get("isSpawn");
                    k++;

                }
            }
        }
        catch(FileNotFoundException e) {e.printStackTrace();}
        catch(IOException e) {e.printStackTrace();}
        catch(Exception e) {e.printStackTrace();}

    }

    public Square[][] getMap(){
        return map;
    }

    public Square getSquareFromCoordinates(int x, int y){
        return map[x][y];
    }

    public Square getSpawnSquare(SquareColor color){
        Square square = null;
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 4; j++){
                if( map[i][j]!=null && map[i][j].isSpawn && map[i][j].getColor().equals(color) )
                    square = map[i][j];
            }
        }
        return square;
    }

    public ArrayList<Square> getAllSpawnSquares(){
        ArrayList<Square> squares = new ArrayList<>();
        for(int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if( map[i][j]!=null && map[i][j].isSpawn)
                    squares.add(map[i][j]);
            }
        }
        return squares;
    }

    public String printMap(){
        return mapCLI;
    }

    public ArrayList<Integer> getAllIDs(){
        ArrayList<Integer> allIDs = new ArrayList<>();
        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 4; j++)
                if(map[i][j] != null)
                    allIDs.add(map[i][j].getID());
    return allIDs;
    }

    public Square getSquareFromID(int ID){
        Square square = null;
        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 4; j++)
                if(map[i][j] != null && map[i][j].getID() == ID) {
                    square = map[i][j];
                }
        return square;
    }

}

