package model.cards;

public class FireMode {
    private final String type;
    private final String effectName;

    public FireMode(String type, String effectName){
        this.type = type;
        this.effectName = effectName;
    }

    public String getType(){
        return this.type;
    }

    public String getEffectName(){
        return this.effectName;
    }
}
