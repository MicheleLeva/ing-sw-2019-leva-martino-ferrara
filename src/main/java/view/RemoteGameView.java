package view;

import model.CLI;
import model.exchanges.messages.PlayerMessage;
import network.ClientConnection;
import utils.Observer;

public class RemoteGameView implements Observer<PlayerMessage> {
    private ClientConnection clientConnection;
    private View view;

    public void setClientConnection(ClientConnection clientConnection) {
        this.clientConnection = clientConnection;
    }

    public RemoteGameView(ClientConnection clientConnection, View view){
        this.clientConnection = clientConnection;
        this.view = view;

        String message = "GAME,GenericMessage,Welcome to the game! Your player color is ";
        message = message + CLI.getColor(view.getPlayerColor()) + view.getPlayerColor().toString() + CLI.getResetString();
        clientConnection.asyncSend(message);
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
