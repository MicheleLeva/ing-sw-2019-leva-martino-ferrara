package model.cards;

import model.adrenaline_exceptions.TagbackGrenadeException;
import model.map_package.Square;
import model.player_package.Player;
import model.player_package.PlayerColor;

public class TagbackGrenade extends PowerUp {

    public TagbackGrenade(AmmoColor color){
        cost = color;
    }
    @Override
    public void usePowerUp(PlayerColor playerColor) throws TagbackGrenadeException{
        throw new TagbackGrenadeException();
    }
}
