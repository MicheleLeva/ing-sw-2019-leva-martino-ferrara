package model;

import model.adrenaline_exceptions.EmptySquareException;
import model.adrenaline_exceptions.IllegalOpponentException;
import model.adrenaline_exceptions.InsufficientAmmoException;
import model.cards.AmmoCard;
import model.cards.AmmoColor;
import model.cards.PowerUp;
import model.cards.Weapon;
import model.events.message.*;
import model.events.message.Message;
import model.events.message.RunMessage;
import model.map_package.Direction;
import model.map_package.*;
import model.player_package.Player;
import model.player_package.PlayerColor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;

import org.omg.PortableServer.POA;
import utils.Observable;

public class Model extends Observable<Message> {

    private final HashMap<PlayerColor , Player> players = new HashMap<>();

    private GameBoard gameBoard;

    private final TurnManager turnManager;

    private final CurrentTurn currentTurn;

    private static Model modelInstance = null;


    private Model(){


    }

    public static Model getModelInstance(){
        if(modelInstance == null)
            modelInstance = new Model();
        return modelInstance;

    }

    public GameBoard getGameBoard(){
        return gameBoard;
    }

    public Player getPlayer(PlayerColor playerColor){
        return players.get(playerColor);
    }

    public void performRun(PlayerColor playerColor, int x, int y){
        Player player = getPlayer(playerColor);
        player.setPosition(gameBoard.getMap().getSquareFromCoordinates(x,y));
        notify(new RunMessage(playerColor, player.getPlayerName(), player.getPosition()));
    }

    public void performShoot(PlayerColor shooterColor, ArrayList<PlayerColor> opponentsColor, Weapon weapon,int fireMode/*, PowerUp powerUp*/) throws IllegalOpponentException {
        //Need weapon cards
        //notify
        ArrayList<Player> selected = new ArrayList<>();
        Player shooter = getPlayer(shooterColor);
        for(PlayerColor playercolor : opponentsColor )
            selected.add(getPlayer(playercolor));
        if (fireMode == 0)
            weapon.useBaseWeapon(shooter,selected,weapon);
        if (fireMode == 1)
            weapon.useOptionalWeapon1(shooter,selected,weapon);
        if (fireMode == 2)
            weapon.useOptionalWeapon2(shooter,selected,weapon);

        String resultString = "";
        ArrayList<String> names= new ArrayList<>();
        for (PlayerColor playerColor : opponentsColor)
            names.add(getPlayer(playerColor).getName());
        for(String name : names)
            resultString = resultString.concat(name+", ");

        notify(new ShootMessage(shooterColor,getPlayer(shooterColor).getName(),opponentsColor,resultString,weapon,gameBoard));
    }

    public void performGrab(PlayerColor playerColor) throws EmptySquareException {
        Player player = getPlayer(playerColor);

        if (player.getPosition().getAmmoCard() !=null){
            AmmoCard ammoCard = player.getPosition().getAmmoCard();
            //Need ammo cards
        }else throw new EmptySquareException();
        //notify
    }

    public void asktTeleporterCoordinates(){
        notify(new TeleporterMessage());
    }


    public void performTeleporterMove(PlayerColor playerColor, int row, int column){
        Player player = getPlayer(playerColor);
        player.setPosition(gameBoard.getMap().getSquareFromCoordinates(row,column));
        notify(new RunMessage(playerColor, player.getPlayerName(), player.getPosition()));

    }

    public void performReload(PlayerColor playerColor, int index) throws InsufficientAmmoException {
        //Need weapon cards
    }

    public void performDraw(PlayerColor playerColor){
        Player player = getPlayer(playerColor);
        PowerUp drawnCard = gameBoard.getDecks().drawPowerUp();
        player.getResources().addPowerUp(drawnCard);
        notify(new DrawMessage(playerColor, player.getPlayerName(), drawnCard));
    }

    public void performUsePowerUp(PlayerColor playerColor, int index){
        //Need powerup cards
    }

    public void performShowCards(PlayerColor playerColor){
        Player player = getPlayer(playerColor);
        notify(new ShowCardsMessage(playerColor, player.getPlayerName(), player.getResources().showPowerUps(),
                player.getResources().showWeapons()));
    }

    public void performShowTargetsMove(PlayerColor playerColor,int weaponIndex,int fireModeIndex){

        Player player = getPlayer(playerColor);
        Weapon weapon = player.getResources().showWeapons().get(weaponIndex);
        notify(new ShowTargetsMessage(weapon.getAvailableTargets(player.getPosition(),fireModeIndex),
               weapon, weapon.getTargetsNumber()[fireModeIndex], fireModeIndex));

    }



    public Model(ArrayList<Player> playersList, int skulls){

        players.put(PlayerColor.BLUE, playersList.get(0));
        players.put(PlayerColor.GREEN, playersList.get(1));
        players.put(PlayerColor.PURPLE, playersList.get(2));
        if (playersList.size()== 4){
            players.put(PlayerColor.YELLOW, playersList.get(3));
        }
        if (playersList.size()== 5){
            players.put(PlayerColor.GREY, playersList.get(4));
        }

        gameBoard = new GameBoard(playersList.size(), skulls);

        turnManager = new TurnManager(playersList);

        currentTurn = new CurrentTurn(this);


    }

