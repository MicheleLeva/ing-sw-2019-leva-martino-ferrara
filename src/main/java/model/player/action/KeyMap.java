package model.player.action;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * KeyMap Class
 * Contains the binding between keys and moves
 */
public class KeyMap {

    private  static char moveUp;

    private  static char moveDown;

    private  static char moveRight;

    private  static char moveLeft;

    private  static char grab;

    private  static char shoot;

    private  static char reload;

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
            reload = myJo.get("reload").toString().charAt(0);

            end = myJo.get("end").toString().charAt(0);
            usePowerUp = myJo.get("usePowerUp").toString().charAt(0);
            //showCards = myJo.get("showCards").toString().charAt(0);
        }

        catch(IOException | ParseException e ){
            e.printStackTrace();
        }


    }

    public KeyMap(){
        this(defaultPath);
    }

    /**
     * This method checks whether the selecterd input is or not valid
     * @param type the type of the move
     * @param move the current move's key
     * @return true if the move is valid
     */
    public static boolean isValid (String type , char move){
        switch (type){
            case "run":
                return (move == moveUp || move == moveDown || move == moveRight || move == moveLeft);

            case "grab":
                return (move == grab);


            case "shoot":
                return (move == shoot);


            case "reload":
                return (move == reload);

                default:
                return false;
        }
    }

    /**
     * This method checks if it exists a valid move based upon the selected input
     * @param move selected input
     * @return true if a valid move exists
     */
    public static boolean isValid(char move){
        return(move == moveUp || move == moveDown || move == moveRight || move == moveLeft ||
                move == grab || move == shoot || move == reload ||
                move == end || move == usePowerUp);
    }

    public static char getEnd(){
        return end;
    }


    public static char getUsePowerUp(){
        return usePowerUp;
    }

    /**
     * Builds the command list
     * @return a String containing the command list
     */
    public static String getCommandList(){
        String result;
        result = "Command List:\n";

        String moveString;
        moveString = "MoveUp: " +moveUp +" | ";
        moveString = moveString + "MoveLeft: " +moveLeft +" | ";
        moveString = moveString + "MoveDown: " +moveDown +" | ";
        moveString = moveString + "MoveRight: " +moveRight +"\n";

        String actionString;
        actionString = "Grab: " +grab +" | ";
        actionString = actionString +"Shoot: " +shoot +"\n";
        actionString = actionString +"Use PowerUp: " +usePowerUp +" | ";
        actionString = actionString +"End Action: " +end +"\n";

        result = result + moveString + actionString;

        return result;

    }

    public static boolean isShoot(char move){
        return (shoot == move);
    }

    public static boolean isGrab(char move){
        return (grab == move);
    }

    public static boolean isRun(char move){
        return (move == moveRight || move == moveUp || move == moveDown || move == moveLeft);
    }

    public static boolean isRunUp(char move){
        return (move == moveUp);
    }

    public static boolean isRunLeft(char move){
        return (move == moveLeft);
    }

    public static boolean isRunDown(char move){
        return (move == moveDown);
    }

    public static boolean isRunRight(char move){
        return (move == moveRight);
    }

    public static boolean isReload(char move){
        return (move == reload);
    }

    public static boolean isUsePowerUp(char move){
        return (move == usePowerUp);
    }

    public static boolean isEnd(char move){
        return (move == end);
    }

    public static char getShoot()
    {
        return shoot;
    }
    public static char getMoveUp()
    {
        return moveUp;
    }
    public static char getMoveDown()
    {
        return moveDown;
    }
    public static char getMoveRight()
    {
        return moveRight;
    }
    public static char getMoveLeft()
    {
        return moveLeft;
    }
    public static char getGrab()
    {
        return grab;
    }
    public static char getReload()
    {
        return reload;
    }


}
