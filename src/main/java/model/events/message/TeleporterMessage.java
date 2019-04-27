package model.events.message;

import model.events.message.Message;
import model.player_package.PlayerColor;

public class TeleporterMessage extends Message  {


    public TeleporterMessage() {
        super();
    }

    public String toPlayer() {
        return "seleziona le coordinate in cui teletrasportarti";
    }
    public String toOthers(){
        return "Player is using teleporter";
        }

}