    public CurrentTurn getCurrentTurn(){
        return (currentTurn);
    }

    public void addDamage(PlayerColor playerColor){
        //addDamage
        //controllo per la granata
        //notify al player se c'è
        //
    }

    public TurnManager getTurnManager(){
        return turnManager;
    }

    public static ArrayList<Square> runnableSquare(int n, Square startingSquare){

        ArrayList<Square> squares = new ArrayList<>();

        squares.add(startingSquare);

        if (n>0){
            if (getSquareFromDir(startingSquare, Direction.NORTH) != null){
                squares.addAll(runnableSquare(n-1, getSquareFromDir(startingSquare, Direction.NORTH)));
            }
            if (getSquareFromDir(startingSquare, Direction.SOUTH) != null){
                squares.addAll(runnableSquare(n-1, getSquareFromDir(startingSquare, Direction.SOUTH)));
            }
            if (getSquareFromDir(startingSquare, Direction.EAST) != null){
                squares.addAll(runnableSquare(n-1, getSquareFromDir(startingSquare, Direction.EAST)));
            }
            if (getSquareFromDir(startingSquare, Direction.WEST) != null){
                squares.addAll(runnableSquare(n-1, getSquareFromDir(startingSquare, Direction.WEST)));
            }
        }

        LinkedHashSet<Square> hashSet = new LinkedHashSet<>(squares); //converting to hashset to remove duplicates

        return new ArrayList<>(hashSet);

    }

    private static Square getSquareFromDir(Square square, Direction direction) throws NullPointerException{

        MapElement side = square.getSide(direction);

        if (side instanceof Door) {
            return ((Door) side).enter();
        }
        if (side instanceof  Square){
            return (Square) side;
        }
        return null;
    }

    //Metodo che ritorna una lista dei giocatori
    //visibili a partire da uno square dato
    public ArrayList<Player> getVisiblePlayers(Square square){
        ArrayList<Player> visiblePlayers = new ArrayList<>();
        for(Player player : players.values()) {

            if (player.getPosition().getColor() == square.getColor())
                visiblePlayers.add(player);
            else
                for (Direction direction : Direction.values()) {
                    if (    square.getSide(direction) instanceof Door &&
                            getSquareFromDir(square, direction).getColor() == player.getPosition().getColor())

                            visiblePlayers.add(player);
                }
        }
        return visiblePlayers;
    }

    //Metodo che ritorna una lista dei giocatori
    //a distanza 0 o 1 da uno square dato
    /*public ArrayList<Player> getPlayersAtDistance1(Square square){
        ArrayList<Player> playersAtDistance1 = new ArrayList<>();
        for(Player player : players.values()) {
            for(Direction direction : Direction.values()){
                if(    !(square.getSide(direction) instanceof Wall) &&
                        player.getPosition() == square.getSide(direction).enter()) {

                        playersAtDistance1.add(player);
                }

                if(square == player.getPosition())
                    playersAtDistance1.add(player);

            }
        }
        return playersAtDistance1;
    }*/

    //Metodo che ritorna una lista di giocatori
    //a distanza variabile dallo square scelto
    public ArrayList<Player> getPlayersAtDistance(int distance, Square square){
        ArrayList<Player> playersAtDistance = new ArrayList<>();
        for(Player player : players.values()) {
            if(runnableSquare(distance, square).contains(player.getPosition())){
                playersAtDistance.add(player);
            }
        }
        return playersAtDistance;

    }

    //Metodo che ritorna una lista dei giocatori
    //lungo le direzioni cardinali dello square
    //dato
    public ArrayList<Player> getPlayersInCardinalDirection(Square square){
        ArrayList<Player> playersInCardinalDirection = new ArrayList<>();
        for(Player player : players.values()) {
            if( player.getPosition().getSquareRow() == square.getSquareRow() ||
                    player.getPosition().getSquareColumn() == square.getSquareColumn()){
                playersInCardinalDirection.add(player);
            }
        }
       return playersInCardinalDirection;
    }

    //Metodo che ritorna una lista dei giocatori
    //NON visibili a partire da uno square dato
    public ArrayList<Player> getNonVisiblePlayers(Square square){
        ArrayList<Player> nonVisiblePlayers = new ArrayList<>();

        for(Player player : players.values()) {

            if(!(getVisiblePlayers(square).contains(player)))
                nonVisiblePlayers.add(player);
        }
        return nonVisiblePlayers;

    }

    public void drawPowerUp(Player player , int num){
        ArrayList<PowerUp> drawnPowerUp = new ArrayList<>();
        //Draw 'num' PowerUps
        for (int i = 0; i < num; i++){
            drawnPowerUp.add(gameBoard.getDecks().drawPowerUp());
        }

        player.drawPowerUp(drawnPowerUp);
        notify(new DrawPowerUpMessage(player.getPlayerColor() , player.getPlayerName() , drawnPowerUp));
    }

