package model.cards;
import model.Model;
import model.player_package.PlayerColor;

public class Teleporter extends PowerUp {
    public Teleporter(Model model , AmmoColor color){
        super(model);
        cost = color;
    }
    @Override
    public void usePowerUp(PlayerColor playerColor){
        getModel().useTeleporter(playerColor);
    }
}
