package model.player_package;

public class Run extends Action {

    private int steps;

    @Override
    public void showAction(){

    }

    public Run(){
        steps = 3;
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

}
