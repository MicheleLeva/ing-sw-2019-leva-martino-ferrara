package view;

import model.events.ActionEvent;
import model.events.PlayerMessage;
import network.ClientConnection;
import utils.Observer;
import utils.observable.ActionObservable;

public class RemoteActionView  extends ActionObservable implements Observer<PlayerMessage>{

    private ClientConnection clientConnection;
    private PlayerView view;

    public RemoteActionView(ClientConnection clientConnection, PlayerView view){
        this.clientConnection = clientConnection;
        this.view = view;
    }

    public void stringToMessage(String[] inputs){

        switch(inputs[0]) {
            case "ActionEvent":
                ActionEvent actionEvent = new ActionEvent(view, inputs[1].charAt(0));
                listeners.forEach(l -> l.update(actionEvent));
                break;
        }
    }


    @Override
    public void update(PlayerMessage message) {
        clientConnection.asyncSend("ACTION," + message.toString());
    }






}
