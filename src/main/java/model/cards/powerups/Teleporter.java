package model.cards.powerups;
import model.Model;
import model.cards.AmmoColor;
import model.player.PlayerColor;

public class Teleporter extends PowerUp {
    public Teleporter(Model model , AmmoColor color){
        super(model , color);
    }
    @Override
    public void usePowerUp(PlayerColor playerColor){
        getModel().useTeleporter(playerColor);
    }
}
