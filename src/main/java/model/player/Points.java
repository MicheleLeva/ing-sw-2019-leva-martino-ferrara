package model.player;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Points {

    private ArrayList<Integer> points;
    private ArrayList<Integer> frenzyPoints = new ArrayList<>();
    private int firstBlood;
    private int frenzyFirstBlood;
    private final String path = "src/resources/playerPoints.json"; //todo settare e modificare il costruttore
    public Points(){
        points = new ArrayList<>();
        readPoints();
    }

    public ArrayList<Integer> getPointsList() {
        return this.points;
    }

    private void readPoints(){
        JSONParser parser = new JSONParser();
        int value;
        try{
            Object obj = parser.parse(new FileReader(path));
            JSONObject myJo = (JSONObject)obj;
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
        catch(FileNotFoundException e){
            System.out.println(e);
        }
        catch(IOException e){
            System.out.println(e);
        }
        catch(ParseException e){
            System.out.println(e);
        }
    }
    public void removeHighestPoint(){
        points.remove(0);
    }

    public void setFrenzyPoints(){
        points = frenzyPoints;
        firstBlood = frenzyFirstBlood;
    }

    public int getFirstBlood(){
        return firstBlood;
    }

    public int getPoint(int position){
        if(position >= points.size()){
            return 0;
        }
        else{
            return points.get(position);
        }
    }

}
