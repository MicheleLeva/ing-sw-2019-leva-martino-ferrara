package model.player_package;

import java.util.ArrayList;

public class ActionBoard {

    private int actionCounter;
    private ArrayList<Action> actions;

    public ActionBoard(int actionCounter, ArrayList<Action> actions){
        this.actionCounter = actionCounter;
        this.actions = actions;
    }

    public void useAction1(){

    }

    public void useAction2(){

    }

    public void useAction3(){

    }

    public void useAction4(){

    }

    public int getActionCounter(){
        return actionCounter;
    }

    public void setActionCounter(int actCounter){
        actionCounter = actCounter;
    }
}
