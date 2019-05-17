package view;

import model.events.*;
import model.events.PlayerMessage;
import network.ClientConnection;
import utils.Observer;

public class RemoteGameView implements Observer<PlayerMessage> {
    private ClientConnection clientConnection;
    private PlayerView view;

    public RemoteGameView(ClientConnection clientConnection, PlayerView view){
        this.clientConnection = clientConnection;
        this.view = view;
    }

    public void stringToMessage(String[] inputs){

        switch(inputs[0]) {
            //non ci sono GameEvents dalla view al controller
        }
    }


    @Override
    public void update(PlayerMessage message) {
        clientConnection.asyncSend("GAME," + message.toString());
    }
}
