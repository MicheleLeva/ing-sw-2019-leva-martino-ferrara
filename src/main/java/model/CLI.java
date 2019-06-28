package model;

import model.cards.AmmoColor;
import model.map.SquareColor;
import model.player.PlayerColor;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CLI {
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
    private static final String CONT_TRANSPARENT = "\u001B[48m";

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

    public static String getColor(AmmoColor ammoColor){
        if(ammoColor == AmmoColor.BLUE){
            return BLUE;
        }

        if(ammoColor == AmmoColor.RED){
            return RED;
        }

        else return YELLOW;
    }

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

    public static String getSkull(){
        return SKULL;
    }
    public static String getResetString(){
        return RESET;
    }

    public static String getDamage(){
        return DAMAGE;
    }

    public static String getBlue(){
        return BLUE;
    }

    public static String getRed(){
        return RED;
    }

    public static String getYellow(){
        return YELLOW;
    }

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
    public static String buildCLIMap(String CLIMapPath)throws FileNotFoundException,IOException {
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

    public static void main(String[] args){
        System.out.print(CONT_GREEN +"verde" +RESET);
    }
}

