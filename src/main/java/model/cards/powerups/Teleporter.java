package model.cards.powerups;
import model.game.Model;
import model.cards.AmmoColor;
import model.player.PlayerColor;

public class Teleporter extends PowerUp {
    public Teleporter(Model model , AmmoColor color){
        super(model , color);
        this.name = "Teleporter";
    }
    @Override
    public void usePowerUp(PlayerColor playerColor){
        getModel().useTeleporter(playerColor);
        getModel().getGameBoard().getDecks().getDiscardedPowerUpDeck().add(this);
        getModel().getPlayer(playerColor).getResources().getPowerUp().remove(this);
    }
}
