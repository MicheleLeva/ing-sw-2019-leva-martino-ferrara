package model.map_package;

import model.map_package.Direction;
import model.map_package.Map;
import model.map_package.Square;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.FileReader;
import java.io.IOException;

public class TestMap {
    private Map map;

    @Before
    public void initMap() throws IOException, ParseException {
        map = new Map(3);
    }
   @Test
    public void testMapLayout() throws IOException, ParseException {

       String nome;
       nome = new String("src/resources/map1.json");
       int k=0;
       JSONParser parser = new JSONParser();



           Object obj = parser.parse(new FileReader(nome));
           JSONObject myJo = (JSONObject) obj;
           //System.out.println(myJo.get("map"));
           JSONArray myArray = (JSONArray) myJo.get("map");




     Square[][] mapTest = map.getMap();
     for(int i = 0; i<3; i++){
         for(int j = 0; j < 4; j++){

             JSONObject result1 = (JSONObject) myArray.get(k);

             if(mapTest[i][j].getSide(Direction.NORTH) != null) {
                 assertEquals(mapTest[i][j].getSide(Direction.NORTH).toString(), result1.get("NORTH"));
                 assertEquals(mapTest[i][j].getSide(Direction.SOUTH).toString(), result1.get("SOUTH"));
                 assertEquals(mapTest[i][j].getSide(Direction.EAST).toString(), result1.get("EAST"));
                 assertEquals(mapTest[i][j].getSide(Direction.WEST).toString(), result1.get("WEST"));
                 assertEquals(mapTest[i][j].getColor().toString(), result1.get("color"));
             }
             k++;
         }
     }
       assertTrue(mapTest[0][2].isSpawn);
       assertTrue(mapTest[1][0].isSpawn);
       assertTrue(mapTest[2][3].isSpawn);
   }


}
