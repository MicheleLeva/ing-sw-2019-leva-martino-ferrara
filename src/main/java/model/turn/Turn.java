package model.turn;

import model.Model;
import model.player.Player;
import model.player.PlayerColor;
import model.player.action.KeyMap;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import java.util.ArrayList;

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
    private long time = 1000L*300; // 5 minuti

    private Timer grenadeTimer = new Timer();
    private boolean isGrenadeTimerOn = true;
    private TimerTask turnGrenadeTimerOff = new TimerTask() {
        @Override
        public void run() {
            isGrenadeTimerOn = false;
        }
    };
    private long grenadeTime = 1000L*60; // 10 sec da ottenere da json

    private boolean isRespawnTimerOn = true;
    private long respawnTime = 1000L*10; // 10 sec da ottenere da json

    public void setFrenzy(boolean frenzy) {
        isFrenzy = frenzy;
    }

    public void setFirstTurn(boolean firstTurn) {
        isFirstTurn = firstTurn;
    }

    public Turn(Model model){
        this.model = model;
    }

    public Turn(Model model, Boolean frenzy){
        this.model = model;
        setFrenzy(frenzy);
    }

    public Model getModel(){
        return model;
    }


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
            getModel().drawPowerUp(currentPlayerColor, 2); //todo modificato per test
            //requests the current player to discard one of his powerup
            getModel().requestPowerUpDiscard(currentPlayer);
            getModel().getCurrent().setReceivedInput(false);
            while (!getModel().getCurrent().isReceivedInput()) {
                System.out.print("");
                if (!isTimerOn) {
                    getModel().discardPowerUp(currentPlayer, random.nextInt(2));
                    getModel().setPlayerAfk(currentPlayer);
                    getModel().getTurnManager().update(); //da mettere nell'end turn
                    return;
                }
            }
        }
        //while the current player hasn't completed his turn
        while (!currentPlayer.getActionTree().hasDoneTurn()){
            //show the current player his available actions
            StringBuilder toOthers = new StringBuilder();
            toOthers.append(currentPlayer.getColoredName());
            toOthers.append(" is choosing between his actions.");
            getModel().printMessage(currentPlayerColor, currentPlayer.getActionTree().availableAction(), toOthers.toString());
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
            //grenadePeopleArray viene popolato da addDamage se il giocatore colpito possiede la granata
            /*if (!getModel().getCurrent().getGrenadePeopleArray().isEmpty()){
                for (Player grenadePlayer : getModel().getCurrent().getGrenadePeopleArray()){
                    getModel().tagbackGranadeRequest(grenadePlayer.getPlayerColor(), currentPlayerColor);
                }
                isGrenadeTimerOn = true;
                grenadeTimer.schedule(turnGrenadeTimerOff, grenadeTime); //start of timer thread

                //finchè l'array è popolato continuo con il timer
                //l'input di un grenade player lo fa rimuovere dall'array
                while (!getModel().getCurrent().getGrenadePeopleArray().isEmpty()){
                    System.out.print("");
                    if (!isGrenadeTimerOn){
                        for (Player grenadePlayer : getModel().getCurrent().getGrenadePeopleArray()){
                            getModel().setPlayerAfk(grenadePlayer);
                        }
                        getModel().getCurrent().getGrenadePeopleArray().clear();
                    }
                }
                grenadeTimer.cancel();
            }*/

        }

        timer.cancel();
    }


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
        getModel().getGameNotifier().notifyGeneric(stringBuilder.toString());
    }

    public void endTurn(){

        //Respawn
        if (!getModel().getCurrent().getDeadPlayers().isEmpty() && !isFrenzy){
            for (Player deadPlayer : getModel().getCurrent().getDeadPlayers()) {
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

                getModel().getCurrent().setReceivedInput(false);
                while (!getModel().getCurrent().isReceivedInput()){
                    System.out.print("");
                    if (!isRespawnTimerOn){
                        getModel().discardPowerUp(deadPlayer, random.nextInt(deadPlayer.getResources().getPowerUp().size()));
                        getModel().setPlayerAfk(deadPlayer);
                        getModel().getTurnManager().update();
                    }
                }
                respawnTimer.cancel();
            }
        }

        //Scoring
        if (isFrenzy){
            getModel().getScoreManager().updateScoreFrenzy();
        } else {
            getModel().getScoreManager().updateScore();
        }

        //Replace Ammo and Weapon cards on the map
        getModel().getGameBoard().setCardsOnMap();

        //
        getModel().getTurnManager().update();
    }
}
