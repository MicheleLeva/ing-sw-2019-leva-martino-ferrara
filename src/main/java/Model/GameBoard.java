package Model;

import java.util.ArrayList;

public class GameBoard {
    private int killShotIndex;
    private KillShotCell[] killShotTrack;
    private Player doubleKiller;

    private Map ourMap;
    private ArrayList<Player> Players;

    public ArrayList<WeaponCard> weaponDeck;
    public ArrayList<AmmoCard> ammoDeck;
    public ArrayList<PowerUpCard> powerUpDeck;

    private void GameBoard() {


    }

    public Map getMap() {
        return ourMap;

    }

    public void setMap() {


    }

    public void updateKillShotTrack() {


    }

    public void SetDoubleKiller(Player p) {


    }

    public Player getDoubleKiller() {

        return Players.get(0);


    }


}