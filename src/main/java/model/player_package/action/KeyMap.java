package model.player_package.action;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

//Classe che mappa i tasti alle mosse
public class KeyMap {

    private  static char moveUp;

    private  static char moveDown;

    private  static char moveRight;

    private  static char moveLeft;

    private  static char grab;

    private  static char shoot;

    private  static char recharge;

    private static char end;

    private static char usePowerUp;

    private final static String defaultPath = "src/resources/keyMapping.json";
    //Metodo che legge la classe da File JSON
    public KeyMap(String path){
        JSONParser parser = new JSONParser();
        try{
            Object obj = parser.parse(new FileReader(path));
            JSONObject myJo = (JSONObject) obj;

            moveUp = myJo.get("moveUp").toString().charAt(0);
            moveDown = myJo.get("moveDown").toString().charAt(0);
            moveRight = myJo.get("moveRight").toString().charAt(0);
            moveLeft = myJo.get("moveLeft").toString().charAt(0);

            grab = myJo.get("grab").toString().charAt(0);
            shoot = myJo.get("shoot").toString().charAt(0);
            recharge = myJo.get("recharge").toString().charAt(0);

            end = myJo.get("end").toString().charAt(0);
            usePowerUp = myJo.get("usePowerUp").toString().charAt(0);
        }

        catch(FileNotFoundException e ){
            System.out.println(e);
        }

        catch(IOException e ){
            System.out.println(e);
        }

        catch(ParseException e){
            System.out.println(e);
        }
    }

    public KeyMap(){
        this(defaultPath);
    }
    //Metodo che verifica se una mossa è valida
    public static boolean isValid (String type , char move){
        switch (type){
            case "run":
                return (move == moveUp || move == moveDown || move == moveRight || move == moveLeft);

            case "grab":
                return (move == grab);


            case "shoot":
                return (move == shoot);


            case "recharge":
                return (move == recharge);

                default:
                return false;
        }
    }

    public static char getEnd(){
        return end;
    }

}
