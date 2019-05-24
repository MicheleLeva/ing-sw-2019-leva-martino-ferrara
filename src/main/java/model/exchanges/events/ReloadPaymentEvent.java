package model.exchanges.events;

import view.View;

import java.util.ArrayList;

public class ReloadPaymentEvent extends Event {
        private ArrayList<Integer> choices;

        public ReloadPaymentEvent(View view, ArrayList<Integer> choices) {
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
