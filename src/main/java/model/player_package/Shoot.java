package model.player_package;

import model.Model;

public class Shoot extends Action {
    private boolean doneShooting;

    @Override
    public void showAction(){

    }
    //implement method
    @Override
    public void perform(Model model, PlayerColor playerColor) {

    }

    public Shoot(){
        doneShooting = false;
    }

    public Boolean isDoneShooting(){
        return doneShooting;
    }

    public void setDoneShooting(Boolean flag){
        doneShooting = flag;
    }
}
