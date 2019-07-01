package model.player;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Class that manages the available points on the player's Board
 */
public class Points {

    private boolean isFrenzy = false;
    private ArrayList<Integer> points;
    private ArrayList<Integer> frenzyPoints = new ArrayList<>();
    private int firstBlood;
    private int frenzyFirstBlood;
    private final String path = "src/resources/playerPoints.json";

    /**
     * Constructor for the Points class
     */
    public Points(){
        points = new ArrayList<>();
        readPoints();
    }

    public ArrayList<Integer> getPointsList() {
        return this.points;
    }

    /**
     * Sets the available point by reading them from a json file
     */
    private void readPoints(){
        JSONParser parser = new JSONParser();
        int value;
        try{
            JSONObject myJo;
            try {
                Object obj = parser.parse(new FileReader(path));
                myJo = (JSONObject) obj;
            } catch (FileNotFoundException e) {
                InputStream configStream = this.getClass().getResourceAsStream("/playerPoints.json");
                myJo = (JSONObject)parser.parse(
                        new InputStreamReader(configStream, StandardCharsets.UTF_8));
            }
            JSONArray standardPointsArray = (JSONArray) myJo.get("standard points");
            firstBlood = ((Long)standardPointsArray.get(0)).intValue();
            for (int i = 1; i < standardPointsArray.size(); i++){
                value = ((Long) standardPointsArray.get(i)).intValue();
                points.add(value);
            }

            JSONArray frenzyPointsArray = (JSONArray) myJo.get("frenzy points");
            frenzyFirstBlood = ((Long)frenzyPointsArray.get(0)).intValue();
            for (int j = 1; j < frenzyPointsArray.size(); j++){
                value = ((Long)frenzyPointsArray.get(j)).intValue();
                frenzyPoints.add(value);
            }

        }
        catch( IOException | ParseException e){
            e.printStackTrace();
        }
    }

    /**
     * Removes the first point from the list of points after a player's death
     */

    public void removeHighestPoint(){
        points.remove(0);
    }

    /**
     * Sets player's point to frenzy points
     */
    public void setFrenzyPoints(){
        isFrenzy = true;
        points = frenzyPoints;
        firstBlood = frenzyFirstBlood;
    }

    /**
     * Returns the damage for the first blood
     * @return the damage for the first blood
     */
    public int getFirstBlood(){
        return firstBlood;
    }

    /**
     * Returns the number of points gained based on the rank's position
     * @param position the rank's position
     * @return the points
     */
    public int getPoint(int position){
        if(position >= points.size()){
            return 0;
        }
        else{
            return points.get(position);
        }
    }

    /**
     * Outputs the list of available points
     * @return the list of available points
     */
    public String printPoints(){
        StringBuilder stringBuilder = new StringBuilder();
        for (Integer point : points) {
            stringBuilder.append(point);
            stringBuilder.append(" ");
        }

        return stringBuilder.toString();
    }

    /**
     * Checks whether the points are or aren't frenzy
     * @return true if the points are frenzy, false otherwise
     */
    public boolean isFrenzy(){
        return this.isFrenzy;
    }
}
