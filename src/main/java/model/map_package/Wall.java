package model.map_package;

import model.adrenaline_exceptions.WallException;


public class Wall implements MapElement  {
    @Override
    public Wall enter() throws WallException {
        throw new WallException();

    }
    @Override
    public String toString() {
        return "wall";
    }
}


