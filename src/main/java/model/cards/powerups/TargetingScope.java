package model.cards.powerups;
import model.Model;
import model.adrenaline_exceptions.TargetingScopeException;
import model.cards.AmmoColor;
import model.player.PlayerColor;

public class TargetingScope  extends PowerUp{
    public TargetingScope(Model model , AmmoColor color){
        super(model , color);
        this.name = "TargetingScope";
    }
    @Override
    public void usePowerUp(PlayerColor playerColor) throws TargetingScopeException {
        throw new TargetingScopeException();
    }
}
