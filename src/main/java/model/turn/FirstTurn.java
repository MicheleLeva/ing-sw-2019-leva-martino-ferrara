package model.turn;

import model.Model;
import model.player.Player;
import model.player.PlayerColor;

import java.util.Timer;
import java.util.TimerTask;

public class FirstTurn extends StandardTurn{

    public FirstTurn(Model model){
        super(model);
    }

    private boolean isTimerOn = false;

    private Timer timer = new Timer();

    private TimerTask turnTimerOff = new TimerTask() {
        @Override
        public void run() {
            isTimerOn = false;
        }
    };

    private long time = 1000L*300; // 5 minuti

    /*@Override
    public synchronized void startTurn(){
        getModel().getTurnManager().startFirstTurn();
        PlayerColor currentPlayerColor = getModel().getTurnManager().getCurrentPlayerColor();
        Player currentPlayer = getModel().getTurnManager().getPlayerFromColor(currentPlayerColor);

        isTimerOn = true;
        timer.schedule(turnTimerOff, time); //timer thread

        getModel().drawPowerUp(currentPlayerColor , 2);
        getModel().requestPowerUpDiscard(currentPlayer);
        while (!inputricevuto){
            if (!isTimerOn){
                //scarta una a caso e lo posiziono di conseguenza
                currentPlayer.setAfk(true);
                //chiudo il turno
            }
        }

        while (!isturnended){
            while (!isactionended){
                //ottieni azioni figlie dall'albero e mostrale
                while (!azionefinita){
                    if (!isTimerOn){
                        currentPlayer.setAfk(true);
                        //chiudo il turno
                    }
                }
            }
            //nuovo timer granata
            //controllo granata
            //per ogni giocatore mando messaggio
            //contatore dei giocatori colpiti in current
            //array di giocatori colpiti in current (popolato da adddamage)
            while (!granatafinita){
                //granatafinita diventerà true quando l'ultimo giocatore ha riposto
                if (!opponentstimer){
                    //setto granata finita a true
                    //se il giocatore con la granata manda il messaggio dopo che il tempo è finito
                    //check su granatafinita: se è true ignoro l'input del giocatore
                }
            }
        }
        while (!haricaricato){
            //se il turno non è frenzy richiede di ricaricare
            while (!reloadinputricevuto){
                if (!isTimerOn){
                    currentPlayer.setAfk(true);
                    //chiudo il turno
                }
            }
        }

        for (PlayerColor playercolor : giocatorimorti) {
            //apro timer di 10 sec
            //dove player è il giocatore morto
            getModel().drawPowerUp(playercolor, 1);
            getModel().requestPowerUpDiscard(playercolor);
            if (!opponentstimer){
                //scarto a caso
            }
        }

        getModel().endTurn();

    }*/


}
