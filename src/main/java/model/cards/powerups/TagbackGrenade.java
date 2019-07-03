package model.cards.powerups;

import model.game.Model;
import model.adrenaline_exceptions.TagbackGrenadeException;
import model.cards.AmmoColor;
import model.player.Player;
import model.player.PlayerColor;

/**
 * Class of the Tagback Grenade PowerUp
 * @author Michele Leva
 */
public class TagbackGrenade extends PowerUp {

    /**
     * Constructor for the Tagback Grenade class
     * @param model game's model
     * @param color of the powerup
     */
    public TagbackGrenade(Model model , AmmoColor color){
        super(model , color);
        this.name = "TagbackGrenade";
    }

    /**
     * Uses the Tagback Grenade on the target (current player got from the model.getTurnManager())
     * @param playerColor of the user
     * @throws TagbackGrenadeException if it's not the right time to use the power up
     */
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
