package model.exchanges.messages;

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
