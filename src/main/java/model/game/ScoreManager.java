package model.game;

import controller.Checks;
import model.player.Player;
import model.player.PlayerColor;

import java.util.*;

public class ScoreManager {

    /**
     * Score Manager Class
     */
    private final Model model;
    //rank of the player
    private ArrayList<PlayerColor> playerRank = new ArrayList<>();

    private static final int [] frenzyKillShotPoints = {8, 6, 4, 2, 1};

    /**
     * Constructor
     *@param model used to get players' info
     */
    public ScoreManager(Model model) {

        this.model = model;
        //the player rank is initialized with the elements of allPlayers array list
        for (Player player : model.getTurnManager().getAllPlayers()) {
            playerRank.add(player.getPlayerColor());
        }
    }

    /**
     * This method is called at the end of every turn to update the current rank
     */
    public void updateScore() {
        ArrayList<Player> allPlayer = model.getTurnManager().getAllPlayers();
        //boolean variable used to determine whether there has been a change in the score
        boolean hasChanged = false;

        ArrayList<Player> deadPlayers = new ArrayList<>();
        for (Player player : allPlayer) {
            if (player.isKillShot() || player.isDead()) {
                deadPlayers.add(player);
            }
        }
        if(!deadPlayers.isEmpty()){
            hasChanged = true;
        }

        for (int i = 0; i < deadPlayers.size(); i++) {
            //the damage scoring is computed only if there is at least one killshot or dead player

                computePoints(deadPlayers.get(i));

                //remove the highest point
                deadPlayers.get(i).getPlayerBoard().getPoints().removeHighestPoint();

                //remove skull and add token
                //gets the color of the player who dealt the killshot damage
                PlayerColor killshotColor = deadPlayers.get(i).getPlayerBoard().getDamageCounter().getDamageCounter().get(Checks.getKillshot()-1);
                model.getGameBoard().getKillShotTrack().removeSkull(killshotColor);

                //check for overkill
                if (deadPlayers.get(i).getPlayerBoard().getDamageCounter().getDamage() == Checks.getMaxDamage()) {
                    model.getGameBoard().getKillShotTrack().addOverKill();
                }
                //check if the frenzy turn is activated
                if(i == deadPlayers.size() - 1){
                    if(model.getGameBoard().getKillShotTrack().getLastIndex() >= model.getGameBoard().getKillShotTrack().getKillShotTrack().length){
                        if(!model.getTurnManager().isFrenzy()){
                            model.getTurnManager().setFrenzy();
                        }
                    }
                }
        }

        //compute double killshots
        if (model.getTurnManager().numOfKillShot() >= Checks.getDoubleKillShot()) {
            model.getTurnManager().getCurrentPlayer().addScore(1);
        }
        //if there has been a change in the score, update the rank
        if(hasChanged) {
            updatePlayerRank();
        }
    }

    /**
     * This method is called at the end of the game to produce the final rank
     */
    public void finalScore() {
        ArrayList<Player> allPlayer = model.getTurnManager().getAllPlayers();
        //maps every player to his token on the killshot track
        LinkedHashMap<PlayerColor, Integer> unsortRank;
        ArrayList<PlayerColor> killShotTrackRank;

        for (Player value : allPlayer) {
            if (value.getPlayerBoard().getDamageCounter().getDamage() > 0)
                computePoints(value);
        }

        //compute killshottrack points
        unsortRank = model.getGameBoard().getKillShotTrack().getRank();

        killShotTrackRank = sortRank(unsortRank);

        //add killshot track points
        for (int i = 0; i < killShotTrackRank.size(); i++) {
            PlayerColor playerColor = killShotTrackRank.get(i);
            Player player = model.getPlayer(playerColor);
            if (i < frenzyKillShotPoints.length) {
                player.addScore(frenzyKillShotPoints[i]);
                player.setScoreFromKillShotTrack(frenzyKillShotPoints[i]);
            }
        }
        updatePlayerRank();

        ArrayList<PlayerColor> afkPlayers = new ArrayList<>();
        for (PlayerColor player : playerRank) {
            if (model.getPlayer(player).isAfk()) {
                afkPlayers.add(player);
            }
        }
        for (PlayerColor playerColor : afkPlayers) {
            playerRank.remove(playerColor);
        }
        model.getGameNotifier().notifyGeneric(showPlayerRank());
        model.getGameNotifier().notifyGeneric(getWinner());

    }

