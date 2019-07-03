package model.player;

import model.game.CLI;

import java.util.HashMap;

/**
 * Representation of the player's list of marks
 * @author Michele Leva, Stefano Martino, Marco Maria Ferrara
 */
public class MarkCounter {

    private HashMap<PlayerColor, Integer> markList;

    /**
     * Constructor of MarkCounter class
     */
    public MarkCounter(){

        this.markList = new HashMap<>();

    }


    /**
     * Adds the given number of marks to the markList with the color of the player that gave them
     * @param color color of the player that gave the marks
     * @param marks number of marks given
     */
    public void addMarks(PlayerColor color, int marks){
        if(markList.containsKey(color))
            markList.put(color, markList.get(color) + marks);
        else
            markList.put(color, marks);

    }

    /**
     * Removes all the marks received from a specific player.
     * @param color of the player.
     */
    public void clearMarks(PlayerColor color){
        markList.put(color, 0);
    }

    /**
     * Returns the mark counter
     * @return the mark counter
     */
    public HashMap<PlayerColor, Integer> getMarkCounter(){
        return markList;
    }

    /**
     * Returns the number of marks received by a player with a specific player color.
     * @param playerColor of the player that placed the marks.
     * @return the number of marks received by a player with a specific player color.
     */
    public int getMarkFromColor(PlayerColor playerColor){
        return markList.getOrDefault(playerColor, 0);
    }

    /**
     * Returns and remove all the marks of the given color
     * @param playerColor the given color
     * @return the removed marks
     */
    public int getMarkFromColorAndRemove(PlayerColor playerColor){
        if(markList.containsKey(playerColor))
            return markList.remove(playerColor);
        else return 0;
    }
    /**
     * Outputs the number of marks received
     * @return a string representing the number of received marks
     */
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
