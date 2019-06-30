package model.cards.powerups;
import model.CLI;
import model.Model;
import model.cards.AmmoColor;
import model.cards.Card;
import model.player.PlayerColor;

public abstract class PowerUp extends Card {

    private AmmoColor ammo;
    private final Model model;
    String name;

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

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        if(ammo.equals(AmmoColor.RED)){
            stringBuilder.append(CLI.getRed());
            stringBuilder.append(name);
            stringBuilder.append(CLI.getResetString());
        }
        if(ammo.equals(AmmoColor.BLUE)){
            stringBuilder.append(CLI.getBlue());
            stringBuilder.append(name);
            stringBuilder.append(CLI.getResetString());
        }
        if(ammo.equals(AmmoColor.YELLOW)){
            stringBuilder.append(CLI.getYellow());
            stringBuilder.append(name);
            stringBuilder.append(CLI.getResetString());
        }

        return stringBuilder.toString();
    }
}
