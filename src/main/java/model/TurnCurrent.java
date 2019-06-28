package model;

import model.player.Player;

import java.util.ArrayList;

/**
 * Support class for the Turn class.
 * Contains attributes and thread locks used by Turn methods.
 */
public class TurnCurrent {

    //Turn counters and arrays

    //lock to wait a generic input from client
    private boolean receivedInput;

    public boolean isReceivedInput() {
        return receivedInput;
    }

    public void setReceivedInput(boolean receivedInput) {
        this.receivedInput = receivedInput;
    }

    //people who can use tagback granade
    private ArrayList<Player> grenadePeopleArray = new ArrayList<>();

    public ArrayList<Player> getGrenadePeopleArray() {
        return grenadePeopleArray;
    }

    //people awaiting respawn
    private ArrayList<Player> deadPlayers = new ArrayList<>();

    public ArrayList<Player> getDeadPlayers() {
        return deadPlayers;
    }
}
