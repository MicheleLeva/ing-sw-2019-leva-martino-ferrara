package view;

import model.exchanges.events.ActionEvent;
import model.exchanges.events.QuitAfkEvent;
import model.exchanges.events.VoteMapEvent;
import model.exchanges.messages.PlayerMessage;
import network.ClientConnection;
import utils.Observer;
import utils.observable.ActionObservable;

/**
 * Auxiliary class of the RemoteView that forwards "Action" messages from the Model to the client
 * and events in the opposite direction
 */
public class RemoteActionView  extends ActionObservable implements Observer<PlayerMessage>{

    private ClientConnection clientConnection;
    private View view;

    public RemoteActionView(ClientConnection clientConnection, View view){
        this.clientConnection = clientConnection;
        this.view = view;
    }

    public void setClientConnection(ClientConnection clientConnection) {
        this.clientConnection = clientConnection;
    }

    /**
     * It simulates the View notify method.
     * Once received the event object form the RemoteView the method uses the correct update
     * in the Controller through Overloading.
     * @param inputs array of strings received from the RemoteView.
     */
    public void stringToMessage(String[] inputs){

        switch(inputs[0]) {
            case "ActionEvent":
                ActionEvent actionEvent = new ActionEvent(view, inputs[1].charAt(0));
                listeners.forEach(l -> l.update(actionEvent));
                break;
            case "QuitAfkEvent":
                QuitAfkEvent quitAfkEvent = new QuitAfkEvent(view, inputs[1].charAt(0));
                listeners.forEach(l -> l.update(quitAfkEvent));
                break;
            case "VoteMapEvent":
                VoteMapEvent voteMapEvent = new VoteMapEvent(view, inputs[1].charAt(0));
                listeners.forEach(l -> l.update(voteMapEvent));
                break;
        }
    }

    /**
     * Received a PlayerMessage object from the Model it serializes it adding its class tag
     * then sends the string through the socket.
     * @param message to be sent.
     */
    @Override
    public void update(PlayerMessage message) {
        clientConnection.asyncSend("ACTION," + message.toString());
    }






}
