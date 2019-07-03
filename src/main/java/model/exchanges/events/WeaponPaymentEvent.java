package model.exchanges.events;

import view.View;

import java.util.ArrayList;

/**
 * Event sent by the Weapon View and received from the Weapon Controller to choose which powerUps to pay the
 * selected fire mode for the current weapon with
 * @author Michele Leva, Stefano Martino, Marco Maria Ferrara
 */
public class WeaponPaymentEvent extends Event {
    private ArrayList<Integer> choices;

    public WeaponPaymentEvent(View view, ArrayList<Integer> choices) {
        super(view);
        this.choices = choices;
    }


    public ArrayList<Integer> getSelectedPowerUps() {
        return choices;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "," +  arrayToString(getSelectedPowerUps());
    }
}
