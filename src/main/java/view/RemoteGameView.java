package view;

import model.exchanges.messages.PlayerMessage;
import network.ClientConnection;
import utils.Observer;

public class RemoteGameView implements Observer<PlayerMessage> {
    private ClientConnection clientConnection;
    private View view;

    public RemoteGameView(ClientConnection clientConnection, View view){
        this.clientConnection = clientConnection;
        this.view = view;

        clientConnection.asyncSend("GAME,GenericMessage,Welcome to the game! Your player color is " + view.getPlayerColor().toString());
    }

    public void stringToMessage(String[] inputs){

        switch(inputs[0]) {
            //non ci sono GameEvents dalla view al controller
        }
    }


    @Override
    public void update(PlayerMessage message) {
        String gameMessage = "GAME," + message.toString();
        clientConnection.asyncSend(gameMessage);

    }
}
