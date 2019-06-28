package model;

import model.player.Player;
import model.turn.Turn;
import network.server.Server;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Main class which runs the game
 */
public class Game implements Runnable{

    private final int gameID;

    private final Model model;

    private Random random = new Random();

    private Server server;

    public int getGameID() {
        return gameID;
    }

    private boolean isMapTimerOn = true;
    private long mapTime;

    public Game(int gameID, Model model, Server server){
        this.gameID = gameID;
        this.model = model;
        this.server = server;
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("src/resources/constants.json"));
            JSONObject myJo = (JSONObject) obj;
            JSONArray myArray = (JSONArray) myJo.get("constants");
            JSONObject temp = (JSONObject)myArray.get(0);
            mapTime = (long)temp.get("MapTimer");

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public Model getModel(){
        return this.model;
    }

    /**
     * Method which calculates the most voted map from an array of votes found in the Model class
     * @return the index of the most voted map
     */
    public int getMapVote(){
        if (model.getMapVotes().isEmpty()){
            return random.nextInt(3) + 1;
        }
        ArrayList<Integer> countedMapVotes = new ArrayList<>();
        countedMapVotes.add(0);
        countedMapVotes.add(0);
        countedMapVotes.add(0);
        countedMapVotes.add(0);
        for(int i : model.getMapVotes()){
            int temp = countedMapVotes.get(i-1);
            countedMapVotes.set(i-1, temp + 1);
        }
        int maxFreq = Collections.max(countedMapVotes);
        int count = 0;
        for(int i : countedMapVotes){
            if (i == maxFreq){
                count++;
            }
        }
        if (count == 1){
            return countedMapVotes.indexOf(maxFreq) + 1;
        }
        if (count != countedMapVotes.size()){
            ArrayList<Integer> tiebreakers = new ArrayList<>();
            for(int i = 0; i<countedMapVotes.size(); i++){
                if (countedMapVotes.get(i) == maxFreq){
                    tiebreakers.add(i + 1);
                }
            }
            return tiebreakers.get(random.nextInt(tiebreakers.size() -1 ));
        }
        return random.nextInt(3) + 1;
    }

    /**
     * Main thread of the game.
     * It starts with collecting the votes for the map from the players which are saved in the Model class.
     * It loops a turn until the game has ended, then shows the rank and signals the server to close the game.
     */
    public void run(){
        model.getGameNotifier().notifyGeneric("Map voting is starting: wait for your turn.");
        for (Player player : model.getEachPlayer()) {
            model.mapVote(player);

            Timer mapTimer = new Timer();
            TimerTask turnMapTimerOff = new TimerTask() {
                @Override
                public void run() {
                    isMapTimerOn = false;
                }
            };
            isMapTimerOn = true;
            mapTimer.schedule(turnMapTimerOff, mapTime);

            model.getTurnCurrent().setReceivedInput(false);
            while (!model.getTurnCurrent().isReceivedInput()){
                System.out.print("");
                if (!isMapTimerOn){
                    model.getTurnCurrent().setReceivedInput(true);
                    player.setVote(true);
                    mapTimer.cancel();
                }
            }
            mapTimer.cancel();
        }
        int vote = getMapVote();
        System.out.println("Chosen map: " + vote);
        model.setGameBoard(vote);
        //model.setGameBoard(1);
        while(!model.getTurnManager().isGameOver()){
            Turn currentTurn = new Turn(model,model.getTurnManager().isFrenzy());
            System.out.println("Game " + getGameID() + " turn: " + getModel().getTurnManager().getCurrentPlayerColor());
            currentTurn.notifyTurn();
            currentTurn.startTurn();
            currentTurn.endTurn();
        }
        if (model.getTurnManager().isGameOverByAfk()){
            model.getGameNotifier().notifyGeneric("Not enough players to continue the game!");
        }
        System.out.println("Game " + gameID + " has ended!");
        getModel().getScoreManager().finalScore();
        model.getGameNotifier().notifyGeneric("The game has ended!");
        server.closeGame(gameID);
    }



}
