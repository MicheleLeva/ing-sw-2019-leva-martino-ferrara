package model.cards.powerups;

import model.Model;
import model.cards.AmmoColor;
import model.player.Player;
import model.player.PlayerColor;

public class TagbackGrenade extends PowerUp {

    public TagbackGrenade(Model model , AmmoColor color){
        super(model , color);
    }
    @Override
    public void usePowerUp(PlayerColor playerColor){
        Player player = getModel().getPlayer(playerColor);
        getModel().addMark(playerColor, getModel().getTurnManager().getCurrentPlayerColor(), 1);
        getModel().getGameNotifier().notifyGrenade(playerColor, getModel().getTurnManager().getCurrentPlayerColor());
        player.getResources().removePowerUp(this);
        getModel().getGameBoard().getDecks().getDiscardedPowerUpDeck().add(this);
    }
}
