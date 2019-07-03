package model.cards.powerups;
import model.game.Model;
import model.adrenaline_exceptions.TargetingScopeException;
import model.cards.AmmoColor;
import model.player.PlayerColor;

/**
 * Class of the Targeting Scope Power Up
 * @author Marco Maria Ferrara
 */
public class TargetingScope  extends PowerUp{

    /**
     * Constructor for the Targeting Scope class
     * @param model game's model
     * @param color of the powerup
     */
    public TargetingScope(Model model , AmmoColor color){
        super(model , color);
        this.name = "TargetingScope";
    }

    /**
     * The actual method is implemented in the model
     * @param playerColor of the user
     * @throws TargetingScopeException if it's not the right time to use the power up
     */
    @Override
    public void usePowerUp(PlayerColor playerColor) throws TargetingScopeException {
        throw new TargetingScopeException();
    }
}
