package model.events;

import model.Model;
import model.adrenaline_exceptions.WallException;
import model.map_package.Direction;
import view.View;


public class RunMove extends PlayerMove {

    private final int x;
    private final int y;

    public RunMove(View view , int x, int y) {
        super(view);
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
