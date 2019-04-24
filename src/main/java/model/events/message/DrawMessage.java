package model.events.message;

import model.cards.PowerUp;
import model.player_package.PlayerColor;

public class DrawMessage extends Message {

    private final PowerUp card;

    public DrawMessage(PlayerColor playerColor , String playerName , PowerUp card){
        super(playerColor , playerName);
        this.card = card;
    }

    public PowerUp getCard(){
        return card;
    }

    @Override
    public String toPlayer(){
        return ("You drew " +card.toString());
    }

    @Override
    public String toOthers(){
        return(getPlayerName() +" drew a Power Up card");
    }

}
