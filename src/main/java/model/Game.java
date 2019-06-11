package model;

import model.turn.Turn;
import network.server.Server;

import java.util.*;

public class Game implements Runnable{

    private final int gameID;

    private final Model model;

    private Random random = new Random();

    private Server server;

    public int getGameID() {
        return gameID;
    }

    private boolean isMapTimerOn = true;
    private long mapTime = 1000L*100; // 10 sec da ottenere da json

    public Game(int gameID, Model model, Server server){
        this.gameID = gameID;
        this.model = model;
        this.server = server;
    }

    public Model getModel(){
        return this.model;
    }

    public int getMapVote(){
        if (model.getMapVotes().isEmpty()){
            System.out.println("Scelgo mappa a caso");
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
        System.out.println("countedmapvotes: "+ countedMapVotes.toString());
        int maxFreq = Collections.max(countedMapVotes);
        int count = 0;
        for(int i : countedMapVotes){
            if (i == maxFreq){
                count++;
            }
        }
        System.out.println("Count: " + count);
        System.out.println("Maxfreq: " + maxFreq);
        if (count == 1){
            System.out.println("Scelgo mappa più votata");
            return countedMapVotes.indexOf(maxFreq) + 1;
        }
        if (count != countedMapVotes.size()){
            ArrayList<Integer> tiebreakers = new ArrayList<>();
            for(int i = 0; i<countedMapVotes.size(); i++){
                if (countedMapVotes.get(i) == maxFreq){
                    tiebreakers.add(i + 1);
                }
            }
            System.out.println("Tiebrakers: " + tiebreakers.toString());
            return tiebreakers.get(random.nextInt(tiebreakers.size() -1 ));
        }
        System.out.println("Scelgo mappa a caso");
        return random.nextInt(3) + 1;
    }

    public void run(){
        /*for (Player player : model.getAllPlayers()) {
            model.mapVote(player);

            Timer mapTimer = new Timer();
            isMapTimerOn = true;
            TimerTask turnMapTimerOff = new TimerTask() {
                @Override
                public void run() {
                    isMapTimerOn = false;
                }
            };
            mapTimer.schedule(turnMapTimerOff, mapTime);
            model.getCurrent().setReceivedInput(false);
            while (!model.getCurrent().isReceivedInput()){
                System.out.print("");
                if (!isMapTimerOn){
                    model.setPlayerAfk(player);
                    mapTimer.cancel();
                    model.getCurrent().setReceivedInput(true);
                }
            }
            mapTimer.cancel();
        }
        int vote = getMapVote();
        System.out.println("Chosen map: " + vote);
        model.setGameBoard(vote);*/
        model.setGameBoard(1);
        while(!model.getTurnManager().isGameOver()){
            Turn currentTurn = new Turn(model,model.getTurnManager().isFrenzy());
            currentTurn.notifyTurn();
            currentTurn.startTurn();
            currentTurn.endTurn();
        }
        System.out.println("Out of Game loop!");
        getModel().getScoreManager().updateScoreFrenzy();
        model.getGameNotifier().notifyGeneric("The game has ended!");
        server.closeGame(gameID);
    }



}
