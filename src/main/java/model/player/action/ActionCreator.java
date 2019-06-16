package model.player.action;

public class ActionCreator {
    /**
     * This static method returns the action based upon the player's input
     * @param move the player's input
     * @return the corresponding Action
     */
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
