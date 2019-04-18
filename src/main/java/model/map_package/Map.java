package model.map_package;



import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import static java.lang.String.valueOf;

public class Map {

    private Square[][] map;

    /*public static void main(String[] args) throws IOException, ParseException {


        System.out.println("inserisci intero\n");
        Scanner scannerIO = new Scanner(System.in);
        int input = scannerIO.nextInt();
        new Map(input);
        return;
    }
*/
    public Map(int playersNumber) {
        String nome;
        if(playersNumber==3) nome = "src/resources/map1.json";
        else if(playersNumber==4) nome = "src/resources/map2.json";
        else if(playersNumber==5) nome = "src/resources/map3.json";
        else if(playersNumber==6) nome = "src/resources/map4.json";
        else nome = "/src/resources/map2.json";

        map = new Square[3][4];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                map[i][j] = new Square();
            }
        }
        int k=0;
        JSONParser parser = new JSONParser();

        try {

            Object obj = parser.parse(new FileReader(nome));
            JSONObject myJo = (JSONObject) obj;
            //System.out.println(myJo.get("map"));
            JSONArray myArray = (JSONArray) myJo.get("map");
            //System.out.println(myArray.get(4));
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 4; j++) {

                    JSONObject result1 = (JSONObject) myArray.get(k);
                    //System.out.println(result1+"/n");
                    if (result1.get("NORTH").equals("wall")) map[i][j].setSide(Direction.NORTH, new Wall());
                    if (result1.get("NORTH").equals("door")) map[i][j].setSide(Direction.NORTH, new Door(map[i - 1][j]));
                    if (result1.get("NORTH").equals("square")) map[i][j].setSide(Direction.NORTH, map[i - 1][j]);

                    if (result1.get("SOUTH").equals("wall")) map[i][j].setSide(Direction.SOUTH, new Wall());
                    if (result1.get("SOUTH").equals("door")) map[i][j].setSide(Direction.SOUTH, new Door(map[i + 1][j]));
                    //if(map[0][0].getSide(Direction.SOUTH) instanceof Door)System.out.println("door");
                    if (result1.get("SOUTH").equals("square")) map[i][j].setSide(Direction.SOUTH, map[i + 1][j]);

                    if (result1.get("WEST").equals("wall")) map[i][j].setSide(Direction.WEST, new Wall());
                    if (result1.get("WEST").equals("door")) map[i][j].setSide(Direction.WEST, new Door(map[i][j - 1]));
                    if (result1.get("WEST").equals("square")) map[i][j].setSide(Direction.WEST, map[i][j - 1]);

                    if (result1.get("EAST").equals("wall")) map[i][j].setSide(Direction.EAST, new Wall());
                    if (result1.get("EAST").equals("door")) map[i][j].setSide(Direction.EAST, new Door(map[i][j + 1]));
                    if (result1.get("EAST").equals("square")) map[i][j].setSide(Direction.EAST, map[i][j + 1]);

                    if(result1.get("color").equals("null"));
                    else
                        map[i][j].setColor(SquareColor.valueOf((String)result1.get("color")));
                    map[i][j].isSpawn = (Boolean) result1.get("isSpawn");
                    //if(map[i][j].getSide(Direction.EAST) instanceof Square)System.out.println("door"+i+j);
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
        return map[x][y].enter();
    }
}

