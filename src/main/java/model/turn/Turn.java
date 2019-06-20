package model.turn;

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
                if (!isTimerOn) {
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
                if (!isTimerOn){
                    getModel().setPlayerAfk(currentPlayer);
                    if (currentPlayer.getActionTree().getLastAction().getData().equals("shoot")){
                        getModel().notifyShoot(currentPlayer);
                    }
                    return;
                }
            }
            getModel().resetCurrent();
            //grenadePeopleArray viene popolato da addDamage se il giocatore colpito possiede la granata
            if (!getModel().getTurnCurrent().getGrenadePeopleArray().isEmpty()){
                for (Player grenadePlayer : getModel().getTurnCurrent().getGrenadePeopleArray()){
                    getModel().tagbackGranadeRequest(grenadePlayer, currentPlayer);
                }
                isGrenadeTimerOn = true;
                grenadeTimer.schedule(turnGrenadeTimerOff, grenadeTime); //start of timer thread

                //finchè l'array è popolato continuo con il timer
                //l'input di un grenade player lo fa rimuovere dall'array
                while (!getModel().getTurnCurrent().getGrenadePeopleArray().isEmpty()){
                    System.out.print("");
                    if (!isGrenadeTimerOn){
                        for (Player grenadePlayer : getModel().getTurnCurrent().getGrenadePeopleArray()){
                            getModel().getGameNotifier().notifyPlayer("Too late!", grenadePlayer.getPlayerColor());
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
        stringBuilder.append("Current Rank: \n");
        stringBuilder.append(getModel().getScoreManager().showPlayerRank()); //todo settare i colori nel rank
        stringBuilder.append("\n");
        //print the killshot track
        stringBuilder.append(getModel().getGameBoard().getKillShotTrack().printKillshotTrack());
        stringBuilder.append("\n");
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
        for (Player player : model.getEachPlayer()){
            if (!player.isAfk()){
                getModel().getGameNotifier().notifyPlayer(stringBuilder.toString(), player.getPlayerColor());
            }
        }
    }

    /**
     * Method called after the turn has ended. Update the scores, respawns the dead players and prepares the next turn.
     */
    public void endTurn(){

        //Scoring
        getModel().getScoreManager().updateScore();


        for (Player player : getModel().getEachPlayer()){
            if (player.isDead() || player.isKillShot()){
                getModel().getTurnCurrent().getDeadPlayers().add(player);
            }
        }

        //Respawn
        if (!getModel().getTurnCurrent().getDeadPlayers().isEmpty() /*&& !isFrenzy*/){
            for (Player deadPlayer : getModel().getTurnCurrent().getDeadPlayers()) {
                getModel().drawPowerUp(deadPlayer.getPlayerColor(), 1);
                getModel().requestPowerUpDiscard(deadPlayer);

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
                    if (!isRespawnTimerOn){
                        getModel().discardPowerUp(deadPlayer, random.nextInt(deadPlayer.getResources().getPowerUp().size()));
                        getModel().setPlayerAfk(deadPlayer);
                        getModel().getTurnCurrent().setReceivedInput(true);
                    }
                }
                respawnTimer.cancel();
                deadPlayer.setAlive();
            }
            getModel().getTurnCurrent().getDeadPlayers().clear();
        }



        //Resetting the current
        getModel().resetCurrent();

        //Replace Ammo and Weapon cards on the map
        getModel().getGameBoard().setCardsOnMap();

        //updating turn number
        getModel().getTurnManager().update();
    }
}
