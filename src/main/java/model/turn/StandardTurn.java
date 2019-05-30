package model.turn;

import model.Model;
import model.player.Player;
import model.player.PlayerColor;
import model.player.action.KeyMap;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class StandardTurn extends Turn {

    private Random random = new Random();

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
    private long grenadeTime = 1000L*10; // 10 sec da ottenere da json

    private Timer respawnTimer = new Timer();
    private boolean isRespawnTimerOn = true;
    private TimerTask turnRespawnTimerOff = new TimerTask() {
        @Override
        public void run() {
            isRespawnTimerOn = false;
        }
    };
    private long respawnTime = 1000L*10; // 10 sec da ottenere da json

    public void setFrenzy(boolean frenzy) {
        isFrenzy = frenzy;
    }

    public void setFirstTurn(boolean firstTurn) {
        isFirstTurn = firstTurn;
    }

    public StandardTurn(Model model){
        super(model);
    }

    public StandardTurn(Model model, Boolean frenzy){
        super(model);
        setFrenzy(frenzy);
    }

    @Override
    public synchronized void startTurn() {
        int currentTurnNumber = getModel().getTurnManager().getCurrentTurnNumber();
        PlayerColor currentPlayerColor = getModel().getTurnManager().getCurrentPlayerColor();
        Player currentPlayer = getModel().getTurnManager().getPlayerFromColor(currentPlayerColor);

        if (currentTurnNumber == 1) {
            getModel().getTurnManager().startFirstTurn(); //serve?

            isTimerOn = true;
            timer.schedule(turnTimerOff, time); //timer thread

            //Mostra al giocatore la lista comandi
            getModel().printMessage(currentPlayerColor, KeyMap.getCommandList(), "");

            getModel().drawPowerUp(currentPlayerColor, 2);
            getModel().requestPowerUpDiscard(currentPlayer);
            getModel().getCurrent().setReceivedInput(false);
            while (!getModel().getCurrent().isReceivedInput()) {
                if (!isTimerOn) {
                    getModel().discardPowerUp(currentPlayer, random.nextInt(2));
                    getModel().setPlayerAfk(currentPlayer);
                    getModel().getTurnManager().update(); //da mettere nell'end turn
                    return;
                }
            }
        }

        while (!currentPlayer.getActionTree().isTurnEnded()){
            while (!currentPlayer.getActionTree().isActionEnded()){
                //ottieni azioni figlie dall'albero e mostrale
                getModel().printMessage(currentPlayerColor, currentPlayer.getActionTree().availableAction(), "");
                //Chiedi input
                getModel().chooseAction(currentPlayerColor);
                while (!currentPlayer.getActionTree().isMoveEnded()){
                    if (!isTimerOn){
                        getModel().setPlayerAfk(currentPlayer);
                        if (currentPlayer.getActionTree().getLastAction().getData().equals("shoot")){
                            getModel().notifyShoot(currentPlayer, getModel().getCurrent().getAllDamagedPlayer());
                        }
                        getModel().getTurnManager().update(); // da mettere nell'end turn
                        return;
                    }
                }
            }

            //grenadePeopleArray viene popolato da addDamage se il giocatore colpito possiede la granata
            if (!getModel().getCurrent().getGrenadePeopleArray().isEmpty()){
                for (Player grenadePlayer : getModel().getCurrent().getGrenadePeopleArray()){
                    getModel().tagbackGranadeRequest(grenadePlayer.getPlayerColor(), currentPlayerColor);
                }
                isGrenadeTimerOn = true;
                grenadeTimer.schedule(turnGrenadeTimerOff, grenadeTime); //start of timer thread

                //finchè l'array è popolato continuo con il timer
                //l'input di un grenade player lo fa rimuovere dall'array
                while (!getModel().getCurrent().getGrenadePeopleArray().isEmpty()){
                    if (!isGrenadeTimerOn){
                        for (Player grenadePlayer : getModel().getCurrent().getGrenadePeopleArray()){
                            getModel().setPlayerAfk(grenadePlayer);
                        }
                        getModel().getCurrent().getGrenadePeopleArray().clear();
                    }
                }
                grenadeTimer.cancel();
            }
        }
        if (!isFrenzy){
            getModel().getCurrent().setFinishedReloading(false);
            while (!getModel().getCurrent().isFinishedReloading()){
                getModel().askReloadEndTurn(currentPlayerColor);
                getModel().getCurrent().setReceivedInput(false);
                while (!getModel().getCurrent().isReceivedInput()){
                    if (!isTimerOn){
                        getModel().setPlayerAfk(currentPlayer);
                        getModel().getTurnManager().update(); //da mettere nell'end turn
                        return;
                    }
                }
            }
        }

        timer.cancel();

        //todo spostare nell'end turn
        if (!getModel().getCurrent().getDeadPlayers().isEmpty() && !isFrenzy){
            for (Player deadPlayer : getModel().getCurrent().getDeadPlayers()) {
                getModel().drawPowerUp(deadPlayer.getPlayerColor(), 1);
                getModel().requestPowerUpDiscard(deadPlayer);
                isRespawnTimerOn = true;
                respawnTimer.schedule(turnRespawnTimerOff, respawnTime);

                getModel().getCurrent().setReceivedInput(false);
                while (!getModel().getCurrent().isReceivedInput()){
                    if (!isRespawnTimerOn){
                        getModel().discardPowerUp(deadPlayer, random.nextInt(deadPlayer.getResources().getPowerUp().size()));
                        getModel().setPlayerAfk(deadPlayer);
                        getModel().getTurnManager().update();
                        return;
                    }
                }
                respawnTimer.cancel();
            }
        }


        getModel().endTurn();

    }
}
