package model.events;

import view.PlayerView;

public class NameSetEvent extends Event {

    private final String name;

    public NameSetEvent(PlayerView playerView, String name){
        super(playerView);
        this.name = name;
    }

    public String getName(){
        return name;
    }

}
