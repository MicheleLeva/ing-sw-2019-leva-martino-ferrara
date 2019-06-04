package model;

import model.player.Player;
import model.turn.Turn;

import java.util.*;

public class Game implements Runnable{

    private final int gameID;

    private final Model model;

    private Random random;

    public int getGameID() {
        return gameID;
    }

    private Timer mapTimer = new Timer();
    private boolean isMapTimerOn = true;
    private TimerTask turnMapTimerOff = new TimerTask() {
        @Override
        public void run() {
            isMapTimerOn = false;
        }
    };
    private long mapTime = 1000L*10; // 10 sec da ottenere da json

    public Game(int gameID, Model model){
        this.gameID = gameID;
        this.model = model;
        run();
    }

    public int getMapVote(){
        if (model.getMapVotes().isEmpty()){
            return random.nextInt(3) + 1;
        }
        ArrayList<Integer> countedMapVotes = new ArrayList<>();
        countedMapVotes.add(0);
        countedMapVotes.add(0);
        countedMapVotes.add(0);
        countedMapVotes.add(0);
        countedMapVotes.add(0);
        for(int i : model.getMapVotes()){
            int temp = countedMapVotes.get(i);
            countedMapVotes.set(i, temp + 1);
        }
        int maxFreq = Collections.max(countedMapVotes);
        int count = 0;
        for(int i : countedMapVotes){
            if (countedMapVotes.get(i).equals(maxFreq)){
                count++;
            }
        }
        if (count == 1){
            return countedMapVotes.indexOf(maxFreq);
        }
        if (count != countedMapVotes.size()){
            ArrayList<Integer> tiebreakers = new ArrayList<>();
            for(int i : countedMapVotes){
                if (countedMapVotes.get(i).equals(maxFreq)){
                    tiebreakers.add(countedMapVotes.indexOf(i) + 1);
                }
            }
            return tiebreakers.get(random.nextInt(1));
        }

        return random.nextInt(3) + 1;
    }

    public void run(){
        /*for (Player player : model.getAllPlayers()) {
            model.mapVote(player);

            isMapTimerOn = true;
            mapTimer.schedule(turnMapTimerOff, mapTime);
            model.getCurrent().setReceivedInput(false);
            while (!model.getCurrent().isReceivedInput()){
                System.out.print("");
                if (!isMapTimerOn){
                    model.setPlayerAfk(player);
                    mapTimer.cancel();
                }
            }
        }*/
        //model.setGameBoard(getMapVote());
        model.setGameBoard(1);
        while(!model.getTurnManager().isGameOver()){
            Turn currentTurn = new Turn(model,model.getTurnManager().isFrenzy());
            currentTurn.notifyTurn();
            currentTurn.startTurn();
            currentTurn.endTurn();
        }
        //manda punteggi
        //clear game dal server
    }



}
