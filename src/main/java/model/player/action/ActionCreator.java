package model.player.action;

public class ActionCreator {
    //this static method returns the specific action based upon the player's input
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

        if (KeyMap.isReload(move)){
            return new Reload();
        }

        if (KeyMap.isUsePowerUp(move)){
            return new UsePowerUp();
        }

        else return new EndAction();
    }
}
