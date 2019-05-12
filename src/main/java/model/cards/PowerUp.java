package model.cards;
import model.Model;
import model.player_package.PlayerColor;

public abstract class PowerUp extends Card{

    protected AmmoColor cost;
    private final Model model;

    public PowerUp(Model model , AmmoColor cost){
        this.model = model;
        this.cost = cost;
    }

    public abstract void usePowerUp(PlayerColor playerColor) throws Exception;

    public AmmoColor getCost(){
        return cost;
    }

    public Model getModel(){
        return model;
    }

}
