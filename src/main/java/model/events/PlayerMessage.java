package model.events;

public abstract class PlayerMessage {
    private final String message;

    public PlayerMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
