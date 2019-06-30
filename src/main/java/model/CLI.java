package model;

import model.cards.AmmoColor;
import model.map.SquareColor;
import model.player.PlayerColor;
import java.io.FileReader;
import java.io.IOException;

public class CLI {
    /**
     * Command Line Input Class
     */
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String PURPLE = "\u001B[35m";
    private static final String GREY = "\u001B[37m";

    private static final String SKULL = "\u25A0";
    private static final String DAMAGE = "*";
    private static final String CONT_RED = "\u001B[41m";
    private static final String CONT_YELLOW = "\u001B[42m";
    private static final String CONT_BLUE = "\u001B[44m";
    private static final String CONT_GREY = "\u001B[47m";
    private static final String CONT_PURPLE = "\u001B[45m";
    private static final String CONT_GREEN = "\u001B[40m";

    /**
     * given the player's color, it returns the string code for the color
     * @param playerColor player's color
     * @return string code
     */
    public static String getColor(PlayerColor playerColor){
        if(playerColor == PlayerColor.BLUE){
            return BLUE;
        }

        if(playerColor == PlayerColor.GREEN){
            return GREEN;
        }

        if(playerColor == PlayerColor.PURPLE){
            return PURPLE;
        }

        if(playerColor == PlayerColor.YELLOW){
            return YELLOW;
        }

        else return GREY;
    }

    /**
     * given the ammo's color, it return the string code for the color
     * @param ammoColor ammo's color
     * @return string code
     */
    public static String getColor(AmmoColor ammoColor){
        if(ammoColor == AmmoColor.BLUE){
            return BLUE;
        }

        if(ammoColor == AmmoColor.RED){
            return RED;
        }

        else return YELLOW;
    }

    /**
     * given the square's color, it return the string code for the color
     * @param squareColor square's color
     * @return string code
     */
    public static String getColor(SquareColor squareColor){
        if(squareColor == SquareColor.BLUE){
            return BLUE;
        }

        if(squareColor == SquareColor.RED){
            return RED;
        }

        if(squareColor == SquareColor.GREEN){
            return GREEN;
        }

        if(squareColor == SquareColor.YELLOW){
            return YELLOW;
        }

        if(squareColor == SquareColor.PURPLE){
            return PURPLE;
        }

        else return "";
    }

    /**
     * @return the string code to represent the skull in the command line
     */
    public static String getSkull(){
        return SKULL;
    }

    /**
     * @return the reset string to reset the color
     */
    public static String getResetString(){
        return RESET;
    }

    /**
     * @return the string code to represent the damage in the command line
     */
    public static String getDamage(){
        return DAMAGE;
    }

    /**
     * @return string code for blue
     */
    public static String getBlue(){
        return BLUE;
    }
    /**
     * @return string code for red
     */
    public static String getRed(){
        return RED;
    }

    /**
     * @return string code for yellow
     */
    public static String getYellow(){
        return YELLOW;
    }

    /**
     * helper method called by buildCLIMap
     * @param uncoloredMap the map without color
     * @return the colored map
     */
    private static String colorMap(String uncoloredMap){
        String coloredMap;
        coloredMap = uncoloredMap.replaceAll("Y",CONT_YELLOW + "  " +RESET);
        coloredMap = coloredMap.replaceAll("R",CONT_RED + "  " +RESET);
        coloredMap = coloredMap.replaceAll("B",CONT_BLUE + "  "+RESET);
        coloredMap = coloredMap.replaceAll("w",CONT_GREY + "  " +RESET);
        coloredMap = coloredMap.replaceAll("S","  ");
        coloredMap = coloredMap.replaceAll("G",CONT_GREEN +"  " +RESET);
        coloredMap = coloredMap.replaceAll("P", CONT_PURPLE + "  " +RESET);
        return coloredMap;
    }

    /**
     * reads the map from the given path and color it to display on the command line
     * @param CLIMapPath the path containing the map
     * @return the colored map
     * @throws IOException
     */
    public static String buildCLIMap(String CLIMapPath)throws IOException {
        String uncoloredMap;
        String coloredMap;
        try (FileReader fr = new FileReader(CLIMapPath)) {
            uncoloredMap = "";
            int i;
            while ((i = fr.read()) != -1) {
                uncoloredMap = uncoloredMap + (char) i;
            }
        }
        coloredMap = colorMap(uncoloredMap);
            return coloredMap;
    }

}

