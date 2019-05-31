package model;

import controller.Checks;
import model.cards.*;
import model.cards.powerups.PowerUp;
import model.cards.powerups.TagbackGrenade;
import model.cards.weapons.FireMode;
import model.cards.weapons.Weapon;
import model.cards.powerups.TargetingScope;
import model.cards.weapons.*;
import model.cards.weapons.WeaponTreeNode;
import model.map.Direction;
import model.map.*;
import model.notifiers.ActionNotifier;
import model.notifiers.GameNotifier;
import model.notifiers.PowerUpNotifier;
import model.notifiers.WeaponNotifier;
import model.player.DamageCounter;
import model.player.MarkCounter;
import model.player.Player;
import model.player.PlayerColor;

import java.util.*;

import model.turn.CurrentTurn;
import model.turn.TurnManager;

public class Model {

    private final HashMap<PlayerColor, Player> players = new HashMap<>();

    private GameBoard gameBoard;

    private final TurnManager turnManager;

    private CurrentTurn currentTurn = null;

    private GameNotifier gameNotifier;

    private PowerUpNotifier powerUpNotifier;

    private ActionNotifier actionNotifier;

    private Current current;

    private WeaponNotifier weaponNotifier;

    private ScoreManager scoreManager;

    public WeaponNotifier getWeaponNotifier() {
        return weaponNotifier;
    }

    public GameNotifier getGameNotifier() {
        return gameNotifier;
    }

    public ActionNotifier getActionNotifier() {
        return actionNotifier;
    }

