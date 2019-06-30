package model.cards.powerups;

import model.game.Model;
import model.adrenaline_exceptions.TagbackGrenadeException;
import model.cards.AmmoColor;
import model.player.Player;
import model.player.PlayerColor;

public class TagbackGrenade extends PowerUp {

    public TagbackGrenade(Model model , AmmoColor color){
        super(model , color);
        this.name = "TagbackGrenade";
    }
    @Override
    public void usePowerUp(PlayerColor playerColor) throws TagbackGrenadeException {
        if (!getModel().getTurnCurrent().getGrenadePeopleArray().isEmpty()) {
            Player player = getModel().getPlayer(playerColor);
            getModel().addMark(playerColor, getModel().getTurnManager().getCurrentPlayerColor(), 1);
            getModel().getGameNotifier().notifyGrenade(playerColor, getModel().getTurnManager().getCurrentPlayerColor());
            player.getResources().removePowerUp(this);
            getModel().getGameBoard().getDecks().getDiscardedPowerUpDeck().add(this);
        } else throw new TagbackGrenadeException();

    }
}
