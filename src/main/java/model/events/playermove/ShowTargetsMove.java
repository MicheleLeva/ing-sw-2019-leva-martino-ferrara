package model.events.playermove;

import view.View;

public class ShowTargetsMove extends PlayerMove {
    private int weaponIndex;
    private int fireModeIndex;
    public ShowTargetsMove(View view, int weaponIndex,int fireModeIndex){
        super(view);
        this.weaponIndex = weaponIndex;
        this.fireModeIndex = fireModeIndex;
    }

    public int getWeaponIndex(){
        return this.weaponIndex;
    }

    public int getFireModeIndex(){return this.fireModeIndex;}
}
