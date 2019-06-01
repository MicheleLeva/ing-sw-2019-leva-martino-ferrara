package model.player;

import model.CLI;

import java.util.HashMap;

public class MarkCounter {

    private HashMap<PlayerColor, Integer> markList;

    public MarkCounter(){

        this.markList = new HashMap<>();

    }


    public void addMarks(PlayerColor color, int marks){
        if(markList.containsKey(color))
            markList.put(color, markList.get(color) + marks);
        else
            markList.put(color, marks);

    }

    public void clearMarks(PlayerColor color){
        markList.put(color, 0);
    }

    public HashMap<PlayerColor, Integer> getMarkCounter(){
        return markList;
    }

    public int getMarkFromColor(PlayerColor playerColor){
        return markList.get(playerColor);
    }

    public String printMarkCounter(){
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder markBuilder = new StringBuilder();
        for(PlayerColor i : markList.keySet()){
            markBuilder.append(CLI.getColor(i));
            markBuilder.append(getMarkFromColor(i));
            markBuilder.append(CLI.getResetString());
        }

        stringBuilder.append(markBuilder);
        return stringBuilder.toString();
    }
}
