package model.game;

import controller.Checks;
import model.cards.*;
import model.cards.powerups.PowerUp;
import model.cards.powerups.TagbackGrenade;
import model.cards.powerups.TargetingScope;
import model.cards.weapons.*;
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

import model.player.action.KeyMap;
import model.turn.Current;
import model.turn.TurnCurrent;
import model.turn.TurnManager;

public class Model {

    private final HashMap<PlayerColor, Player> players = new HashMap<>();

    private GameBoard gameBoard;

    private final TurnManager turnManager;

    private GameNotifier gameNotifier;

    private PowerUpNotifier powerUpNotifier;

    private ActionNotifier actionNotifier;

    private Current current;

    private TurnCurrent turnCurrent;

    private WeaponNotifier weaponNotifier;

    private ScoreManager scoreManager;

    private KeyMap keyMap;

    private ArrayList<Integer> mapVotes = new ArrayList<>();

    private int skulls;

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

    public ScoreManager getScoreManager(){
        return scoreManager;
    }

    public Player getPlayer(PlayerColor playerColor) {
        return players.get(playerColor);
    }

    /**
     * Constructor of the model class
     * @param playersList list of players of the game
     * @param skulls number of skulls on the killShotTrack
     */
    public Model(ArrayList<Player> playersList, int skulls) {
        for (Player player : playersList) {
            players.put(player.getPlayerColor(), player);
        }

        this.skulls = skulls;
        current = new Current();
        turnCurrent = new TurnCurrent();

        keyMap = new KeyMap();

        turnManager = new TurnManager(this,playersList);

        scoreManager = new ScoreManager(this);

        this.gameNotifier = new GameNotifier();
        this.actionNotifier = new ActionNotifier();
        this.powerUpNotifier = new PowerUpNotifier();
        this.weaponNotifier = new WeaponNotifier();
    }

    public void setGameBoard(int chosenMap) {
        this.gameBoard = new GameBoard(chosenMap, skulls, this);
    }

    public TurnManager getTurnManager() {
        return turnManager;
    }

    public TurnCurrent getTurnCurrent() {
        return turnCurrent;
    }

    /**
     * Returns a list of all the squares reachable from the startingSquare with n moves
     * @param n number of moves
     * @param startingSquare square from which the method calculates the distances
     * @return the list of reachable squares
     */
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


