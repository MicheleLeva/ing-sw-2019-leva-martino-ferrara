package view;

import model.exchanges.events.ActionEvent;
import model.exchanges.events.QuitAfkEvent;
import model.exchanges.events.VoteMapEvent;
import model.exchanges.messages.PlayerMessage;
import model.exchanges.messages.SetAfkMessage;
import network.ClientConnection;
import utils.Observer;
import utils.observable.ActionObservable;

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


    @Override
    public void update(PlayerMessage message) {
        clientConnection.asyncSend("ACTION," + message.toString());
    }






}
