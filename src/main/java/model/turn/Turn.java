package model.turn;

import model.CLI;
import model.Model;
import model.player.Player;
import model.player.PlayerColor;
import model.player.action.KeyMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import java.util.ArrayList;

/**
 * Class that manages the turn flow.
 * It is initialized by the Game class which also calls its methods.
 */
public class Turn {

    private Random random = new Random();
    private final Model model;

    private boolean isFrenzy;
    private boolean isFirstTurn;

    private boolean isTimerOn = false;
    private Timer timer = new Timer();
    private TimerTask turnTimerOff = new TimerTask() {
        @Override
        public void run() {
            isTimerOn = false;
        }
    };
    private long time;

    private Timer grenadeTimer = new Timer();
    private boolean isGrenadeTimerOn = true;
    private TimerTask turnGrenadeTimerOff = new TimerTask() {
        @Override
        public void run() {
            isGrenadeTimerOn = false;
        }
    };
    private long grenadeTime;

    private boolean isRespawnTimerOn = true;
    private long respawnTime;


    public void setFrenzy(boolean frenzy) {
        isFrenzy = frenzy;
    }

    public Turn(Model model, Boolean frenzy){
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("src/resources/constants.json"));
            JSONObject myJo = (JSONObject) obj;
            JSONArray myArray = (JSONArray) myJo.get("constants");
            JSONObject temp = (JSONObject)myArray.get(0);
            time = (long)temp.get("TurnTimer");
            grenadeTime = (long)temp.get("GrenadeTimer");
            respawnTime = (long)temp.get("RespawnTimer");


        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        this.model = model;
        setFrenzy(frenzy);
    }

    public Model getModel(){
        return model;
    }

    public String grenadeTimeOut(){
        StringBuilder stringBuilder = new StringBuilder();
        String color = CLI.getRed();
        stringBuilder.append(color);
        stringBuilder.append("Too late! You didn't choose a grenade in time so none has been thrown.");
        stringBuilder.append(CLI.getResetString());
        return stringBuilder.toString();
    }

    public String respawnTimeOut(){
        StringBuilder stringBuilder = new StringBuilder();
        String color = CLI.getRed();
        stringBuilder.append(color);
        stringBuilder.append("Too late! You didn't choose a card to discard in time");
        stringBuilder.append(" so you discarded a random card.");
        stringBuilder.append(CLI.getResetString());
        return stringBuilder.toString();
    }

    /**
     * Main method of the turn, it sends the correct messages to the View to collect the choices of the player.
     * If the timer ends the method returns and sets the player afk.
     */
    public synchronized void startTurn() {
        //gets the current turn number
        int currentTurnNumber = getModel().getTurnManager().getCurrentTurnNumber();
        //gets the current player
        Player currentPlayer = getModel().getTurnManager().getCurrentPlayer();
        //gets the current player's color
        PlayerColor currentPlayerColor = currentPlayer.getPlayerColor();
        //the player hasn't completed his turn yet
        currentPlayer.getActionTree().setDoneTurn(false);
        isTimerOn = true;
        timer.schedule(turnTimerOff, time); //timer thread

        if (currentTurnNumber == 1) {
            //Show the current player the command list
            StringBuilder toOthers = new StringBuilder();
            toOthers.append(currentPlayer.getColoredName());
            toOthers.append(" is viewing his commands.");
            getModel().printMessage(currentPlayerColor, KeyMap.getCommandList(), toOthers.toString());
            //the current player draws two powerups
            getModel().drawPowerUp(currentPlayerColor, 5); //todo modificato per test
            //requests the current player to discard one of his powerup
            getModel().requestPowerUpDiscard(currentPlayer);
            getModel().getTurnCurrent().setReceivedInput(false);
            while (!getModel().getTurnCurrent().isReceivedInput()) {
                System.out.print("");
                if (!isTimerOn || currentPlayer.isAfk()) {
                    getModel().discardPowerUp(currentPlayer, random.nextInt(2));
                    getModel().setPlayerAfk(currentPlayer);
                    return;
                }
            }
        }
        //while the current player hasn't completed his turn
        while (!currentPlayer.getActionTree().hasDoneTurn()){

            //show the current player his available actions
            //requests the action input
            getModel().chooseAction(currentPlayerColor);
            while (!currentPlayer.getActionTree().isMoveEnded()){
                System.out.print("");
                if (!isTimerOn || currentPlayer.isAfk()){
                    getModel().setPlayerAfk(currentPlayer);
                    if (currentPlayer.getActionTree().getLastAction().getData().equals("shoot")){
                        getModel().notifyShoot(currentPlayer);
                    }
                    return;
                }
            }
            getModel().resetCurrent();
            //grenadePeopleArray is populated by model.addDamage if the hit player has a TagbackGranade
            if (!getModel().getTurnCurrent().getGrenadePeopleArray().isEmpty()){
                for (Player grenadePlayer : getModel().getTurnCurrent().getGrenadePeopleArray()){
                    getModel().tagbackGranadeRequest(grenadePlayer, currentPlayer);
                }
                isGrenadeTimerOn = true;
                grenadeTimer.schedule(turnGrenadeTimerOff, grenadeTime); //start of timer thread

                //As players insert their input they will be removed from the array
                //If the array becomes empty before the timer ends, the timer will be stopped
                //If the timer ends the choice of the players still in the array will be ignored
                //and the array is cleared to get out of the loop.
                while (!getModel().getTurnCurrent().getGrenadePeopleArray().isEmpty()){
                    System.out.print("");
                    if (!isGrenadeTimerOn){
                        for (Player grenadePlayer : getModel().getTurnCurrent().getGrenadePeopleArray()){
                            getModel().getGameNotifier().notifyPlayer(grenadeTimeOut(), grenadePlayer.getPlayerColor());
                        }
                        getModel().getTurnCurrent().getGrenadePeopleArray().clear();
                    }
                }
                grenadeTimer.cancel();
            }

        }

        timer.cancel();
    }

    /**
     * Notify all the players the current state of the game. It is called at the start of each turn.
     */
    public void notifyTurn(){
        //print the game status
        StringBuilder stringBuilder  = new StringBuilder();
        if(isFrenzy){
            stringBuilder.append(CLI.getBlue());
            stringBuilder.append("FRENZY");
            stringBuilder.append(CLI.getResetString());
            stringBuilder.append("\n");
        }
        //print current turn number
        int currentTurnNumber = getModel().getTurnManager().getCurrentTurnNumber();
        stringBuilder.append("Current turn number: ");
        stringBuilder.append(currentTurnNumber);
        stringBuilder.append("\n");
        //print current player
        Player currentPlayer = getModel().getTurnManager().getCurrentPlayer();
        stringBuilder.append("Current player: ");
        stringBuilder.append(currentPlayer.getColoredName());
        stringBuilder.append("\n");
        //print the updated rank
        stringBuilder.append("Player Rank: \n");
        stringBuilder.append(getModel().getScoreManager().showPlayerRank());
        stringBuilder.append("\n");
        //print the killshot track
        stringBuilder.append("Killshot Track:\n");
        stringBuilder.append(getModel().getGameBoard().getKillShotTrack().printKillshotTrack());
        stringBuilder.append("\n\n");
        //print the map
        stringBuilder.append(getModel().getGameBoard().getMap().printMap());
        stringBuilder.append("\n");
        //print all players info
        if(currentTurnNumber != 1) {
            ArrayList<Player> allPlayers = getModel().getAllPlayers();
            for (int i = 0; i < allPlayers.size(); i++) {
                stringBuilder.append(allPlayers.get(i).printPlayerInfo());
                stringBuilder.append("\n");
            }
        }
        if(!isFrenzy) {
            model.verifyNewAction(currentPlayer.getPlayerColor());
        }
        for (Player player : model.getEachPlayer()){
            if (!player.isAfk()){
                getModel().getGameNotifier().notifyPlayer(stringBuilder.toString(), player.getPlayerColor());
            }
        }
    }

    /**
     * Start the respawn procedure for the dead players
     */
    private void respawnProcedure(){
        if (!getModel().getTurnCurrent().getDeadPlayers().isEmpty() /*&& !isFrenzy*/){
            for (Player deadPlayer : getModel().getTurnCurrent().getDeadPlayers()) {
                getModel().drawPowerUp(deadPlayer.getPlayerColor(), 1);
                getModel().requestPowerUpDiscard(deadPlayer);

                if (!deadPlayer.getResources().getPowerUp().isEmpty()){
                    Timer respawnTimer = new Timer();
                    TimerTask turnRespawnTimerOff = new TimerTask() {
                        @Override
                        public void run() {
                            isRespawnTimerOn = false;
                        }
                    };
                    isRespawnTimerOn = true;
                    respawnTimer.schedule(turnRespawnTimerOff, respawnTime);

                    getModel().getTurnCurrent().setReceivedInput(false);
                    while (!getModel().getTurnCurrent().isReceivedInput()){
                        System.out.print("");
                        int cards = deadPlayer.getResources().getPowerUp().size();
                        if (!isRespawnTimerOn){
                            getModel().discardPowerUp(deadPlayer, random.nextInt(cards));
                            getModel().getGameNotifier().notifyPlayer(respawnTimeOut(), deadPlayer.getPlayerColor());
                            getModel().getTurnCurrent().setReceivedInput(true);
                        }
                    }
                    respawnTimer.cancel();
                }

                deadPlayer.setAlive();
            }
            getModel().getTurnCurrent().getDeadPlayers().clear();
        }
    }


    /**
     * Method called after the turn has ended. Update the scores, revives the dead players
     * and prepares the board for the next turn.
     */
    public void endTurn(){

        //Scoring
        getModel().getScoreManager().updateScore();

        //Populates the deadPlayers array for the respawn procedure
        for (Player player : getModel().getEachPlayer()){
            if (player.isDead() || player.isKillShot()){
                getModel().getTurnCurrent().getDeadPlayers().add(player);
            }
        }

        //Flips the points boards of dead players in a frenzy turn
        if(isFrenzy){
            for(Player player : getModel().getTurnCurrent().getDeadPlayers()){
                if(!player.getPlayerBoard().getPoints().isFrenzy()){
                    player.getPlayerBoard().getPoints().setFrenzyPoints();
                }
            }
        }

        //Respawn
        respawnProcedure();

        //Resetting the current
        getModel().resetCurrent();

        //Replace Ammo and Weapon cards on the map
        getModel().getGameBoard().setCardsOnMap();

        //updating turn number
        getModel().getTurnManager().update();
    }
}
