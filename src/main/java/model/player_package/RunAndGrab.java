package model.player_package;

import model.Model;

public class RunAndGrab extends Action{

    private Player player;
    private int steps;
    private boolean doneGrabbing;

    @Override
    public void showAction(){

    }
    //
    //
    //
    //added temporarely to avoid error
    @Override
    public void perform(Model model, PlayerColor playerColor) throws Exception {

    }

    public RunAndGrab(Player player){
        steps = 1;
        doneGrabbing = false;
    }

    public int getSteps(){
        return steps;
    }

    public void setSteps(int steps){
        this.steps = steps;
    }

    public void decreaseSteps(){
        this.steps = this.steps - 1;
    }

    public void setDoneGrabbing(Boolean flag){
        doneGrabbing = flag;
    }

    public boolean isDoneGrabbing() {
        return doneGrabbing;
    }
}