    public PowerUpNotifier getPowerUpNotifier() {
        return powerUpNotifier;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public Player getPlayer(PlayerColor playerColor) {
        return players.get(playerColor);
    }

    public Model(ArrayList<Player> playersList, int skulls) {

        players.put(PlayerColor.BLUE, playersList.get(0));
        players.put(PlayerColor.GREEN, playersList.get(1));
        players.put(PlayerColor.PURPLE, playersList.get(2));
        if (playersList.size() == 4) {
            players.put(PlayerColor.YELLOW, playersList.get(3));
        }
        if (playersList.size() == 5) {
            players.put(PlayerColor.GREY, playersList.get(4));
        }

        gameBoard = new GameBoard(playersList.size(), skulls);

        turnManager = new TurnManager(playersList);

        currentTurn = new CurrentTurn(this);

        this.gameNotifier = new GameNotifier();
        this.actionNotifier = new ActionNotifier();
        this.powerUpNotifier = new PowerUpNotifier();
        this.weaponNotifier = new WeaponNotifier();
    }


    public CurrentTurn getCurrentTurn() {
        return (currentTurn);
    }

    public TurnManager getTurnManager() {
        return turnManager;
    }

    public static ArrayList<Square> runnableSquare(int n, Square startingSquare) {

        ArrayList<Square> squares = new ArrayList<>();

        squares.add(startingSquare);

        if (n > 0) {
            if (startingSquare.getSide(Direction.NORTH) != null) {
                squares.addAll(runnableSquare(n - 1, startingSquare.getSide(Direction.NORTH)));
            }
            if (startingSquare.getSide(Direction.SOUTH) != null) {
                squares.addAll(runnableSquare(n - 1, startingSquare.getSide(Direction.SOUTH)));
            }
            if (startingSquare.getSide(Direction.EAST) != null) {
                squares.addAll(runnableSquare(n - 1, startingSquare.getSide(Direction.EAST)));
            }
            if (startingSquare.getSide(Direction.WEST) != null) {
                squares.addAll(runnableSquare(n - 1, startingSquare.getSide(Direction.WEST)));
            }
        }

        LinkedHashSet<Square> hashSet = new LinkedHashSet<>(squares); //converting to hashset to remove duplicates

        return new ArrayList<>(hashSet);

    }

    //Metodo che ritorna una lista dei giocatori
    //visibili a partire da uno square dato
    public ArrayList<Player> getVisiblePlayers(Player currentPlayer) {
        Square square = currentPlayer.getPosition();
        ArrayList<Player> visiblePlayers = new ArrayList<>();
        ArrayList<Player> allPlayers = getAllPlayers();
        for (Player player : allPlayers) {

            if (player.getPosition().getColor() == square.getColor() && currentPlayer != player)
                visiblePlayers.add(player);
            else
                for (Direction direction : Direction.values()) {
                    if (square.getSide(direction) != null && square.getSide(direction).getColor() != square.getColor() &&
                            square.getSide(direction).getColor() == player.getPosition().getColor())
                        visiblePlayers.add(player);
                }
        }
        return visiblePlayers;
    }

    public ArrayList<Square> getVisibleSquares(Player currentPlayer){
        Square currentSquare = currentPlayer.getPosition();
        ArrayList<Square> visibleSquares = new ArrayList<>();
        Square[][] allSquares = getGameBoard().getMap().getMap();

        for (int row = 0; row < allSquares.length; row++) {
            for (int col = 0; col < allSquares[row].length; col++) {
                if(currentSquare.getColor().equals(allSquares[row][col].getColor()))
                    visibleSquares.add(allSquares[row][col]);
                else
                    for (Direction direction : Direction.values()) {
                        if (    currentSquare.getSide(direction)!=null && currentSquare.getSide(direction).getColor()!=allSquares[row][col].getColor() &&
                                currentSquare.getSide(direction).getColor() == allSquares[row][col].getColor())
                            visibleSquares.add(allSquares[row][col]);
                    }
            }
        }
        return visibleSquares;
    }

    public ArrayList<Player> getPlayersNotInYourRoom(Player currentPlayer){
        ArrayList<Player> visiblePlayers = getVisiblePlayers(currentPlayer);
        ArrayList<Player> copy = new ArrayList<>(visiblePlayers);
        for (Player player : copy) {
            if (player.getPosition().getColor().equals(currentPlayer.getPosition().getColor()))
                visiblePlayers.remove(player);
        }
        return visiblePlayers;
    }

    public ArrayList<Square> getSquaresInCardinal2(Player currentPlayer) {
        ArrayList<Square> finalList = new ArrayList<>();
        finalList.add(currentPlayer.getPosition());
        for (Direction dir : Direction.values()) {
            if (currentPlayer.getPosition().getSide(dir) != null) {
                finalList.add(currentPlayer.getPosition().getSide(dir));
                if (currentPlayer.getPosition().getSide(dir).getSide(dir) != null)
                    finalList.add(currentPlayer.getPosition().getSide(dir).getSide(dir));
            }
        }
        return finalList;
    }

    public ArrayList<Square> getSquaresInCardinal1(Player currentPlayer) {
        ArrayList<Square> finalList = new ArrayList<>();
        finalList.add(currentPlayer.getPosition());
        for(Direction dir : Direction.values()) {
            if (currentPlayer.getPosition().getSide(dir) != null) {
                finalList.add(currentPlayer.getPosition().getSide(dir));
            }
        }
        return finalList;
    }

    //Metodo che ritorna una lista di giocatori
    //a distanza variabile dallo square scelto
    public ArrayList<Player> getPlayersAtDistance(int distance, Player currentPlayer) {
        Square square = currentPlayer.getPosition();
        ArrayList<Player> playersAtDistance = new ArrayList<>();
        ArrayList<Player> allPlayers = getAllPlayers();
        for (Player player : allPlayers) {
            if (runnableSquare(distance, square).contains(player.getPosition()) && player != currentPlayer) {
                playersAtDistance.add(player);
            }
        }
        return playersAtDistance;

    }

    public Square getSquareInSameDirection(Square currentSquare,Square targetSquare){
        Square finalSquare = null;
        for(Direction dir : Direction.values()) {
            if (currentSquare.getSide(dir) == targetSquare) {
                if (dir == Direction.NORTH)
                    if (targetSquare.getSide(Direction.NORTH) != null)
                        finalSquare = targetSquare.getSide(Direction.NORTH);
                if (dir == Direction.EAST)
                    if (targetSquare.getSide(Direction.EAST) != null)
                        finalSquare = targetSquare.getSide(Direction.EAST);
                if (dir == Direction.SOUTH)
                    if (targetSquare.getSide(Direction.SOUTH) != null)
                        finalSquare = targetSquare.getSide(Direction.SOUTH);
                if (dir == Direction.WEST)
                    if (targetSquare.getSide(Direction.WEST) != null)
                        finalSquare = targetSquare.getSide(Direction.WEST);
            }
        }
        return finalSquare;
    }

    public ArrayList<Player> getPlayersInSameDirection(Player currentPlayer,Player target){
        ArrayList<Player> finalPlayers = new ArrayList<>();
        Square currentSquare = currentPlayer.getPosition();
        Square targetSquare = target.getPosition();
        Square finalSquare = getSquareInSameDirection(currentSquare,targetSquare);
        for(Player player : getAllPlayers()){
            if(player.getPosition() == finalSquare)
                finalPlayers.add(player);
        }
        return finalPlayers;
    }

    public ArrayList<Player> getPlayersAtDistance(int distance, Player currentPlayer, Square square){
        ArrayList<Player> playersAtDistance = new ArrayList<>();
        ArrayList<Player> allPlayers = getAllPlayers();
        for(Player player : allPlayers) {
            if(runnableSquare(distance, square).contains(player.getPosition())&& player!=currentPlayer){
                playersAtDistance.add(player);
            }
        }
        return playersAtDistance;

    }


    public ArrayList<Player> getPlayersAtDistanceMore(int distance, Player currentPlayer){
        Square square = currentPlayer.getPosition();
        ArrayList<Player> playersAtDistanceMore;
        ArrayList<Player> playersAtDistance = getPlayersAtDistance(distance, currentPlayer);
        ArrayList<Player> allPlayers = getAllPlayers();
        for (Player player : playersAtDistance) {
            if (allPlayers.contains(player))
                allPlayers.remove(player);
        }
        if (allPlayers.contains(currentPlayer))
            allPlayers.remove(currentPlayer);
        return allPlayers;
    }

    //Metodo che ritorna una lista dei giocatori
    //lungo le direzioni cardinali dello square
    //dato
    public ArrayList<Player> getPlayersInCardinalDirection(Player currentPlayer) {
        Square square = currentPlayer.getPosition();
        ArrayList<Player> playersInCardinalDirection = new ArrayList<>();
        ArrayList<Player> allPlayers = getAllPlayers();

        for (Player player : allPlayers) {
            if (player.getPosition().getSquareRow() == square.getSquareRow() ||
                    player.getPosition().getSquareColumn() == square.getSquareColumn()) {
                playersInCardinalDirection.add(player);
            }
        }
        if (playersInCardinalDirection.contains(currentPlayer))
            playersInCardinalDirection.remove(currentPlayer);
        return playersInCardinalDirection;
    }

    public ArrayList<Player> getPlayersInSelectedCardinal(Player currentPlayer, char cardinal) {
        ArrayList<Player> allPlayers = getAllPlayers();
        ArrayList<Player> finalPlayers = new ArrayList<>();
        if (cardinal == 'n') {
            for (Player player : allPlayers)
                if (player.getPosition().getSquareColumn() == currentPlayer.getPosition().getSquareColumn() &&
                        player.getPosition().getSquareRow() < currentPlayer.getPosition().getSquareRow() && player != currentPlayer)
                    finalPlayers.add(player);
        }
        if (cardinal == 'e') {
            for (Player player : allPlayers)
                if (player.getPosition().getSquareRow() == currentPlayer.getPosition().getSquareRow() &&
                        player.getPosition().getSquareColumn() > currentPlayer.getPosition().getSquareColumn() &&
                        player != currentPlayer)
                    finalPlayers.add(player);
        }
        if (cardinal == 's') {
            for (Player player : allPlayers)
                if (player.getPosition().getSquareColumn() == currentPlayer.getPosition().getSquareColumn() &&
                        player.getPosition().getSquareRow() > currentPlayer.getPosition().getSquareRow() &&
                        player != currentPlayer)
                    finalPlayers.add(player);
        }
        if (cardinal == 'w') {
            for (Player player : allPlayers)
                if (player.getPosition().getSquareRow() == currentPlayer.getPosition().getSquareRow() &&
                        player.getPosition().getSquareColumn() < currentPlayer.getPosition().getSquareColumn() &&
                        player != currentPlayer)
                    finalPlayers.add(player);
        }
        if (cardinal == 'x') {
            finalPlayers = getPlayersInCardinalDirection(currentPlayer);
        }
        for (Player player : allPlayers) {
            if (player.getPosition() == currentPlayer.getPosition() && player != currentPlayer)
                finalPlayers.add(player);
        }
        return finalPlayers;
    }

    //Metodo che ritorna una lista dei giocatori
    //nello stesso square

    public ArrayList<Player> getPlayersInSameSquare(Player currentPlayer) {
        Square square = currentPlayer.getPosition();
        ArrayList<Player> playersInSameSquare = new ArrayList<>();
        ArrayList<Player> allPlayers = getAllPlayers();

        for (Player player : allPlayers) {
            if (player.getPosition().getSquareRow() == square.getSquareRow() &&
                    player.getPosition().getSquareColumn() == square.getSquareColumn() && currentPlayer != player) {
                playersInSameSquare.add(player);
            }
        }
        return playersInSameSquare;
    }

    //Metodo che ritorna una lista dei giocatori
    //NON visibili a partire da uno square dato
    public ArrayList<Player> getNonVisiblePlayers(Player currentPlayer) {
        ArrayList<Player> nonVisiblePlayers = new ArrayList<>();
        ArrayList<Player> allPlayers = getAllPlayers();

        for (Player player : allPlayers) {

            if (!(getVisiblePlayers(currentPlayer).contains(player)))
                nonVisiblePlayers.add(player);
        }
        if (nonVisiblePlayers.contains(currentPlayer))
            nonVisiblePlayers.remove(currentPlayer);
        return nonVisiblePlayers;

    }

    public void requestPowerUpDiscard(Player player) {
        String powerUpList = player.getResources().showpowerUp();
        int num = player.getResources().getPowerUp().size();
        if (num > 1) {
        }
        //todo notify
        else {
            discardPowerUp(player, 0);
        }
    }

    public void discardPowerUp(Player player, int index) {
        PowerUp discardedPowerUp = player.getResources().removePowerUp(index);
        gameBoard.getDecks().getDiscardedPowerUpDeck().add(discardedPowerUp);

        String toPlayer = "You discarded " + discardedPowerUp.toString();
        String toOthers = player.getPlayerName() + " discarded " + discardedPowerUp.toString();
        printMessage(player.getPlayerColor(), toPlayer, toOthers);

        spawnPlayer(player, discardedPowerUp.getAmmo());

        if (turnManager.getCurrentTurnNumber() == 1) {
            //todo notify();
        }


    }


    public void printMessage(PlayerColor playerColor, String toPlayer, String toOthers) {
        gameNotifier.notifyMessages(playerColor, toPlayer, toOthers);
    }

    public void spawnPlayer(Player player, AmmoColor ammoColor) {
        Square spawnSquare = gameBoard.getMap().getSpawnSquare(ammoColor);
        player.setPosition(spawnSquare);
        String toPlayer;
        String toOthers;

        toPlayer = "You just spawned at " + spawnSquare.toString();
        toOthers = player.getPlayerName() + " just spawned at " + spawnSquare.toString();
        printMessage(player.getPlayerColor(), toPlayer, toOthers);
    }

    private boolean canRecharge(Player player) {
        ArrayList<Weapon> reloadableWeapon = player.getResources().getReloadableWeapon();
        Ammo allAmmoAvailable = new Ammo(player.getResources().getAvailableAmmo());
        ArrayList<PowerUp> powerUp = player.getResources().getPowerUp();

        for (int i = 0; i < powerUp.size(); i++) {
            allAmmoAvailable.addAmmo(powerUp.get(i).getAmmo());
        }

        boolean result = false;
        //controlla nel ciclo while se getBaseCost è corretto
        for (int i = 0; i < reloadableWeapon.size(); i++) {
            while (!result) {
                if (allAmmoAvailable.isEnough(reloadableWeapon.get(i).getBaseCost())) {
                    result = true;
                }
            }
        }

        return result;
    }

    public void endTurn(Player player) {
        //2 casi:
        //1. piò ricaricare
        //2: non può      ricordarsi di notify

        if (canRecharge(player)) {
            //todo
        }

        String toPlayer;
        String toOthers;
        toPlayer = "Your turn has ended\n";
        toOthers = player.getPlayerName() + "'s turn has ended\n";
        //todo notify
        turnManager.update();

    }

    public void endFrenzyTurn(Player player) {
        String toPlayer;
        String toOthers;
        toPlayer = "Your frenzy turn has ended\n";
        toOthers = player.getPlayerName() + "'s frenzy turn has ended\n";
        //notify (new Message(player.getPlayerColor() , toPlayer , toOthers));

        if (player.getPlayerColor() == turnManager.getLastPlayerColor()) {
            turnManager.setGameOver(true);
        } else {
            turnManager.update();
        }
    }

    public void endGame() {
        //Calcolo classifica
        //Notifica

        ArrayList<Player> rank = new ArrayList<>();

        ArrayList<Player> allPlayer = turnManager.getAllPlayers();

        for (int i = 0; i < allPlayer.size(); i++) {
            //todo classifica
        }


    }


    public void updateRun() {
        Player currentPlayer = turnManager.getCurrentPlayer();
        PlayerColor currentPlayerColor = currentPlayer.getPlayerColor();
        gameNotifier.notifyRun(currentPlayerColor, currentPlayer.getPlayerName(), currentPlayer.getPosition().toString());
    }

    public void updateAction() {
        Player currentPlayer = turnManager.getCurrentPlayer();
        currentPlayer.getActionTree().updateAction();
    }

    public void updateTurn() { //todo controllare se serve ancora
        Player currentPlayer = turnManager.getCurrentPlayer();
        if (currentPlayer.getActionTree().isTurnEnded()) {
            ArrayList<Weapon> reloadableWeapon = currentPlayer.getResources().getReloadableWeapon();
            Ammo allAmmo = currentPlayer.getResources().getAllAmmo();
            if (Checks.canReload(reloadableWeapon, allAmmo)) {
                //chiedi di ricaricare
                askReloadEndTurn(currentPlayer.getPlayerColor());
            } else {
                endTurn();
            }
        } else {
            chooseAction(currentPlayer.getPlayerColor());
        }
    }

    public void endTurn() { //todo riempire metodo
        //cambia giocatore
        //notifica nuovo turno
        //stampa lo stato del gioco
        //calcola punteggi
        //ridistribuisci le ammo sulla mappa
    }

    public void choosePowerUp(PlayerColor playerColor) {
        String availablePowerUp;
        availablePowerUp = getPlayer(playerColor).getResources().showpowerUp();
        powerUpNotifier.choosePowerUp(playerColor, availablePowerUp);
    }

    public void useTeleporter(PlayerColor playerColor) {
        powerUpNotifier.chooseTeleporterSquare(playerColor);
    }

    public void notifyTeleporter(PlayerColor playerColor) {
        String playerName = getPlayer(playerColor).getPlayerName();
        String newSquare = getPlayer(playerColor).getPosition().toString();
        gameNotifier.notifyTeleporter(playerColor, playerName, newSquare);
    }

    public void chooseAction(PlayerColor playerColor) {
        Player currentPlayer = getPlayer(playerColor);
        String availableAction = currentPlayer.getActionTree().availableAction();
        currentPlayer.getActionTree().setMoveEnded(false);
        actionNotifier.chooseAction(playerColor, availableAction);

    }

    public Current getCurrent() {
        return current;
    }

    public void useNewton(PlayerColor playerColor) {
        ArrayList<Player> allPLayers = turnManager.getAllPlayers();
        String opponentList = "";
        for (int i = 0; i < allPLayers.size(); i++) {
            if (allPLayers.get(i).getPlayerColor() != playerColor) {
                current.addOpponent(allPLayers.get(i));
                opponentList = opponentList + allPLayers.get(i).getPlayerName() + " ";
            }
        }
        powerUpNotifier.chooseNewtonOpponent(playerColor, opponentList);
    }

    public void chooseNewtonSquare(PlayerColor playerColor, Player opponent) {
        Square currentOpponentSquare = opponent.getPosition();
        ArrayList<Square> possibleSquare = runnableSquare(2, currentOpponentSquare);
        current.setSquare(possibleSquare);
        String squareList = "";
        for (int i = 0; i < possibleSquare.size(); i++) {
            squareList = squareList + possibleSquare.get(i).getID() + " ";
        }
        powerUpNotifier.chooseNewtonSquare(playerColor, squareList);
    }

    public void notifyNewton(PlayerColor playerColor, Player opponent) {
        String playerName = getPlayer(playerColor).getPlayerName();
        String opponentName = opponent.getPlayerName();
        String newSquare = opponent.getPosition().toString();
        PlayerColor opponentColor = opponent.getPlayerColor();
        gameNotifier.notifyNewton(playerName, opponentName, playerColor, opponentColor, newSquare);
    }

    public void showWeaponCards(PlayerColor playerColor) {
        String availableWeapons;
        availableWeapons = getPlayer(playerColor).getResources().showWeapon();
        weaponNotifier.showWeaponCards(playerColor, availableWeapons);
    }

    public void selectTargets(PlayerColor playerColor, ArrayList<Player> availableTargets, int targetsNumber) {
        String opponentList = "";
        for (int i = 0; i < availableTargets.size(); i++) {
            if (availableTargets.get(i).getPlayerColor() != playerColor) {
                current.addOpponent(availableTargets.get(i));
                opponentList = opponentList + availableTargets.get(i).getPlayerName() + " ";
            }
        }
        weaponNotifier.selectTargets(playerColor, opponentList, targetsNumber);
    }

    public void showFireModes(PlayerColor playerColor, Weapon weapon) {
        List<WeaponTreeNode<FireMode>> FireModes = new ArrayList<>();
        List<WeaponTreeNode<FireMode>> availableFireModes = weapon.getWeaponTree().getLastActionPerformed().getChildren();
        for(WeaponTreeNode<FireMode> fireMode : availableFireModes){
            if(Checks.canUseFireMode(getPlayer(playerColor),weapon, fireMode.getData().getType()))
                FireModes.add(fireMode);
        }
        getCurrent().setAvailableFireModes(availableFireModes);
        String result = "Your available fire modes: \n";
        for (WeaponTreeNode<FireMode> child : availableFireModes) {
            result = result + child.getData().getEffectName();
        }
        weaponNotifier.showFireModes(playerColor, result);
    }

    public void askReloadPayment(Player currentPlayer, Weapon weapon){
        Ammo fireModeCost;
        int fireRED;
        int fireBLUE;
        int fireYELLOW;

        fireModeCost = setFireCost("reload",weapon);

        fireRED = fireModeCost.getRed();
        fireBLUE = fireModeCost.getBlue();
        fireYELLOW = fireModeCost.getYellow();

        if(currentPlayer.getResources().getPowerUp().isEmpty()){
            payReload(currentPlayer,weapon);
            return;
        }

        for(PowerUp powerUp : currentPlayer.getResources().getPowerUp()){
            if (fireRED > 0 && powerUp.getAmmo().toString().equals("RED"))
                getCurrent().addAvailablePaymentPowerUps(powerUp);
            else
            if(fireBLUE > 0 && powerUp.getAmmo().toString().equals("BLUE"))
                getCurrent().addAvailablePaymentPowerUps(powerUp);
            else
            if(fireYELLOW > 0 && powerUp.getAmmo().toString().equals("YELLOW"))
                getCurrent().addAvailablePaymentPowerUps(powerUp);
        }
        weaponNotifier.askReloadPayment(currentPlayer.getPlayerColor(),currentPlayer.getResources().getPowerUp());
    }

    public void payReload(Player currentPlayer, Weapon weapon){
        int powerUpRED = 0;
        int powerUpBLUE = 0;
        int powerUpYELLOW = 0;
        Ammo fireModeCost = weapon.getBaseCost();
        int fireRED = fireModeCost.getRed();
        int fireBLUE = fireModeCost.getBlue();
        int fireYELLOW = fireModeCost.getYellow();
        ArrayList<PowerUp> powerUps = current.getSelectedPaymentPowerUps();

        for(PowerUp powerUp : powerUps){
            if (powerUp.getAmmo().toString().equals("RED"))
                powerUpRED++;
            else
            if(powerUp.getAmmo().toString().equals("BLUE"))
                powerUpBLUE++;
            else
            if(powerUp.getAmmo().toString().equals("YELLOW"))
                powerUpYELLOW++;
        }
        fireRED = fireRED-powerUpRED;
        fireBLUE = fireBLUE-powerUpBLUE;
        fireYELLOW = fireYELLOW-powerUpYELLOW;
        currentPlayer.getResources().removeFromAvailableAmmo(new Ammo(fireRED,fireBLUE,fireYELLOW));
        current.resetCurrent();
        getCurrent().setReceivedInput(true);
    }

    public void askPickUpPayment(Player currentPlayer, Weapon weapon){
        Ammo fireModeCost;
        int fireRED;
        int fireBLUE;
        int fireYELLOW;

        fireModeCost = setFireCost("pickup",weapon);

        fireRED = fireModeCost.getRed();
        fireBLUE = fireModeCost.getBlue();
        fireYELLOW = fireModeCost.getYellow();

        if(currentPlayer.getResources().getPowerUp().isEmpty()){
            payPickUp(currentPlayer,weapon);
            return;
        }

        for(PowerUp powerUp : currentPlayer.getResources().getPowerUp()){
            if (fireRED > 0 && powerUp.getAmmo().toString().equals("RED"))
                getCurrent().addAvailablePaymentPowerUps(powerUp);
            else
            if(fireBLUE > 0 && powerUp.getAmmo().toString().equals("BLUE"))
                getCurrent().addAvailablePaymentPowerUps(powerUp);
            else
            if(fireYELLOW > 0 && powerUp.getAmmo().toString().equals("YELLOW"))
                getCurrent().addAvailablePaymentPowerUps(powerUp);
        }
        weaponNotifier.askPickUpPayment(currentPlayer.getPlayerColor(),currentPlayer.getResources().getPowerUp());
    }

    public void payPickUp(Player currentPlayer, Weapon weapon){
        int powerUpRED = 0;
        int powerUpBLUE = 0;
        int powerUpYELLOW = 0;
        Ammo fireModeCost = weapon.getBaseCost();
        int fireRED = fireModeCost.getRed();
        int fireBLUE = fireModeCost.getBlue();
        int fireYELLOW = fireModeCost.getYellow();
        ArrayList<PowerUp> powerUps = current.getSelectedPaymentPowerUps();

        for(PowerUp powerUp : powerUps){
            if (powerUp.getAmmo().toString().equals("RED"))
                powerUpRED++;
            else
            if(powerUp.getAmmo().toString().equals("BLUE"))
                powerUpBLUE++;
            else
            if(powerUp.getAmmo().toString().equals("YELLOW"))
                powerUpYELLOW++;
        }
        fireRED = fireRED-powerUpRED;
        fireBLUE = fireBLUE-powerUpBLUE;
        fireYELLOW = fireYELLOW-powerUpYELLOW;
        currentPlayer.getResources().removeFromAvailableAmmo(new Ammo(fireRED,fireBLUE,fireYELLOW));
        current.resetCurrent();
        //todo richiedi azione dopo aver ricaricato;
    }



    public void payFireMode(Player currentPlayer, Weapon weapon){
        int powerUpRED = 0;
        int powerUpBLUE = 0;
        int powerUpYELLOW = 0;
        Ammo fireModeCost;
        int fireRED ;
        int fireBLUE ;
        int fireYELLOW ;
        ArrayList<PowerUp> powerUps = current.getSelectedPaymentPowerUps();
        String effectType = weapon.getWeaponTree().getLastAction().getData().getType();
        switch (effectType) {
            case "alternative": {
                fireModeCost = ((WeaponAlternative) weapon).getAlternativeCost();
                break;
            }
            case "optional1": {
                fireModeCost = ((WeaponOptional1) weapon).getOptionalCost1();
                break;
            }
            case "optional2": {
                fireModeCost = ((WeaponOptional2) weapon).getOptionalCost2();
                break;
            }
            default: fireModeCost = new Ammo(0,0,0);
        }
            fireRED = fireModeCost.getRed();
            fireBLUE = fireModeCost.getBlue();
            fireYELLOW = fireModeCost.getYellow();

        for(PowerUp powerUp : powerUps){
            if (powerUp.getAmmo().toString().equals("RED"))
                powerUpRED++;
            else
            if(powerUp.getAmmo().toString().equals("BLUE"))
                powerUpBLUE++;
            else
            if(powerUp.getAmmo().toString().equals("YELLOW"))
                powerUpYELLOW++;
            }
        fireRED = fireRED-powerUpRED;
        fireBLUE = fireBLUE-powerUpBLUE;
        fireYELLOW = fireYELLOW-powerUpYELLOW;
        currentPlayer.getResources().removeFromAvailableAmmo(new Ammo(fireRED,fireBLUE,fireYELLOW));
        current.getSelectedPaymentPowerUps().clear();
        current.getAvailablePaymentPowerUps().clear();
    }

    public void askFireModePayment(Player currentPlayer,Weapon weapon,String effectType){
        Ammo fireModeCost;
        int fireRED;
        int fireBLUE;
        int fireYELLOW;
        fireModeCost = setFireCost(effectType,weapon);

        fireRED = fireModeCost.getRed();
        fireBLUE = fireModeCost.getBlue();
        fireYELLOW = fireModeCost.getYellow();

        if(currentPlayer.getResources().getPowerUp().isEmpty()){
            switch (effectType){
                case("alternative"):
                    ((WeaponAlternative)weapon).askAlternativeRequirements(currentPlayer);
                    break;
                case("optional1"):
                    ((WeaponOptional1)weapon).askOptionalRequirements1(currentPlayer);
                    break;
                case("optional2"):
                    ((WeaponOptional2)weapon).askOptionalRequirements2(currentPlayer);
                    break;
            }
            return;
        }

        for(PowerUp powerUp : currentPlayer.getResources().getPowerUp()){
            if (fireRED > 0 && powerUp.getAmmo().toString().equals("RED"))
                getCurrent().addAvailablePaymentPowerUps(powerUp);
            else
                if(fireBLUE > 0 && powerUp.getAmmo().toString().equals("BLUE"))
                    getCurrent().addAvailablePaymentPowerUps(powerUp);
                else
                    if(fireYELLOW > 0 && powerUp.getAmmo().toString().equals("YELLOW"))
                        getCurrent().addAvailablePaymentPowerUps(powerUp);
        }


        weaponNotifier.askWeaponPayment(currentPlayer.getPlayerColor(),getCurrent().getAvailablePaymentPowerUps());
    }

    public Ammo setFireCost(String effectType,Weapon weapon){
        Ammo fireModeCost;
        switch(effectType){

            case "reload":{
                fireModeCost = weapon.getBaseCost();
                break;
            }
            case "pickup":{
                fireModeCost = weapon.getPickUpCost();
                break;
            }
            case "alternative":{
                fireModeCost = ((WeaponAlternative)weapon).getAlternativeCost();
                break;
            }
            case "optional1":{
                fireModeCost = ((WeaponOptional1)weapon).getOptionalCost1();
                break;
            }
            case "optional2":{
                fireModeCost = ((WeaponOptional2)weapon).getOptionalCost2();
                break;
            }
            default: fireModeCost = new Ammo(0,0,0);
        }

        return fireModeCost;
    }


    public void chooseWeaponSquare(PlayerColor playerColor, ArrayList<Square> squares) {
        current.setAvailableWeaponSquares(squares);
        weaponNotifier.chooseWeaponSquare(playerColor, squares);
    }

    public void notifyShoot(Player currentPlayer, ArrayList<Player> targets) {
        Set<Player> set = new LinkedHashSet<Player>(current.getSelectedBaseTargets());
        set.addAll(current.getSelectedAlternativeTargets());
        set.addAll(current.getSelectedOptionalTargets1());
        set.addAll(current.getSelectedOptionalTargets2());
        targets = new ArrayList<>(set);
        ArrayList<Player> allPlayers = turnManager.getAllPlayers();
        gameNotifier.notifyShoot(currentPlayer, targets, allPlayers);
    }

    public void checkNextWeaponAction(Weapon weapon, Player currentPlayer, ArrayList<Player> selectedTargets) {
        weapon.getWeaponTree().updateLastActionPerformed();
        if (weapon.getWeaponTree().isActionEnded()) {
            weapon.getWeaponTree().resetAction();
            powerUpNotifier.askTargetingScope(currentPlayer.getPlayerColor());
        }
        else
            weapon.getModel().showFireModes(currentPlayer.getPlayerColor(), weapon);
    }

    public void discardAmmo(Square square) {
        AmmoCard discardedAmmo = square.getAmmoCard();
        square.removeAmmoCard();
        gameBoard.getDecks().getDiscardedAmmoCardDeck().add(discardedAmmo);
    }

    public void drawPowerUp(PlayerColor playerColor, int num) {
        Player currentPlayer = getPlayer(playerColor);
        ArrayList<PowerUp> drawnPowerUp = new ArrayList<>();
        String powerUpList = "";
        for (int i = 0; i < num; i++) {
            PowerUp powerUp = gameBoard.getDecks().drawPowerUp();
            drawnPowerUp.add(powerUp);
            powerUpList = powerUpList + powerUp.toString() + " ";
        }
        currentPlayer.getResources().addPowerUp(drawnPowerUp);


        gameNotifier.notifyDrawPowerUp(playerColor, currentPlayer.getPlayerName(), powerUpList, num);

    }

    public void addAmmo(PlayerColor playerColor, Ammo ammo) {
        Player currentPlayer = getPlayer(playerColor);
        currentPlayer.getResources().addAmmo(ammo);
        gameNotifier.notifyDrawAmmo(playerColor, currentPlayer.getPlayerName(), ammo.toString());
    }

    public void askReloadEndTurn(PlayerColor playerColor) {
        Player currentPlayer = getPlayer(playerColor);
        if (!currentPlayer.getResources().getReloadableWeapon().isEmpty()){
            String reloadableWeapon = currentPlayer.getResources().showReloadableWeapon();
            weaponNotifier.askReload(playerColor, reloadableWeapon);
        } else {
            getCurrent().setFinishedReloading(true);
            getCurrent().setReceivedInput(true);
        }
    }

    public void requestWeaponReload(PlayerColor playerColor) {
        Player currentPlayer = getPlayer(playerColor);
        ArrayList<Weapon> reloadableWeapon = currentPlayer.getResources().getReloadableWeapon();
        current.setReloadableWeapon(reloadableWeapon);
        String reloadableWeaponList = currentPlayer.getResources().showReloadableWeapon();
        String availableAmmoList = currentPlayer.getResources().getAvailableAmmo().toString();
        String availablePowerUpList = currentPlayer.getResources().showpowerUp();

        weaponNotifier.requestWeaponReload(playerColor, reloadableWeaponList, availableAmmoList, availablePowerUpList);
    }

    public void addDamage(PlayerColor shooterColor, PlayerColor opponentColor, int damage) { //todo aggiungere anche i marchi
        Player opponent = getPlayer(opponentColor);
        int opponentDamage = opponent.getPlayerBoard().getDamageCounter().getDamage();
        int givenDamage = Checks.givenDamage(opponentDamage, damage);
        DamageCounter damageCounter = opponent.getPlayerBoard().getDamageCounter();

        if (givenDamage != 0) {
            damageCounter.addDamage(shooterColor, givenDamage);
            if (damageCounter.getDamage() >= Checks.getKillshot()) {
                opponent.setKillshot();

                if(damageCounter.getDamage() == Checks.getKillshot()) {
                    turnManager.addKillShot(shooterColor);
                    }
                }

                if (damageCounter.getDamage() == Checks.getMaxDamage()) {
                    opponent.setDead();
                }
            }
        if (getPlayer(opponentColor).hasTagBackGrenade()) {
            getCurrent().getGrenadePeopleArray().add(getPlayer(opponentColor));
        }
        }


    public void addMark(PlayerColor shooterColor, PlayerColor opponentColor, int mark) {
        Player opponent = getPlayer(opponentColor);
        MarkCounter markCounter = opponent.getPlayerBoard().getMarkCounter();
        int opponentMark = markCounter.getMarkFromColor(shooterColor);
        int givenMark = Checks.givenMark(opponentMark, mark);
        if (givenMark != 0) {
            markCounter.addMarks(shooterColor, givenMark);
        }
    }

    //va chiamato sempre alla fine del turno su tutti i giocatori colpiti dopo aver valutato i marchi e i giocatori morti
    public void verifyNewAction(PlayerColor opponentColor) {
        Player player = getPlayer(opponentColor);
        int currentTreeID = player.getActionTree().getID();
        int newTreeID = Checks.verifyNewAction(player);
        if (currentTreeID != newTreeID) {
            player.setActionTree(newTreeID);
            //notifica al giocatore(?)
        }
    }


    public ArrayList<Player> getAllPlayers() {
        ArrayList<Player> def = new ArrayList<Player>(players.values());
        return def;
    }

    public void targetingScopeTargets(PlayerColor playerColor, ArrayList<Player> damagedPlayers){
        String message="Select Targeting Scope target: ";
        for(Player player : damagedPlayers)
            message = message + player.getPlayerName();
        powerUpNotifier.targetingScopeTargets(playerColor,message);
    }

    public void setPlayerAfk(Player player){
        player.setAfk(true);
        getActionNotifier().setPlayerAfk(player.getPlayerColor());
    }

    public void wakeUpPlayer(Player player){
        player.setAfk(false);
    }

    public void tagbackGranadeRequest(PlayerColor playerColor, PlayerColor opponentColor){
        StringBuilder stringBuilder = new StringBuilder();
        int i = 1;
        for (PowerUp currentPowerUp : getPlayer(playerColor).getResources().getPowerUp()){
            if (currentPowerUp instanceof TagbackGrenade){
                stringBuilder.append(i);
                stringBuilder.append(": ");
                stringBuilder.append(currentPowerUp.toString());
                stringBuilder.append(" Ammo: ");
                stringBuilder.append(currentPowerUp.getAmmo().toString());
                stringBuilder.append(".\n");
                i++;
            }
        }
        getPowerUpNotifier().askTagbackGrenade(playerColor, opponentColor, stringBuilder.toString());
    }

}


