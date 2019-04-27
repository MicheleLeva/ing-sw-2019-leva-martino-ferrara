package model.cards;


import model.player_package.Player;

public class TargetingScope  extends PowerUp{


    public TargetingScope(AmmoColor color){
        powerUpName = "TargetingScope";
        cost = color;

    }

    public void usePowerUp(Player player, Player opponent){
        opponent.getPlayerBoard().getDamageCounter().addDamage(player.getPlayerColor(), 1);

    }
}
