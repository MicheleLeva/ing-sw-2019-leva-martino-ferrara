package network;

import utils.Observer;

/**
 * Interface fot the connection used by the server and the client.
 */
public interface ClientConnection {

    void closeConnection();

    void asyncSend(String message);

    void register(Observer<String> observer);
}