package model.events;

import model.Model;
import model.adrenaline_exceptions.WallException;
import model.map_package.Direction;
import view.View;


public class RunMove extends PlayerMove {

    private final Direction direction;

    public RunMove(View view , Direction direction){
        super(view);
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public void performMove(Model model) throws WallException {

    }

}
