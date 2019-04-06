package model.player_package;

public class Player {

    private final String playerName;
    private final PlayerColor playerColor;
    private final Resources resources;
    private final PlayerBoard playerBoard;
    private ActionBoard actionBoard;
    private final Figure figure;
    private Score score;

    public Player(String name, PlayerColor color, Resources res, PlayerBoard pboard, Figure fig){
        playerName = name;
        playerColor = color;
        resources = res;
        playerBoard = pboard;
        figure = fig;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    public PlayerBoard getPlayerBoard(){
        return playerBoard;
    }

    public ActionBoard getActionBoard(){
        return actionBoard;
    }

    public Figure getFigure(){
        return figure;
    }

    public Resources getResources(){
        return resources;
    }

    public PlayerColor getPlayerColor(){
        return playerColor;
    }

    public void setActionBoard(ActionBoard actionBoard) {
        this.actionBoard = actionBoard;
    }

    public void drawAmmoCard(){

    }

    public void drawWeaponCard(){

    }

    public void drawPowerUpCard(){

    }
}