    public void requestPowerUpDiscard(Player player){
        String powerUpList = player.getResources().showpowerUp();
        int num = player.getResources().getPowerUp().size();
        if (num > 1)
            notify(new PowerUpDiscardMessage(player.getPlayerColor() , player.getPlayerName() , powerUpList , num));
        else{
            discardPowerUp(player , 0);
        }
    }

    public void discardPowerUp(Player player , int index){
        PowerUp discardedPowerUp = player.getResources().removePowerUp(index);
        gameBoard.getDecks().getDiscardedPowerUpDeck().add(discardedPowerUp);

        String toPlayer = "You discarded " +discardedPowerUp.toString();
        String toOthers = player.getName() +" discarded " +discardedPowerUp.toString();
        printMessage(player.getPlayerColor() , toPlayer , toOthers);

        spawnPlayer(player , discardedPowerUp.getCost());
    }

    public void showPowerUp(PlayerColor playerColor){

    }

    public void printMessage(PlayerColor playerColor , String toPlayer , String toOthers){
        notify (new Message(playerColor , toPlayer , toOthers));
    }

    public void setPlayerPosition(Player player , Square square){
        player.setPosition(square);
        String toPlayer;
        String toOthers;

        toPlayer = "You just moved to " + square.toString();
        toOthers = player.getName() +" just moved to " +square.toString();
        printMessage(player.getPlayerColor() , toPlayer , toOthers);
    }

    public void spawnPlayer(Player player , AmmoColor ammoColor){
        Square spawnSquare = gameBoard.getMap().getSpawnSquare(ammoColor);
        player.setPosition(spawnSquare);
        String toPlayer;
        String toOthers;

        toPlayer = "You just spawned at " + spawnSquare.toString();
        toOthers = player.getName() +" just spawned at " +spawnSquare.toString();
        printMessage(player.getPlayerColor() , toPlayer , toOthers);
    }

    public void askTurnInput(PlayerColor playerColor){
        notify (new AskTurnInputMessage(playerColor));
    }

    public void showCards(PlayerColor playerColor){
        Player player = turnManager.getPlayerFromColor(playerColor);
        String powerUp = "PowerUp: " +player.getResources().showpowerUp();
        String weapon = "Weapon: " +player.getResources().showWeapon();
        String ammo = player.getResources().showAmmo();

        String cards = powerUp +"\n" + weapon +"\n" +ammo +"\n";

        notify(new Message(playerColor , cards , ""));

    }

    private boolean canRecharge(Player player){
        ArrayList<Weapon> reloadableWeapon = player.getResources().getReloadableWeapon();
        Ammo allAmmoAvailable = new Ammo(player.getResources().getAvailableAmmo());
        ArrayList<PowerUp> powerUp = player.getResources().getPowerUp();

        for (int i = 0; i < powerUp.size(); i++){
            allAmmoAvailable.addAmmo(powerUp.get(i).getCost());
        }

        boolean result = false;

        for (int i = 0; i < reloadableWeapon.size(); i++){
            while (!result){
                if (allAmmoAvailable.isEnough(reloadableWeapon.get(i).getReloadCost())){
                    result = true;
                }
            }
        }

        return result;
    }

    public void endTurn(Player player){
        //2 casi:
         //1. piò ricaricare
        //2: non può

        if (canRecharge(player)){

        }

        String toPlayer;
        String toOthers;
        toPlayer = "Your turn has ended\n";
        toOthers = player.getName() +"'s turn has ended\n";
        notify (new Message(player.getPlayerColor() , toPlayer , toOthers));
        turnManager.update();

    }

    public void endFrenzyTurn(Player player){
        String toPlayer;
        String toOthers;
        toPlayer = "Your frenzy turn has ended\n";
        toOthers = player.getName() +"'s frenzy turn has ended\n";
        notify (new Message(player.getPlayerColor() , toPlayer , toOthers));

        if(player.getPlayerColor() == turnManager.getLastPlayerColor()){
            turnManager.setGameOver(true);
        }

        else{
            turnManager.update();
        }
    }

    public void endGame(){
        //Calcolo classifica
        //Notifica

        ArrayList<Player> rank = new ArrayList<>();

        ArrayList<Player> allPlayer = turnManager.getAllPlayers();

        for (int i = 0; i < allPlayer.size(); i++){
            addInRank(rank , allPlayer.get(i));
        }


    }

    private void addInRank(ArrayList<Player> rank , Player player){
        if (rank.isEmpty()){
            rank.add(player);
            return;
        }
        else{
            for (int i = 0; i < rank.size(); i++){
                if(player.getScore().getScore() > rank.get(i).getScore().getScore()){
                    rank.add(i , player);
                    return;
                }

                if(player.getScore() == rank.get(i).getScore()){
                    if (player.getScore().getNumKillShot() > rank.get(i).getScore().getNumKillShot()){
                        rank.add(i , player);
                        return;
                    }
                }
            }

            rank.add(player);
        }
    }
}
