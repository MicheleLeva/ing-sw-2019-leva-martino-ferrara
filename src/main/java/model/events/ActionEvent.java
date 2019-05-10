package model.events;

import view.PlayerView;

public class ActionEvent extends Event {
    private final char input;

    public ActionEvent(PlayerView playerView , char input){
        super(playerView);
        this.input = input;
    }

    public char getInput(){
        return input;
    }
}
