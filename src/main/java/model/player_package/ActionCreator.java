package model.player_package;

import model.adrenaline_exceptions.IllegalActionException;
import model.player_package.action.KeyMap;

public class ActionCreator {
    public static Action createAction(char move) {
        if (KeyMap.isShoot(move)){
            return new Shoot();
        }

        if (KeyMap.isGrab(move)){
            return new Grab();
        }

        if (KeyMap.isRun(move)){
            return new Run(move);
        }

        if (KeyMap.isRecharge(move)){
            return new Recharge();
        }

        if (KeyMap.isUsePowerUp(move)){
            return new UsePowerUp();
        }

        else return new EndAction();
    }
}
