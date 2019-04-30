package view;

import model.player_package.PlayerColor;
import server.ClientConnection;
import utils.Observer;

public class RemoteView extends View {

    private class MessageReceiver implements Observer<String> {

        @Override
        public void update(String message) {
            System.out.println("Received " + message);
            try{
                String[] inputs = message.split(",");
                //devo ricreare la playermove dall'input che arriva dal client
                //faccio fare alla view la notify al controller
            }catch(IllegalArgumentException e){
                clientConnection.asyncSend("Error!");
            }
        }

    }

    ClientConnection clientConnection;

    public RemoteView(PlayerColor playerColor, ClientConnection c) {
        super(playerColor);
        this.clientConnection=c;
        c.register(new MessageReceiver());
        c.asyncSend("Welcome to the game! Your player color is " + playerColor.toString()); //messaggio di benvenuto?
    }


}