    /**
     * Method that returns a list of the players that are visible by the current player
     * @param currentPlayer current player
     */
    public ArrayList<Player> getVisiblePlayers(Player currentPlayer) {
        Square square = currentPlayer.getPosition();
        ArrayList<Player> visiblePlayers = new ArrayList<>();
        ArrayList<Player> allPlayers = getAllSpawnedPlayers();
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

    /**
     * Method that returns a list of the squares that are visible by the current player
     * @param currentPlayer current player
     */
    public ArrayList<Square> getVisibleSquares(Player currentPlayer){
        Square currentSquare = currentPlayer.getPosition();
        ArrayList<Square> visibleSquares = new ArrayList<>();
        Square[][] allSquares = getGameBoard().getMap().getMap();

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 4; col++) {
                if(allSquares[row][col]!=null && currentSquare.getColor().equals(allSquares[row][col].getColor()))
                    visibleSquares.add(allSquares[row][col]);
                else
                    for (Direction direction : Direction.values()) {
                        if (    allSquares[row][col]!=null &&
                                currentSquare.getSide(direction)!=null &&
                                currentSquare.getSide(direction).getColor().equals(allSquares[row][col].getColor()))
                            visibleSquares.add(allSquares[row][col]);
                    }
            }
        }
        return visibleSquares;
    }

    /**
     * Method that returns a list of the players that are not in the current player's room
     * @param currentPlayer current player
     */
    public ArrayList<Player> getPlayersNotInYourRoom(Player currentPlayer){
        ArrayList<Player> visiblePlayers = getVisiblePlayers(currentPlayer);
        ArrayList<Player> copy = new ArrayList<>(visiblePlayers);
        for (Player player : copy) {
            if (player.getPosition().getColor().equals(currentPlayer.getPosition().getColor()))
                visiblePlayers.remove(player);
        }
        return visiblePlayers;
    }

    /**
     * Method that returns a list of the squares at distance 2 in all cardinal directions
     * @param currentPlayer current player
     */
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

    /**
     * Method that returns a list of the squares at distance 1 in all cardinal directions
     * @param currentPlayer current player
     */
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


    /**
     * Method that returns a list of the visible players at a given distance
     * @param currentPlayer current player
     * @param distance maximum distance for the targets
     */
    public ArrayList<Player> getPlayersAtDistance(int distance, Player currentPlayer) {
        Square square = currentPlayer.getPosition();
        ArrayList<Player> playersAtDistance = new ArrayList<>();
        ArrayList<Player> allPlayers = new ArrayList<>(getAllSpawnedPlayers());
        for (Player player : allPlayers) {
            if (runnableSquare(distance, square).contains(player.getPosition()) && player != currentPlayer) {
                playersAtDistance.add(player);
            }
        }
        return playersAtDistance;

    }

    /**
     * Method that returns a list of the visible players at a given distance from a selected square
     * @param currentPlayer current player
     * @param distance maximum distance for the targets
     * @param square selected square to calculate the distance from
     */
    public ArrayList<Player> getPlayersAtDistance(int distance, Player currentPlayer, Square square){
        ArrayList<Player> playersAtDistance = new ArrayList<>();
        ArrayList<Player> allPlayers = getAllSpawnedPlayers();
        for(Player player : allPlayers) {
            if(runnableSquare(distance, square).contains(player.getPosition())&& player!=currentPlayer){
                playersAtDistance.add(player);
            }
        }
        return playersAtDistance;

    }



    /**
     * Method that returns a list of the visible players at a distance superior to the given one
     * @param currentPlayer current player
     * @param distance maximum distance for the targets
     */
    public ArrayList<Player> getPlayersAtDistanceMore(int distance, Player currentPlayer){
        ArrayList<Player> playersAtDistanceMore = new ArrayList<>();
        ArrayList<Player> playersAtDistance = getPlayersAtDistance(distance, currentPlayer);
        ArrayList<Player> allPlayers = new ArrayList<>(getAllSpawnedPlayers());
        for (Player player : allPlayers) {
            if (!playersAtDistance.contains(player))
                playersAtDistanceMore.add(player);
        }
        playersAtDistanceMore.remove(currentPlayer);

        return playersAtDistanceMore;
    }



    /**
     * Method that returns a list of the players in all cardinal directions
     * @param currentPlayer current player
     */
    public ArrayList<Player> getPlayersInCardinalDirection(Player currentPlayer) {
        Square square = currentPlayer.getPosition();
        ArrayList<Player> playersInCardinalDirection = new ArrayList<>();
        ArrayList<Player> allPlayers = new ArrayList<>(getAllSpawnedPlayers());

        for (Player player : allPlayers) {
            if (    player.getPosition()!=null &&
                    (player.getPosition().getSquareRow() == square.getSquareRow() ||
                    player.getPosition().getSquareColumn() == square.getSquareColumn())) {
                playersInCardinalDirection.add(player);
            }
        }
        playersInCardinalDirection.remove(currentPlayer);
        return playersInCardinalDirection;
    }


    /**
     * Method that returns a list of the visible players in a selected cardinal direction
     * @param currentPlayer current player
     * @param cardinal selected cardinal direction
     */
    public ArrayList<Player> getPlayersInSelectedCardinal(Player currentPlayer, char cardinal) {
        ArrayList<Player> allPlayers = new ArrayList<>(getAllSpawnedPlayers());
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


    /**
     * Method that returns a list of the players in the same square of the current player
     * @param currentPlayer current player
     */
    public ArrayList<Player> getPlayersInSameSquare(Player currentPlayer) {
        Square square = currentPlayer.getPosition();
        ArrayList<Player> playersInSameSquare = new ArrayList<>();
        ArrayList<Player> allPlayers = getAllSpawnedPlayers();

        for (Player player : allPlayers) {
            if (player.getPosition().getSquareRow() == square.getSquareRow() &&
                    player.getPosition().getSquareColumn() == square.getSquareColumn() && currentPlayer != player) {
                playersInSameSquare.add(player);
            }
        }
        return playersInSameSquare;
    }


    /**
     * Method that returns a list of the players that are not visible from the current player
     * @param currentPlayer current player
     */
    public ArrayList<Player> getNonVisiblePlayers(Player currentPlayer) {
        ArrayList<Player> nonVisiblePlayers = new ArrayList<>();
        ArrayList<Player> allPlayers = getAllSpawnedPlayers();

        for (Player player : allPlayers) {

            if (!(getVisiblePlayers(currentPlayer).contains(player)))
                nonVisiblePlayers.add(player);
        }
        nonVisiblePlayers.remove(currentPlayer);
        return nonVisiblePlayers;
    }

    /**
     * Method that returns the next square for the power glove weapon
     */
    public Square getNextPowerGloveSquare(Square playerSquare, Square secondSquare){
        if(playerSquare.getSquareRow()==secondSquare.getSquareRow()) {
            if (playerSquare.getSquareColumn() > secondSquare.getSquareColumn())
                if (secondSquare.getSide(Direction.WEST) != null)
                    return secondSquare.getSide(Direction.WEST);
            if (playerSquare.getSquareColumn() < secondSquare.getSquareColumn())
                if (secondSquare.getSide(Direction.EAST) != null)
                    return secondSquare.getSide(Direction.EAST);
        }
        if (playerSquare.getSquareColumn() == secondSquare.getSquareColumn()) {
            if (playerSquare.getSquareRow() > secondSquare.getSquareRow())
                if (secondSquare.getSide(Direction.NORTH) != null)
                    return secondSquare.getSide(Direction.NORTH);
                if (playerSquare.getSquareRow() < secondSquare.getSquareRow())
                    if (secondSquare.getSide(Direction.SOUTH) != null)
                        return secondSquare.getSide(Direction.SOUTH);
            }
        return null;

    }

    /**
     * sends a message to the player asking what powerUp he wants to discard
     * @param player current player
     */
    public void requestPowerUpDiscard(Player player) {
        String powerUpList = player.getResources().showpowerUp();
        int num = player.getResources().getPowerUp().size();
        if (num > 1) {
            powerUpNotifier.requestPowerUpDiscard(player.getPlayerColor(), powerUpList);
        }
        else {
            discardPowerUp(player, 0);
        }
    }

    /**
     * Discards te chosen powerUp
     * @param player current player
     * @param index index corresponding to the powerUp the player wants to discard
     */
    public void discardPowerUp(Player player, int index) {
        PowerUp discardedPowerUp = player.getResources().removePowerUp(index);
        gameBoard.getDecks().getDiscardedPowerUpDeck().add(discardedPowerUp);

        String toPlayer = "You discarded " + discardedPowerUp.toString();
        String toOthers = player.getPlayerName() + " discarded " + discardedPowerUp.toString();
        printMessage(player.getPlayerColor(), toPlayer, toOthers);

        spawnPlayer(player, discardedPowerUp.getAmmo());
        getTurnCurrent().setReceivedInput(true);
    }


    /**
     * Sends a message to the current player and to all other player
     * @param playerColor color of the current player
     * @param toPlayer message to send to the current player
     * @param toOthers message to send to the players that are not the current player
     */
    public void printMessage(PlayerColor playerColor, String toPlayer, String toOthers) {
        gameNotifier.notifyMessages(playerColor, toPlayer, toOthers);
    }

    /**
     * Sets the player position to the spawn square with the selected color
     * @param player current player
     * @param ammoColor color of the spawn square
     */
    public void spawnPlayer(Player player, AmmoColor ammoColor) {
        SquareColor squareColor;
        switch(ammoColor.toString()) {
            case "RED":
                squareColor = SquareColor.RED;
                break;
            case "BLUE":
                squareColor = SquareColor.BLUE;
                break;
            case "YELLOW":
                squareColor = SquareColor.YELLOW;
                break;
            default:
                squareColor = null;
        }
        Square spawnSquare = gameBoard.getMap().getSpawnSquare(squareColor);
        player.setPosition(spawnSquare);
        String toPlayer;
        String toOthers;

        toPlayer = "You just spawned at " + spawnSquare.getID();
        toOthers = player.getColoredName() + " just spawned at " + spawnSquare.getID();
        printMessage(player.getPlayerColor(), toPlayer, toOthers);
    }

    /**
     * Notifies all the player that the Run Move is completed
     */
    public void updateRun() {
        Player currentPlayer = turnManager.getCurrentPlayer();
        PlayerColor currentPlayerColor = currentPlayer.getPlayerColor();
        gameNotifier.notifyRun(currentPlayerColor, currentPlayer.getPlayerName(), currentPlayer.getPosition().getID());
    }

    /**
     * Updates the action Tree of the current player to show the next available actions
     */
    public void updateAction() {
        Player currentPlayer = turnManager.getCurrentPlayer();
        currentPlayer.getActionTree().updateAction();
    }

    /**
     * Sends to the current player a list of the available powerUps to choose from
     * @param playerColor current player
     */
    public void choosePowerUp(PlayerColor playerColor) {
        String availablePowerUp;
        availablePowerUp = getPlayer(playerColor).getResources().showpowerUp();
        powerUpNotifier.choosePowerUp(playerColor, availablePowerUp);
    }

    /**
     * Sends the player a list of all the available squares to move to using the teleporter
     * @param playerColor color of the current player
     */
    public void useTeleporter(PlayerColor playerColor) {

        powerUpNotifier.chooseTeleporterSquare(playerColor);
    }

    /**
     * Sends a message to all players to notify that the teleporter powerUp was used
     * @param playerColor current player color
     */
    public void notifyTeleporter(PlayerColor playerColor) {
        String playerName = getPlayer(playerColor).getPlayerName();
        String newSquare = String.valueOf(getPlayer(playerColor).getPosition().getID());
        gameNotifier.notifyTeleporter(playerColor, playerName, newSquare);
        chooseAction(playerColor);
    }

    /**
     * Sends a message to the current player showing him all his available actions and
     * tells the other players that the current player is choosing his next action
     * @param playerColor current player color
     */
    public void chooseAction(PlayerColor playerColor) {
        Player currentPlayer = getPlayer(playerColor);
        String availableAction = currentPlayer.getActionTree().availableAction();
        currentPlayer.getActionTree().setMoveEnded(false);
        actionNotifier.chooseAction(playerColor, availableAction);
        String toOthers = currentPlayer.getColoredName() + " is choosing between his actions.";
        getGameNotifier().notifyOtherPlayers(toOthers, playerColor);

    }

    public Current getCurrent() {
        return current;
    }

    /**
     * Sends a message to the current player showing him all the targets he can move and if there
     * are no targets to move shows him his available actions again
     * @param playerColor color of he current player
     */
    public void useNewton(PlayerColor playerColor) {
        ArrayList<Player> allPLayers = turnManager.getAllPlayers();
        ArrayList<Player> allPlayersCopy = new ArrayList<>(allPLayers);
        for(Player player : allPLayers)
            if(player.getPosition() == null || player.getPlayerColor().equals(playerColor))
                allPlayersCopy.remove(player);
        if(allPlayersCopy.isEmpty()){
            getGameNotifier().notifyPlayer("You have no targets to move!",playerColor);
            getCurrent().setSelectedNewton(null);
            chooseAction(playerColor);
            return;
        }

        Player player = getPlayer(playerColor);
        player.getResources().removePowerUp(getCurrent().getSelectedNewton());
        getGameBoard().getDecks().getDiscardedPowerUpDeck().add(getCurrent().getSelectedNewton());
        getCurrent().setSelectedNewton(null);

        String opponentList = "";
        for (Player value : allPlayersCopy) {
            if (value.getPlayerColor() != playerColor) {
                current.addOpponent(value);
                opponentList = opponentList + value.getPlayerName() + " ";
            }
        }
        powerUpNotifier.chooseNewtonOpponent(playerColor, opponentList);
    }

    /**
     * Sends the current player a list of squares to move the selected target to
     * @param playerColor color of the current square
     * @param opponent player that has to be moved by the Newton
     */
    public void chooseNewtonSquare(PlayerColor playerColor, Player opponent) {
        Square currentOpponentSquare = opponent.getPosition();
        ArrayList<Square> possibleSquare = getSquaresInCardinal2(opponent);
        current.setSquare(possibleSquare);
        possibleSquare.remove(currentOpponentSquare);
        String squareList = "";
        for (Square square : possibleSquare) {
            squareList = squareList + square.getID() + " ";
        }
        powerUpNotifier.chooseNewtonSquare(playerColor, squareList);
    }

    /**
     * Sends all the players a message notifying tha the newton has been used
     * @param playerColor current player color
     * @param opponent player moved by the newton
     */
    public void notifyNewton(PlayerColor playerColor, Player opponent) {
        String playerName = getPlayer(playerColor).getPlayerName();
        String opponentName = opponent.getPlayerName();
        int newSquare = opponent.getPosition().getID();
        PlayerColor opponentColor = opponent.getPlayerColor();
        gameNotifier.notifyNewton(playerName, opponentName, playerColor, opponentColor, newSquare);
        getPlayer(playerColor).getActionTree().setMoveEnded(true);
    }

    /**
     * Sends the player a list of the reloaded weapons he holds
     * @param playerColor current player color
     */
    public void showWeaponCards(PlayerColor playerColor) {
        String availableWeapons = "";
        ArrayList<Weapon> weapons = getPlayer(playerColor).getResources().getReloadedWeapon();
        int i = 1;
        for(Weapon weapon : weapons){
            availableWeapons =  availableWeapons + i +". " + weapon.getWeaponName() + "\n";
            i++;
        }
        weaponNotifier.showWeaponCards(playerColor, availableWeapons);
    }

    /**
     * Sends a list of the available targets to the current player
     * @param playerColor current player's color
     * @param availableTargets list of the targets the current player can hit
     * @param targetsNumber maximum number of targets the player can choose
     */
    public void selectTargets(PlayerColor playerColor, ArrayList<Player> availableTargets, int targetsNumber) {
        String opponentList = "";
        Player currentPlayer = getPlayer(playerColor);
        Weapon weapon = current.getSelectedWeapon();
        String effectType = weapon.getWeaponTree().getLastAction().getData().getType();
        if(availableTargets.isEmpty()){
            if(weapon.getWeaponTree().getRoot().getChildren().contains(weapon.getWeaponTree().getLastAction())){
                getGameNotifier().notifyPlayer("No available targets with this Fire Mode choose another one",playerColor);
                weapon.getWeaponTree().resetAction();
                resetCurrent();
                getCurrent().setSelectedWeapon(weapon);
                showFireModes(playerColor,weapon);
                return;
            }
            else{
                getGameNotifier().notifyPlayer("No available targets with this Fire Mode you didn't hit anybody",playerColor);
                switch (effectType){
                    case "base":
                        weapon.askBaseRequirements(currentPlayer);
                        return;
                    case "alternative":
                        ((WeaponAlternative) weapon).askAlternativeRequirements(currentPlayer);
                        return;
                    case "optional1":

                        ((WeaponOptional1) weapon).askOptionalRequirements1(currentPlayer);
                        return;
                    case "optional2":
                        ((WeaponOptional2) weapon).askOptionalRequirements2(currentPlayer);
                        return;
                }

            }
        }
        int j = 1;
        for (Player availableTarget : availableTargets) {
            if (availableTarget.getPlayerColor() != playerColor) {
                current.addOpponent(availableTarget);
                opponentList = opponentList + (j) + ". " + availableTarget.getPlayerName() + " | \n";
                j++;
            }
        }
        weaponNotifier.selectTargets(playerColor, opponentList, targetsNumber);
    }

    /**
     * Shows the player the available fire modes for the seected weapon
     * @param playerColor current player color
     * @param weapon selected weapon
     */
    public void showFireModes(PlayerColor playerColor, Weapon weapon) {
        List<WeaponTreeNode<FireMode>> FireModes = new ArrayList<>();
        List<WeaponTreeNode<FireMode>> availableFireModes = weapon.getWeaponTree().getLastActionPerformed().getChildren();
        for(WeaponTreeNode<FireMode> fireMode : availableFireModes){
            if(Checks.canUseFireMode(getPlayer(playerColor), weapon, fireMode.getData().getType()))
                FireModes.add(fireMode);
        }
        if(FireModes.size()==1 && FireModes.get(0).getData().getType().equals("end")){
            if(getPlayer(playerColor).getResources().hasOneCube()) {
                for (PowerUp powerUp : getPlayer(playerColor).getResources().getPowerUp()) {
                    if (powerUp instanceof TargetingScope && !getCurrent().getAllDamagedPlayer().isEmpty()) {
                        current.addAllTargetingScopes((TargetingScope) powerUp);
                    }
                }
                for (PowerUp powerUp : getPlayer(playerColor).getResources().getPowerUp()) {
                    if (powerUp instanceof TargetingScope && !getCurrent().getAllDamagedPlayer().isEmpty()) {
                        current.setLastTargetingScope((TargetingScope) powerUp);
                        powerUpNotifier.askTargetingScope(getPlayer(playerColor).getPlayerColor(), powerUp);
                        return;
                    }
                }
            }
            notifyShoot(getPlayer(playerColor));
            return;
        }

        getCurrent().setAvailableFireModes(FireModes);
        String result = "Your available fire modes: \n";
        int i = 1;
        for (WeaponTreeNode<FireMode> child : FireModes) {
            result = result +i+". "+child.getData().getEffectName()+"\n";
            i++;
        }
        weaponNotifier.showFireModes(playerColor, result);
    }

    /**
     * Sends the player a list of the powerups he can use to pay the reload cost of the selected weapon
     * @param currentPlayer current player
     * @param weapon selected weapon
     */
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
            boolean red = (fireRED > 0 && powerUp.getAmmo().toString().equals("RED"));
            boolean blue = (fireBLUE > 0 && powerUp.getAmmo().toString().equals("BLUE"));
            boolean yellow = (fireYELLOW > 0 && powerUp.getAmmo().toString().equals("YELLOW"));

            if(red || blue || yellow)
                getCurrent().addAvailablePaymentPowerUps(powerUp);
        }
        if(getCurrent().getAvailablePaymentPowerUps().isEmpty()){
            payReload(currentPlayer,weapon);
            return;
        }
        weaponNotifier.askReloadPayment(currentPlayer.getPlayerColor(),current.getAvailablePaymentPowerUps());
    }

    /**
     * Pays the reload cost using the previously selected powerUps
     * @param currentPlayer current player
     * @param weapon selected weapon
     */
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
            switch (powerUp.getAmmo().toString()) {
                case "RED":
                    powerUpRED++;
                    break;
                case "BLUE":
                    powerUpBLUE++;
                    break;
                case "YELLOW":
                    powerUpYELLOW++;
                    break;
            }
        }
        for(PowerUp powerUp : getCurrent().getSelectedPaymentPowerUps()){
            currentPlayer.getResources().removePowerUp(powerUp);
            getGameBoard().getDecks().getDiscardedPowerUpDeck().add(powerUp);
        }
        fireRED = fireRED-powerUpRED;
        fireBLUE = fireBLUE-powerUpBLUE;
        fireYELLOW = fireYELLOW-powerUpYELLOW;
        Ammo ammo = new Ammo(fireRED,fireBLUE,fireYELLOW);
        currentPlayer.getResources().removeFromAvailableAmmo(ammo.getRed(),ammo.getBlue(),ammo.getYellow());
        weapon.reload();
        resetCurrent();
        updateAction();
    }
    /**
     * Sends the player a list of the powerUps he can use to pay the pickUp cost of the selected weapon
     * @param currentPlayer current player
     * @param weapon selected weapon
     */
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
            boolean red = fireRED > 0 && powerUp.getAmmo().toString().equals("RED");
            boolean blue = fireBLUE > 0 && powerUp.getAmmo().toString().equals("BLUE");
            boolean yellow = fireYELLOW > 0 && powerUp.getAmmo().toString().equals("YELLOW");

            if(red || blue || yellow)
                getCurrent().addAvailablePaymentPowerUps(powerUp);
        }

        if(getCurrent().getAvailablePaymentPowerUps().isEmpty()){
            payPickUp(currentPlayer,weapon);
            return;
        }
        weaponNotifier.askPickUpPayment(currentPlayer.getPlayerColor(),getCurrent().getAvailablePaymentPowerUps());
    }
    /**
     * Pays the pickUp cost using the previously selected powerUps
     * @param currentPlayer current player
     * @param weapon selected weapon
     */
    public void payPickUp(Player currentPlayer, Weapon weapon){
        int powerUpRED = 0;
        int powerUpBLUE = 0;
        int powerUpYELLOW = 0;
        Ammo fireModeCost = weapon.getPickUpCost();
        int fireRED = fireModeCost.getRed();
        int fireBLUE = fireModeCost.getBlue();
        int fireYELLOW = fireModeCost.getYellow();
        ArrayList<PowerUp> powerUps = current.getSelectedPaymentPowerUps();

        for(PowerUp powerUp : powerUps){
            switch (powerUp.getAmmo().toString()) {
                case "RED":
                    powerUpRED++;
                    break;
                case "BLUE":
                    powerUpBLUE++;
                    break;
                case "YELLOW":
                    powerUpYELLOW++;
                    break;
            }
        }

        for(PowerUp powerUp : getCurrent().getSelectedPaymentPowerUps()){
            currentPlayer.getResources().removePowerUp(powerUp);
            getGameBoard().getDecks().getDiscardedPowerUpDeck().add(powerUp);
        }
        fireRED = fireRED-powerUpRED;
        fireBLUE = fireBLUE-powerUpBLUE;
        fireYELLOW = fireYELLOW-powerUpYELLOW;
        Ammo ammo = new Ammo(fireRED,fireBLUE,fireYELLOW);
        currentPlayer.getResources().removeFromAvailableAmmo(ammo.getRed(),ammo.getBlue(),ammo.getYellow());

        if(currentPlayer.getResources().getAllWeapon().size() == 3){
            weaponNotifier.askWeaponSwap(currentPlayer);
            return;
        }
        currentPlayer.getResources().addWeapon(getCurrent().getSelectedPickUpWeapon());
        for(int i = 0; i < 3; i++){
            if(currentPlayer.getPosition().getWeapon()[i] == getCurrent().getSelectedPickUpWeapon()) {
                currentPlayer.getPosition().getWeapon()[i] = null;
            }
        }


        resetCurrent();
        updateAction();
    }


    /**
     * Pays the fire mode cost using the previously selected powerUps
     * @param currentPlayer current player
     * @param weapon selected weapon
     */
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
            switch (powerUp.getAmmo().toString()) {
                case "RED":
                    powerUpRED++;
                    break;
                case "BLUE":
                    powerUpBLUE++;
                    break;
                case "YELLOW":
                    powerUpYELLOW++;
                    break;
            }

            }
        for(PowerUp powerUp : getCurrent().getSelectedPaymentPowerUps()){
            currentPlayer.getResources().removePowerUp(powerUp);
            getGameBoard().getDecks().getDiscardedPowerUpDeck().add(powerUp);
        }
        fireRED = fireRED-powerUpRED;
        fireBLUE = fireBLUE-powerUpBLUE;
        fireYELLOW = fireYELLOW-powerUpYELLOW;
        Ammo ammo = new Ammo(fireRED,fireBLUE,fireYELLOW);
        currentPlayer.getResources().removeFromAvailableAmmo(ammo.getRed(),ammo.getBlue(),ammo.getYellow());
        current.getSelectedPaymentPowerUps().clear();
        current.getAvailablePaymentPowerUps().clear();

    }
    /**
     * Sends the player a list of the powerups he can use to pay the fire mode cost of the selected weapon
     * @param currentPlayer current player
     * @param weapon selected weapon
     */
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
            boolean red = fireRED > 0 && powerUp.getAmmo().toString().equals("RED");
            boolean blue = fireBLUE > 0 && powerUp.getAmmo().toString().equals("BLUE");
            boolean yellow = fireYELLOW > 0 && powerUp.getAmmo().toString().equals("YELLOW");

            if(red || blue || yellow)
                getCurrent().addAvailablePaymentPowerUps(powerUp);
        }

        if(getCurrent().getAvailablePaymentPowerUps().isEmpty()){
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
        weaponNotifier.askWeaponPayment(currentPlayer.getPlayerColor(),getCurrent().getAvailablePaymentPowerUps());
    }

    /**
     * Support method that calculates the cost of the multiple payment methods from the selected weapon
     */
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


    /**
     * Sends the current player a list of the squares to choose from
     * @param playerColor current player color
     * @param squares list of available squares
     */
    public void chooseWeaponSquare(PlayerColor playerColor, ArrayList<Square> squares) {
        current.setAvailableWeaponSquares(squares);
        weaponNotifier.chooseWeaponSquare(playerColor, squares);
    }

    /**
     * Notifies all players the end of the Shoot Action
     * @param currentPlayer current player
     */
    public void notifyShoot(Player currentPlayer) {
        /*Set<Player> set = new LinkedHashSet<Player>(current.getSelectedBaseTargets());
        set.addAll(current.getSelectedAlternativeTargets());
        set.addAll(current.getSelectedOptionalTargets1());
        set.addAll(current.getSelectedOptionalTargets2());
        ArrayList<Player> targets = new ArrayList<>(set);*/
        ArrayList<Player> targets = getCurrent().getAllShotPlayer();
        targets.remove(currentPlayer);
        ArrayList<Player> allPlayers = turnManager.getAllPlayers();
        if(getCurrent().getSelectedWeapon()!=null)
            getCurrent().getSelectedWeapon().getWeaponTree().resetAction();
        resetCurrent();
        gameNotifier.notifyShoot(currentPlayer, targets, allPlayers);
    }

    /**
     * Controls if the player can shoot with another fire mode, if so the available fire modes
     * are sent, otherwise the Shoot Action ends
     * @param weapon selected weapon
     * @param currentPlayer current player in game
     */
    public void checkNextWeaponAction(Weapon weapon, Player currentPlayer) {

        weapon.unload();
        weapon.getWeaponTree().updateLastActionPerformed();

        if (weapon.getWeaponTree().isActionEnded()) {
            weapon.getWeaponTree().resetAction();
            if(currentPlayer.getResources().hasOneCube()) {
                for (PowerUp powerUp : currentPlayer.getResources().getPowerUp()) {
                    if (powerUp instanceof TargetingScope && !getCurrent().getAllDamagedPlayer().isEmpty()) {
                        current.addAllTargetingScopes((TargetingScope) powerUp);
                    }
                }
                for (PowerUp powerUp : currentPlayer.getResources().getPowerUp()) {
                    if (powerUp instanceof TargetingScope && !getCurrent().getAllDamagedPlayer().isEmpty()) {
                        current.setLastTargetingScope((TargetingScope) powerUp);
                        powerUpNotifier.askTargetingScope(currentPlayer.getPlayerColor(), powerUp);
                        return;
                    }
                }
            }
            notifyShoot(currentPlayer);
        }
        else
            weapon.getModel().showFireModes(currentPlayer.getPlayerColor(), weapon);
    }

    /**
     * Removes the ammo card from the selected square and discards it
     * @param square square from which the player is grabbing ammo
     */
    public void discardAmmo(Square square) {
        AmmoCard discardedAmmo = square.getAmmoCard();
        square.removeAmmoCard();
        gameBoard.getDecks().getDiscardedAmmoCardDeck().add(discardedAmmo);
    }

    /**
     * Draws the selected number of powerUps from the PowerUp Deck
     * @param playerColor current player color
     * @param num number of powerUps to draw
     */
    public void drawPowerUp(PlayerColor playerColor, int num) {
        Player currentPlayer = getPlayer(playerColor);
        ArrayList<PowerUp> drawnPowerUp = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < num; i++) {
            PowerUp powerUp = gameBoard.getDecks().drawPowerUp();
            drawnPowerUp.add(powerUp);
            stringBuilder.append(powerUp.toString());
            stringBuilder.append(" ");
        }
        currentPlayer.getResources().addPowerUp(drawnPowerUp);


        gameNotifier.notifyDrawPowerUp(playerColor, currentPlayer.getPlayerName(), stringBuilder.toString(), num);

    }

    /**
     * Adds the picked up ammo to the player's resources and notifies the players that the Grab action
     * is completed
     * @param playerColor current player color
     * @param ammo ammo added to the player's resources
     */
    public void addAmmo(PlayerColor playerColor, Ammo ammo) {
        Player currentPlayer = getPlayer(playerColor);
        currentPlayer.getResources().addToAvailableAmmo(ammo.getRed(),ammo.getBlue(),ammo.getYellow());
        gameNotifier.notifyDrawAmmo(playerColor, currentPlayer.getPlayerName(), ammo.toString());
    }

    /**
     * Sends the player a list of the weapons he can reload
     * @param playerColor current player color
     */
    public void requestWeaponReload(PlayerColor playerColor) {
        Player currentPlayer = getPlayer(playerColor);
        Ammo allAmmo = currentPlayer.getResources().getAllAmmo();
        ArrayList<Weapon> reloadableWeapon = currentPlayer.getResources().getReloadableWeapon();
        ArrayList<Weapon> reloadableWeaponCopy = new ArrayList<>(reloadableWeapon);
        for(Weapon weapon : reloadableWeaponCopy){
            Ammo cost = weapon.getBaseCost();
            if(cost.getRed()>allAmmo.getRed() || cost.getBlue()>allAmmo.getBlue() || cost.getYellow()>allAmmo.getYellow()){
                reloadableWeapon.remove(weapon);
            }
        }
        current.setReloadableWeapon(reloadableWeapon);
        String reloadableWeaponList = currentPlayer.getResources().showReloadableWeapon();
        String availableAmmoList = currentPlayer.getResources().getAvailableAmmo().toString();
        String availablePowerUpList = currentPlayer.getResources().showpowerUp();

        weaponNotifier.requestWeaponReload(playerColor, reloadableWeaponList, availableAmmoList, availablePowerUpList);
    }

    /**
     * Adds the selected amount of damage to the target's playerBoard
     * @param shooterColor current player color
     * @param opponentColor color of the target
     * @param damage number of damage to deal to the target
     */
    public void addDamage(PlayerColor shooterColor, PlayerColor opponentColor, int damage) {
        Player opponent = getPlayer(opponentColor);
        int opponentDamage = opponent.getPlayerBoard().getDamageCounter().getDamage();
        //move the shooter color's marks on the opponent damage board
        int pastMarks = opponent.getPlayerBoard().getMarkCounter().getMarkFromColorAndRemove(shooterColor);
        int givenDamage = Checks.givenDamage(opponentDamage, damage + pastMarks);

        DamageCounter damageCounter = opponent.getPlayerBoard().getDamageCounter();

        if(!getCurrent().getAllShotPlayer().contains(opponent))
            getCurrent().addShotPlayer(opponent);

        if (givenDamage != 0) {
            if(!getCurrent().getAllDamagedPlayer().contains(opponent))
                getCurrent().addDamagedPlayer(opponent);
            damageCounter.addDamage(shooterColor, givenDamage);
            if (damageCounter.getDamage() >= Checks.getKillshot()) {
                opponent.setKillshot(true);

                if(damageCounter.getDamage() >= Checks.getKillshot()) {
                    turnManager.addKillShot();
                    }
                }

                if (damageCounter.getDamage() == Checks.getMaxDamage()) {
                    int playermark = getPlayer(shooterColor).getPlayerBoard().getMarkCounter().getMarkFromColor(opponentColor);
                    if (Checks.givenMark(playermark, 1) != 0){
                        getGameNotifier().notifyPlayer("You have been marked by " + getPlayer(opponentColor).getColoredName(),
                                shooterColor);
                    }
                    addMark(opponentColor, shooterColor, 1);
                    opponent.setDead();
                }
            }
        if (getPlayer(opponentColor).hasTagBackGrenade() && !getTurnCurrent().getGrenadePeopleArray().contains(opponent)) {
            getTurnCurrent().getGrenadePeopleArray().add(opponent);
        }
        }

    /**
     * Adds the selected number of marks to the target's playerBoard
     * @param markerColor current player color
     * @param markedColor color of the target
     * @param mark number of damage to deal to the target
     */
    public void addMark(PlayerColor markerColor, PlayerColor markedColor, int mark) {
        Player opponent = getPlayer(markedColor);
        MarkCounter markCounter = opponent.getPlayerBoard().getMarkCounter();
        int opponentMark = markCounter.getMarkFromColor(markerColor);
        int givenMark = Checks.givenMark(opponentMark, mark);

        if(!getCurrent().getAllShotPlayer().contains(opponent))
            getCurrent().addShotPlayer(opponent);

        if (givenMark != 0) {
            markCounter.addMarks(markerColor, givenMark);
        }
    }

    /**
     *  Called at the end of Turn on all damaged players after having managed marks and dead players to
     *  set the Frenzy action tree
     */

    public void verifyNewAction(PlayerColor playerColor) {
        Player player = getPlayer(playerColor);
        int currentTreeID = player.getActionTree().getID();
        int newTreeID = Checks.verifyNewAction(player);
        if (currentTreeID != newTreeID) {
            player.setActionTree(newTreeID);
        }
    }


    /**
     * Returns all in game players but not those that are not on the map
     */
    public ArrayList<Player> getAllSpawnedPlayers() {
        ArrayList<Player> def = new ArrayList<>(players.values());
        for(Player player : players.values())
            if(player.getPosition() == null)
                def.remove(player);
        return def;
    }
    /**
     * Returns all in game players
     */
    public List<Player> getEachPlayer(){
        return new ArrayList<>(players.values());
    }

    /**
     * Sends the current player a list of the players he has damaged
     * @param playerColor current player color
     * @param damagedPlayers players that have been damaged
     */
    public void targetingScopeTargets(PlayerColor playerColor, ArrayList<Player> damagedPlayers){
        String message="Select Targeting Scope target: \n";
        int i=1;
        for(Player player : damagedPlayers) {
            message =  message+ i + ". " + player.getPlayerName() + " | \n";
            i++;
        }
        powerUpNotifier.targetingScopeTargets(playerColor,message);
    }

    /**
     * Sets the selected player afk
     * @param player player to set AFK
     */
    public void setPlayerAfk(Player player){
        if (player.isAfk()){
            return;
        }
        player.setAfk(true);
        getActionNotifier().setPlayerAfk(player.getPlayerColor());
        String toOthers = player.getColoredName() + " is afk. Their turn will be skipped";
        getGameNotifier().notifyOtherPlayers(toOthers, player.getPlayerColor());
    }

    /**
     * Removes the player from the AFK players
     * @param player player to remove from the afk players
     */
    public void wakeUpPlayer(Player player){
        System.out.println("Waking up " + player.getPlayerName());
        player.setAfk(false);
    }

    /**
     * Asks the damaged player if he wants to use the Tagback Grenade
     * @param player current player
     * @param opponent player the has been damaged
     */
    public void tagbackGranadeRequest(Player player, Player opponent){
        StringBuilder stringBuilder = new StringBuilder();
        int i = 1;
        for (PowerUp currentPowerUp : player.getResources().getPowerUp()){
            if (currentPowerUp instanceof TagbackGrenade){
                stringBuilder.append(i);
                stringBuilder.append(": ");
                stringBuilder.append(currentPowerUp.toString());
                stringBuilder.append(" ");
            }
            i++;
        }
        getPowerUpNotifier().askTagbackGrenade(player, opponent, stringBuilder.toString());
    }

    /**
     * Sends to the players a list of the maps to choose from
     */
    public void mapVote(Player player){
        actionNotifier.mapVote(player.getPlayerColor());
    }

    /**
     * Getter for the map votes
     */
    public ArrayList<Integer> getMapVotes() {
        return mapVotes;
    }

    /**
     * Sends the layers a list of the weapons he can pick up
     * @param payableWeapons weapon the player can pick up from the spawn square
     * @param playerColor current player
     */
    public void showPickUpWeapons(ArrayList<Weapon> payableWeapons,PlayerColor playerColor){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0. RETURN\n");
        for (int i = 0; i < payableWeapons.size(); i++){
            stringBuilder.append(i+1);
            stringBuilder.append(". ");
            stringBuilder.append(payableWeapons.get(i).getWeaponName());
            stringBuilder.append(" ");
            stringBuilder.append(payableWeapons.get(i).getPickUpCost().toString());
            stringBuilder.append("\n");
        }
        weaponNotifier.showPickUpWeapons(playerColor,stringBuilder.toString());
    }

    /**
     * Clears the whole current class
     */
    public void resetCurrent(){
        current = new Current();
    }

    /**
     * Swaps the selected weapon the player holds with the one he wants to pick up from the spawn square
     * @param currentPlayer current player
     * @param input index of the weapon the player wants to put down
     */
    public void swapPickUpWeapon(Player currentPlayer, int input){
        currentPlayer.getResources().addWeapon(getCurrent().getSelectedPickUpWeapon());
        for(int i = 0; i < 3; i++){
            if(currentPlayer.getPosition().getWeapon()[i] == getCurrent().getSelectedPickUpWeapon())
                currentPlayer.getPosition().getWeapon()[i] = currentPlayer.getResources().getAllWeapon().get(input-1);
        }
        currentPlayer.getResources().getAllWeapon().remove(input-1);
        resetCurrent();
        updateAction();
    }

    /**
     * Asks the current player what color of cube he is going to use to pay
     * for the Targeting Scope
     * @param player Player that is going to pay for the Targeting Scope
     */
    public void askScopePayment(Player player){
        String message = "Choose the color of the cube to pay: \n";
        message = message + player.getResources().getAvailableAmmo().toString()+"\n";
        if(player.getResources().getAvailableAmmo().getRed()>0)
            message = message + CLI.getRed()+"R "+CLI.getResetString();
        if(player.getResources().getAvailableAmmo().getBlue()>0)
            message = message + CLI.getBlue()+"B "+CLI.getResetString();
        if(player.getResources().getAvailableAmmo().getYellow()>0)
            message = message + CLI.getYellow()+"Y "+CLI.getResetString();
        powerUpNotifier.askScopePayment(player.getPlayerColor(),message);
    }
}


