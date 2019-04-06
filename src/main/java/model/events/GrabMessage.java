package model.events;

import model.Cards.Card;
import model.GameBoard;
import model.player_package.PlayerColor;
;
public class GrabMessage extends Message {

    private final Card card;

    public GrabMessage(PlayerColor playerColor , String playerName , GameBoard gameBoard , Card card){
        super(playerColor , playerName , gameBoard);
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
