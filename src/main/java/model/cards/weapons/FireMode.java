package model.cards.weapons;

/**
 * Representation of a single fire Mode with its particular name and type(base, alternative, first optional and
 * second optional
 * @author Marco Maria Ferrara
 */
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
