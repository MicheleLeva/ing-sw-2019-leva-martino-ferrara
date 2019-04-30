package model.player_package;

import model.adrenaline_exceptions.IllegalActionException;
import model.player_package.action.KeyMap;

public class ActionCreator {
    public Action createAction(char move) throws IllegalActionException{
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

        throw new IllegalActionException();
    }
}
