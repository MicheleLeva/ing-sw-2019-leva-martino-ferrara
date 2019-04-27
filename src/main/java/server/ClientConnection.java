package server;

import utils.Observer;

public interface ClientConnection {

    void closeConnection();

    void asyncSend(String message);

    void register(Observer<String> observer);
}
