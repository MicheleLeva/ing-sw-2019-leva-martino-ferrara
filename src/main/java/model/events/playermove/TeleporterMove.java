package model.events.playermove;

import view.View;

public class TeleporterMove extends PlayerMove {
    private int row;
    private int column;

    public TeleporterMove(View view, int row, int column){
        super(view);
        this.row = row;
        this.column = column;
    }

    public int getRow(){return row;}

    public int getColumn(){return column;}
}
