package model.player_package;

public class Shoot extends Action {
    private boolean doneShooting;

    @Override
    public void showAction(){

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
