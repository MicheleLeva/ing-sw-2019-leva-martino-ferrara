package Model;

public class PlayerBoard {

    private final Color color;

    private final Figure figure;

    private final ActionBoard actionBoard;

    private final Resources resources;

    public PlayerBoard (Color color){
        this.color = color;
        figure = new Figure (color);
        resources = new Resources();
        actionBoard = new ActionBoard(this);

    }

    public Color getColor() {
        return color;
    }

    public Figure getFigure() {
        return figure;
    }

    public ActionBoard getActionBoard() {
        return actionBoard;
    }

    public Resources getResources() {
        return resources;
    }

    public void switchActionBoardDamaged(){

    }

    public void switchActionBoardWounded(){

    }

    public void switchActionBoardFrenzy(){

    }
}
