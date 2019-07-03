package model.exchanges.messages;

/**
 * Abstract class extended by all other messages
 * @author Michele Leva, Stefano Martino, Marco Maria Ferrara
 */
public abstract class PlayerMessage {
    private final String message;
    private String name = getClass().getSimpleName();

    public PlayerMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }

    @Override
    public String toString() {
        return name + "," + getMessage();
    }
}
