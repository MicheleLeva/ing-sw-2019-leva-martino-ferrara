package model.cards.powerups;
import model.Model;
import model.cards.AmmoColor;
import model.cards.Card;
import model.player.PlayerColor;

public abstract class PowerUp extends Card {

    protected AmmoColor ammo;
    private final Model model;

    public PowerUp(Model model , AmmoColor ammo){
        this.model = model;
        this.ammo = ammo;
    }

    public abstract void usePowerUp(PlayerColor playerColor) throws Exception;

    public AmmoColor getAmmo(){
        return ammo;
    }

    public Model getModel(){
        return model;
    }

}
