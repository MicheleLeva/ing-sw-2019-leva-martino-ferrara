package model.events;

import model.cards.Card;
import model.player_package.PlayerColor;
public class GrabMessage extends Message {

    private final Card card;

    public GrabMessage(PlayerColor playerColor , String playerName , Card card){
        super(playerColor , playerName);
        this.card = card;
    }

    public Card getCard(){
        return card;
    }

    @Override
    public String toPlayer(){
        return ("You grabbed " +card.toString());
    }

    @Override
    public String toOthers(){
        return(getPlayerName() +" has grabbed a card");
    }
}