    /**
     * Established the winner(s) of the game and notify to the players
     */
    public String getWinner() {
        int maxScore;
        ArrayList<Player> evenPlayers = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        maxScore = model.getPlayer(playerRank.get(0)).getScore().getScore();
        boolean even = true;
        int maxScoreFromKillShotTrack;
        Player singleWinner = model.getPlayer(playerRank.get(0));

        for (PlayerColor playerColor : playerRank) {
            Player currentPlayer = model.getPlayer(playerColor);
            if (currentPlayer.getScore().getScore() == maxScore) {
                evenPlayers.add(currentPlayer);
            }
        }

        if (evenPlayers.size() == 1) { //there's only one winner
            stringBuilder.append(evenPlayers.get(0).getColoredName());
            stringBuilder.append(" ");
            stringBuilder.append("has won the game!");
        }
        else{
            //find the player who got the highest score from the killShotTrack
            maxScoreFromKillShotTrack = evenPlayers.get(0).getScoreFromKillShotTrack();
            for (Player currentPlayer : evenPlayers) {
                if (currentPlayer.getScoreFromKillShotTrack() > maxScoreFromKillShotTrack) {
                    maxScoreFromKillShotTrack = currentPlayer.getScoreFromKillShotTrack();
                    singleWinner = currentPlayer;
                    even = false;
                }
            }

            if(even){
                for (Player evenPlayer : evenPlayers) {
                    stringBuilder.append(evenPlayer.getColoredName());
                    stringBuilder.append(" | ");
                }
                stringBuilder.append("have won the game!");
            }
            else{
                stringBuilder.append(singleWinner.getColoredName());
                stringBuilder.append(" ");
                stringBuilder.append("has won the game!");
            }


        }

        return stringBuilder.toString();
    }

    /**
     * This method is an helper method called by updateScore(). It determines the score distribution of the turn.
     * @param player the player whose damage counter is being evaluated
     */
    private void computePoints(Player player){

        //all players that dealt damage
        ArrayList<PlayerColor> temp = player.getPlayerBoard().getDamageCounter().getDamageCounter();
        //all players that dealt damage, with no duplicates
        ArrayList<PlayerColor> shooterColor = new ArrayList<>();
        //current turn rank
        LinkedHashMap<PlayerColor,Integer> currentRank = new LinkedHashMap<>();

        //delete duplicates
        for (PlayerColor currentColor : temp) {
            if (!shooterColor.contains(currentColor)) {
                shooterColor.add(currentColor);
            }
        }
        //add the first blood damage
        //retrieve the damage to deal for the first blood
        int firstBloodDamage = player.getPlayerBoard().getPoints().getFirstBlood();
        //player who dealt the first blood
        Player firstBloodPlayer = model.getPlayer(shooterColor.get(0));
        //add score
        firstBloodPlayer.getScore().addScore(firstBloodDamage);

        //reverse the array list
        Collections.reverse(shooterColor);


        for (PlayerColor currentColor : shooterColor) {
            int currentDamage = player.getPlayerBoard().getDamageCounter().getDamageFromColor(currentColor);
            //build the linked map that maps every shooter to the damage he dealt
            currentRank.put(currentColor, currentDamage);
        }


        //sort the rank by descending order
        shooterColor = sortRank(currentRank);


        for (int i = 0; i < shooterColor.size(); i++){
            Player currentPlayer = model.getPlayer(shooterColor.get(i));
            //retrieve the score for the i-th highest damage
            int score = player.getPlayerBoard().getPoints().getPoint(i);
            //add damage
            currentPlayer.getScore().addScore(score);


        }

    }

    /**
     * This method is an helper method, used to sort a LinkedHashMap based on the value
     * @param unsortedRank the unsorted rank
     * @return the sorted ArrayList
     */
    private ArrayList<PlayerColor> sortRank(LinkedHashMap<PlayerColor,Integer> unsortedRank){ //sort a linkedhashmap
        LinkedHashMap<PlayerColor,Integer> sortedRank = new LinkedHashMap<>();

        unsortedRank.entrySet().
                stream().
                    sorted(Map.Entry.comparingByValue()).
                        forEachOrdered(x -> sortedRank.put(x.getKey(),x.getValue())); //sort by ascending order

        ArrayList<PlayerColor> result;
        result = new ArrayList<>(sortedRank.keySet()); //build the arraylist from the hashmap
        Collections.reverse(result); //sort by descending order
        return result;
    }

    /**
     * This is an helper method called by updateScore()
     */
    private void updatePlayerRank(){
        ArrayList<Player> allPlayer = model.getTurnManager().getAllPlayers();
        LinkedHashMap<PlayerColor,Integer> temp = new LinkedHashMap<>();
        playerRank.clear();

        for (Player currentPlayer : allPlayer) {
            int playerScore = currentPlayer.getScore().getScore();
            temp.put(currentPlayer.getPlayerColor(), playerScore);
        }
        playerRank = sortRank(temp);

    }

    /**
     * This method is called to print the current player rank on the screen
     * @return the current player rank as a String
     */
    public String showPlayerRank(){
        StringBuilder stringBuilder = new StringBuilder();
        for (PlayerColor playerColor : playerRank) {
            Player currentPlayer = model.getPlayer(playerColor);
            String currentPlayerName = currentPlayer.getColoredName();
            int currentScore = currentPlayer.getScore().getScore();
            stringBuilder.append(currentPlayerName);
            stringBuilder.append(": ");
            stringBuilder.append(currentScore);
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
