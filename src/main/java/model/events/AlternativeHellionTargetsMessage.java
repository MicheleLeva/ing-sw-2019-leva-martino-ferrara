package model.events;

public class AlternativeHellionTargetsMessage extends PlayerMessage {
    private final int targetsNumber;

    public AlternativeHellionTargetsMessage(String message, int targetsNumber){
        super(message);
        this.targetsNumber = targetsNumber;
    }

    public int getTargetsNumber(){
        return this.targetsNumber;
    }
}
