package model.map;

import model.cards.AmmoColor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class TestMap {
    private Map map;

    @Before
    public void initMap() throws IOException, ParseException {
       /* map = new Map(4);
       map = new Map(5);
        map = new Map(6);
        map = new Map(7);*/
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
       JSONArray myArray = (JSONArray) myJo.get("map");


     Square[][] mapTest = map.getMap();
     for(int i = 0; i<3; i++){
         for(int j = 0; j < 4; j++) {

             JSONObject result1 = (JSONObject) myArray.get(k);
            if(mapTest[i][j]!=null){

                System.out.println(mapTest[i][j].getID());
                for(Direction dir : Direction.values()) {
                    if (mapTest[i][j].getSide(dir) != null){
                        System.out.println(dir.toString() + " : " + mapTest[i][j].getSide(dir).toString());
                        System.out.println(dir.toString() + " : " + mapTest[i][j].getSide(dir).getID());

                    }

                    else
                        System.out.println(dir.toString() + " : "+"wall");
                }
                System.out.println(mapTest[i][j].getColor().toString());
/*
                if (mapTest[i][j].getSide(Direction.NORTH) == null){}

                else if (mapTest[i][j].getSide(Direction.NORTH).getColor().equals(mapTest[i][j].getColor()))
                     assertEquals(mapTest[i][j].getSide(Direction.NORTH).toString(), result1.get("NORTH"));
                else
                     assertEquals("door", result1.get("NORTH"));
                if (mapTest[i][j].getSide(Direction.SOUTH) == null){}

                else if (mapTest[i][j].getSide(Direction.SOUTH).getColor().equals(mapTest[i][j].getColor()))
                    assertEquals(mapTest[i][j].getSide(Direction.SOUTH).toString(), result1.get("SOUTH"));
                else
                    assertEquals("door", result1.get("SOUTH"));
                if (mapTest[i][j].getSide(Direction.WEST) == null){}

                else if (mapTest[i][j].getSide(Direction.WEST).getColor().equals(mapTest[i][j].getColor()))
                    assertEquals(mapTest[i][j].getSide(Direction.WEST).toString(), result1.get("WEST"));
                else
                    assertEquals("door", result1.get("WEST"));
                if (mapTest[i][j].getSide(Direction.EAST) == null){}
                else if (mapTest[i][j].getSide(Direction.EAST).getColor().equals(mapTest[i][j].getColor()))
                    assertEquals(mapTest[i][j].getSide(Direction.EAST).toString(), result1.get("EAST"));
                else
                    assertEquals("door", result1.get("EAST"));
                    if(!result1.get("color").equals("null"))
                    assertEquals(mapTest[i][j].getColor().toString(), result1.get("color"));
*/
         }
             k++;
         }
     }
     /*
       assertTrue(mapTest[0][2].isSpawn);
       assertNull(mapTest[0][3]);
       assertTrue(mapTest[1][0].isSpawn);
       assertTrue(mapTest[0][2].getColor().equals(SquareColor.BLUE));
       assertTrue(mapTest[2][3].isSpawn);
       //System.out.println(map.getSpawnSquare(SquareColor.BLUE).getSquareRow());
       assertEquals(map.getSpawnSquare(SquareColor.RED),mapTest[1][0]);
       assertEquals(map.getSpawnSquare(SquareColor.BLUE),mapTest[0][2]);
       assertEquals(map.getSpawnSquare(SquareColor.YELLOW),mapTest[2][3]);
       */
   }


   @Test(expected = java.io.FileNotFoundException.class)
   public void testException() throws FileNotFoundException{
       String nome;
       nome = new String("src/resources/map12.json");
       int k=0;
       JSONParser parser = new JSONParser();


       try {
           Object obj = parser.parse(new FileReader(nome));
       }
       catch (FileNotFoundException e) {throw new FileNotFoundException();}
       catch (IOException e) {
           e.printStackTrace();
       } catch (ParseException e) {
           e.printStackTrace();
       }
   }




}
