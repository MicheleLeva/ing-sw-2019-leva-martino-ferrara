package Model;

public class Player {

    private final String playerName;

    private final Color playerColor;

    private final PlayerBoard playerBoard;

    private void init(){
        playerBoard = new PlayerBoard(playerColor);
    }

    public Player (String name , Color color){
        playerName = name;
        playerColor = color;
        init();
    }




}
