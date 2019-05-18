package model.map;

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
     //for(int i = 0; i<3; i++){
       //  for(int j = 0; j < 4; j++) {
            int i = 0,j = 0;
             JSONObject result1 = (JSONObject) myArray.get(k);
            if(mapTest[i][j]!=null){
                if (mapTest[i][j].getSide(Direction.NORTH) == null){}

                else if (mapTest[i][j].getSide(Direction.NORTH).getColor() == mapTest[i][j].getColor())
                     assertEquals(mapTest[i][j].getSide(Direction.NORTH).toString(), result1.get("NORTH"));
                else
                     assertEquals("door", result1.get("NORTH"));
                if (mapTest[i][j].getSide(Direction.SOUTH) == null){}

                else if (mapTest[i][j].getSide(Direction.SOUTH).getColor() == mapTest[i][j].getColor())
                    assertEquals(mapTest[i][j].getSide(Direction.SOUTH).toString(), result1.get("SOUTH"));
                else
                    assertEquals("door", result1.get("SOUTH"));
                if (mapTest[i][j].getSide(Direction.WEST) == null){}

                else if (mapTest[i][j].getSide(Direction.WEST).getColor() == mapTest[i][j].getColor())
                    assertEquals(mapTest[i][j].getSide(Direction.WEST).toString(), result1.get("WEST"));
                else
                    assertEquals("door", result1.get("WEST"));
                if (mapTest[i][j].getSide(Direction.EAST) == null){}
                else if (mapTest[i][j].getSide(Direction.EAST).getColor() == mapTest[i][j].getColor())
                    assertEquals(mapTest[i][j].getSide(Direction.EAST).toString(), result1.get("EAST"));
                else
                    assertEquals("door", result1.get("EAST"));

                    //assertEquals(mapTest[i][j].getColor().toString(), result1.get("color"));
         }
         //    k++;
       //  }
     //}
       assertTrue(mapTest[0][2].isSpawn);
       assertTrue(mapTest[1][0].isSpawn);
       assertTrue(mapTest[2][3].isSpawn);
   }


}
