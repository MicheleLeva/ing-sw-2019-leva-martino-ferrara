package view;

import model.CLI;
import model.exchanges.messages.PlayerMessage;
import network.ClientConnection;
import utils.Observer;

/**
 * Auxiliary class of the RemoteView that forwards "Game" messages from the Model to the client
 * and events in the opposite direction
 */
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

    /**
     * It simulates the View notify method.
     * Once received the event object form the RemoteView the method uses the correct update
     * in the Controller through Overloading.
     * @param inputs array of strings received from the RemoteView.
     */
    public void stringToMessage(String[] inputs){

        switch(inputs[0]) {
            default:
                System.out.println("An event is in the wrong remote view!\n" + inputs[0]);
        }
    }

    /**
     * Received a PlayerMessage object from the Model it serializes it adding its class tag
     * then sends the string through the socket.
     * @param message to be sent.
     */
    @Override
    public void update(PlayerMessage message) {
        String gameMessage = "GAME," + message.toString();
        clientConnection.asyncSend(gameMessage);

    }
}
