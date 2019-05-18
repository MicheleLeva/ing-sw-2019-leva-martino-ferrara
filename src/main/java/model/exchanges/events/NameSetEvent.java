package model.exchanges.events;

import view.View;

public class NameSetEvent extends Event {

    private final String name;

    public NameSetEvent(View view, String name){
        super(view);
        this.name = name;
    }

    public String getName(){
        return name;
    }

}
