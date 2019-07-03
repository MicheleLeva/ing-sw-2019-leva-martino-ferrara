package model.cards.powerups;
import model.game.Model;
import model.cards.AmmoColor;
import model.player.PlayerColor;

/**
 * Class of the Teleporter Power Up
 * @author Marco Maria Ferrara
 */
public class Teleporter extends PowerUp {

    /**
     * Constructor for the Teleporter class
     * @param model game's model
     * @param color of the powerup
     */
    public Teleporter(Model model , AmmoColor color){
        super(model , color);
        this.name = "Teleporter";
    }

    /**
     * Starts the chain of messages to use the Power Up and discards the card
     * @param playerColor of the user
     */
    @Override
    public void usePowerUp(PlayerColor playerColor){
        getModel().useTeleporter(playerColor);
        getModel().getGameBoard().getDecks().getDiscardedPowerUpDeck().add(this);
        getModel().getPlayer(playerColor).getResources().getPowerUp().remove(this);
    }
}
