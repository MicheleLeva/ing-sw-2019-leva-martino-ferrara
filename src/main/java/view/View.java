package view;

import model.player.PlayerColor;

import java.util.Scanner;

/**
 * Main View used as a CLI interface
 */
public class View {
    private final ActionView actionView;
    private final WeaponView weaponView;
    private final PowerUpView powerUpView;
    private final GameView gameView;
    private final PlayerColor playerColor;

    private Scanner scanner = new Scanner(System.in);

    public View(PlayerColor playerColor) {
        this.playerColor = playerColor;
        actionView = new ActionView(playerColor , this);
        weaponView = new WeaponView(playerColor , this);
        powerUpView = new PowerUpView(playerColor , this);
        gameView = new GameView(playerColor , this);
    }

    private String in = "";

    public ActionView getActionView() {
        return actionView;
    }

    public WeaponView getWeaponView() {
        return weaponView;
    }

    public PowerUpView getPowerUpView() {
        return powerUpView;
    }

    public GameView getGameView() {
        return gameView;
    }

    public PlayerColor getPlayerColor() {
        return playerColor;
    }

    /**
     * Method called by the controller if the player inserts an invalid input
     * @param error to be shown to the player
     */
    public void reportError(String error){
        System.out.println(error);
    }

    /**
     * Starts the scanner loop to read the input from the player
     */
    public void startScanner(){
        boolean isActive = true;
        while(isActive){
            in = scanner.nextLine();
        }
    }

    /**
     * Checks if the given string is an integer
     * @param value string to check
     * @return true if the String is an integer, false otherwise
     */
    public boolean isStringAnInt(final String value){
        for(int i = 0; 0<=i && i<value.length(); i++){
            char c = value.charAt(i);
            if (!(c >= '0' && c <= '9')){
                return false;
            }
        }
        return true;
    }

    /**
     * Clears the last input inserted and collect the next integer inserted by the player
     * @return the collected integer
     */
    public int inputInt(){
        boolean validInput = false;
        int input = -1; //it will be overwritten
        while (!validInput) {
            in = "";
            while (in.isEmpty()){
                System.out.print("");
            }
            if (isStringAnInt(in)){
                input = Integer.parseInt(in);
                validInput = true;
            } else{
                printMessage("Invalid input! Insert a number!");
                in = "";
            }
        }
        return  input;
    }

    /**
     * Clears the last input inserted and collect the next char inserted by the player
     * @return the collected char
     */
    public char inputChar(){
        boolean validInput = false;
        char input = '$'; //it will be overwritten
        while (!validInput) {
            in = "";
            while (in.isEmpty()){
                System.out.print("");
            }
            if (in.length()==1){
                input = in.charAt(0);
                validInput = true;
            } else{
                printMessage("Invalid input! Insert a single character!");
                in = "";
            }
        }
        return input;
    }

    /**
     * Prints the given String to screen
     * @param message to be shown to the player
     */
    public synchronized void printMessage(String message){
        System.out.println(message);
    }

}
