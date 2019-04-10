package model.map_package;

import model.adrenaline_exceptions.WallException;
import model.player_package.Figure;

public interface MapElement {
    MapElement enter(Figure figure) throws WallException;



}
