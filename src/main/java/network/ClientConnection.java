package network;

import utils.Observer;

/**
 * Interface for the connection used by the server and the client.
 * @author Michele Leva
 */
public interface ClientConnection {

    void closeConnection();

    void asyncSend(String message);

    void register(Observer<String> observer);
}