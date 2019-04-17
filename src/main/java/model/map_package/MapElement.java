package model.map_package;

import model.adrenaline_exceptions.WallException;


public interface MapElement {
    MapElement enter() throws WallException;
}
