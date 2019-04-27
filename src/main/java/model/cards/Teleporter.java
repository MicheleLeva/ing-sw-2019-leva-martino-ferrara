package model.cards;


import model.Model;
import model.player_package.PlayerColor;

public class Teleporter extends PowerUp {
    public Teleporter(AmmoColor color){
        powerUpName = "Teleporter";
        cost = color;
    }

    public void usePowerUp(PlayerColor playerColor){
        //Model model = Model.getModelInstance();

        //model.asktTeleporterCoordinates();


    }


}
