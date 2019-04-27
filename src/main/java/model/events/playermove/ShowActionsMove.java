package model.events.playermove;

import view.View;

public class ShowActionsMove extends PlayerMove {

    //il model controlla l'albero delle azioni e a seconda dell'indice ritorna le azioni possibili
    //se l'albero è alla fine scala di uno l'action counter e rimanda il giocatore a StartMove
    //se l'action counter è a zero manda al giocatore la possibilità di effetuare una reload
    //se siamo già dopo la reload, termina il turno per il giocatore

    public ShowActionsMove(View view){
        super(view);
    }



}
