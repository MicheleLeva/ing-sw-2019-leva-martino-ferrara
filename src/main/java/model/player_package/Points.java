package model.player_package;

import java.util.ArrayList;

public class Points {

    private ArrayList<Integer> points;
    private Player player;

    public Points(ArrayList<Integer> points, Player player){
        this.player = player;
        this.points = points;

    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<Integer> getPoints() {
        return points;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setPoints(ArrayList<Integer> points) {
        this.points = points;
    }
}
