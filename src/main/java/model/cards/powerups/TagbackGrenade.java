package model.cards.powerups;

import model.Model;
import model.adrenaline_exceptions.TagbackGrenadeException;
import model.cards.AmmoColor;
import model.player.PlayerColor;

public class TagbackGrenade extends PowerUp {

    public TagbackGrenade(Model model , AmmoColor color){
        super(model , color);
    }
    @Override
    public void usePowerUp(PlayerColor playerColor) throws TagbackGrenadeException{
        throw new TagbackGrenadeException();
    }
}
