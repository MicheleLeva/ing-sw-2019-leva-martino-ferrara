package view;

import model.player_package.PlayerColor;

import java.util.Scanner;

public class PlayerView {
    private final ActionView actionView;
    private final WeaponView weaponView;
    private final PowerUpView powerUpView;
    private final GameView gameView;
    private final PlayerColor playerColor;

    private Scanner scanner = new Scanner(System.in);

    public PlayerView(PlayerColor playerColor) {
        this.playerColor = playerColor;
        actionView = new ActionView(playerColor , this);
        weaponView = new WeaponView(playerColor , this);
        powerUpView = new PowerUpView(playerColor , this);
        gameView = new GameView(playerColor);
    }

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

    public void reportError(String error){
        System.out.println(error);
    }

    public int inputInt(){
        return scanner.nextInt();
    }

    public char inputChar(){
        return scanner.next().charAt(0);
    }
}
