package model.cards;
import model.adrenaline_exceptions.TargetingScopeException;
import model.player_package.PlayerColor;

public class TargetingScope  extends PowerUp{
    public TargetingScope(AmmoColor color){
        cost = color;
    }
    @Override
    public void usePowerUp(PlayerColor playerColor) throws TargetingScopeException {
        throw new TargetingScopeException();
    }
}
