package model.map_package;

import model.adrenaline_exceptions.WallException;
import model.player_package.Figure;

public class Wall implements MapElement{
    public Wall enter(Figure figure) throws WallException{
        throw new WallException();
    }
}

