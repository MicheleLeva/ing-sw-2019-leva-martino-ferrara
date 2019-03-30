package Model;

public class ActionBoard {

    private final PlayerBoard playerBoard;

    private Action action1;
    private Action action2;
    private Action action3;
    private Action action4;

    public void useAction1(){
        action1.useAction(playerBoard);
    }

    public void useAction2(){
        action2.useAction(playerBoard);
    }

    public void useAction3(){
        action3.useAction(playerBoard);
    }

    public void useAction4(){
        action4.useAction(playerBoard);
    }

    public ActionBoard(PlayerBoard playerBoard){
        this.playerBoard = playerBoard;
    }

    public void setAction1(Action action1) {
        this.action1 = action1;
    }

    public void setAction2(Action action2) {
        this.action2 = action2;
    }

    public void setAction3(Action action3) {
        this.action3 = action3;
    }

    public void setAction4(Action action4) {
        this.action4 = action4;
    }
}
