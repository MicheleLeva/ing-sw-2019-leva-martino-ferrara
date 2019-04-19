package model.player_package;

import java.util.ArrayList;

public class Points {

    private ArrayList<Integer> points;


    public Points(){
        points = new ArrayList<>();
        points.add(1);
        points.add(1);
        points.add(2);
        points.add(4);
        points.add(6);
        points.add(8);
    }



    public ArrayList<Integer> getPointsList() {
        return this.points;
    }


    public void removeHighestPoint(){
        points.remove(0);

    }
}
