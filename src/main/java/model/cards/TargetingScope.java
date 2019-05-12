package model.cards;
import model.Model;
import model.adrenaline_exceptions.TargetingScopeException;
import model.player_package.PlayerColor;

public class TargetingScope  extends PowerUp{
    public TargetingScope(Model model , AmmoColor color){
        super(model , color);
    }
    @Override
    public void usePowerUp(PlayerColor playerColor) throws TargetingScopeException {
        throw new TargetingScopeException();
    }
}
