package model;

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

    private static final int frenzyKillShotPoints[] = {8,6,4,2,1};

    /**
     * Constructor
     * @param model used to get players' info
     */
    public ScoreManager(Model model){

        this.model = model;
        //the player rank is initialized with the elements of allPlayers array list
        for(Player player : model.getTurnManager().getAllPlayers()){
            playerRank.add(player.getPlayerColor());
        }
    }

    /**
     * This method is called at the end of every turn to update the current rank
     */
    public void updateScore(){
        ArrayList<Player> allPlayer = model.getTurnManager().getAllPlayers();

        for (int i = 0; i < allPlayer.size(); i++){
            //the damage scoring is computed only if there is at least one killshot or dead player
            if(allPlayer.get(i).isKillShot() || allPlayer.get(i).isDead()){
                computePoints(allPlayer.get(i));

                //remove skull and add token
                model.getGameBoard().getKillShotTrack().removeSkull(allPlayer.get(i).getPlayerBoard().
                        getDamageCounter().getDamageCounter().
                            get(Checks.getKillshot() - 1));

                //remove the highest point
                allPlayer.get(i).getPlayerBoard().getPoints().removeHighestPoint();

                //overkill
                if(allPlayer.get(i).getPlayerBoard().getDamageCounter().getDamage() == Checks.getMaxDamage()){
                    model.getGameBoard().getKillShotTrack().addOverKill();
                }
            }
        }

        //compute double killshots
        if(model.getTurnManager().numOfKillShot() >= Checks.getDoubleKillShot()){
            model.getTurnManager().getCurrentPlayer().addScore(1);
        }

        updatePlayerRank();
    }

    /**
     * This method is called at the end of the game to produce the final rank
     */
    public void finalScore(){
        ArrayList<Player> allPlayer = model.getTurnManager().getAllPlayers();
        //maps every player to his token on the killshot track
        LinkedHashMap<PlayerColor,Integer> unsortRank;
        ArrayList<PlayerColor> killShotTrackRank;

        for (int i = 0; i < allPlayer.size(); i++){
            if(allPlayer.get(i).getPlayerBoard().getDamageCounter().getDamage() > 0)
                computePoints(allPlayer.get(i));
        }

        //compute killshottrack points
         unsortRank = model.getGameBoard().getKillShotTrack().getRank(); //todo aggiungere i frenzyTokens

        killShotTrackRank = sortRank(unsortRank);

        //add killshot track points
        for (int i = 0; i < killShotTrackRank.size(); i++){
            PlayerColor playerColor = killShotTrackRank.get(i);
            Player player = model.getPlayer(playerColor);
            player.addScore(frenzyKillShotPoints[i]); //todo controllare che non esca dai limiti dell'array
        }

        updatePlayerRank();

        ArrayList<PlayerColor> afkPlayers = new ArrayList<>();
        for (PlayerColor player : playerRank){
            if (model.getPlayer(player).isAfk()){
                afkPlayers.add(player);
            }
        }
        for (PlayerColor playerColor : afkPlayers){
            playerRank.remove(playerColor);
        }
        //todo rivedere
        model.getGameNotifier().notifyGeneric(model.getPlayer(getWinner()).getColoredName() + " has won the game!");
    }

    /**
     * This method is used to establish the game's winner
     * @return winnerColor the color of the winner
     */
    public PlayerColor getWinner(){
        return playerRank.get(0);
        //todo ties
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
        for (int i = 0; i < temp.size(); i++){
            PlayerColor currentColor = temp.get(i);
            if(!shooterColor.contains(currentColor)){
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

        for (int i = 0; i < shooterColor.size(); i++){
            PlayerColor currentColor = shooterColor.get(i);
            int currentDamage = player.getPlayerBoard().getDamageCounter().getDamageFromColor(currentColor);
            //build the linked map that maps every shooter to the damage he dealt
            currentRank.put(currentColor,currentDamage);
        }
        //sort the rank by descending order
        shooterColor = sortRank(currentRank);

        for (int i = 0; i < shooterColor.size(); i++){
            Player currentPlayer = model.getPlayer(shooterColor.get(i));
            //retrive the score for the i-th highest damage
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

        for (int i = 0; i < allPlayer.size(); i++){
            int playerScore = allPlayer.get(i).getScore().getScore();
            temp.put(allPlayer.get(i).getPlayerColor(),playerScore);
        }

        playerRank = sortRank(temp);

    }

    /**
     * This method is called to print the current player rank on the screen
     * @return the current player rank as a String
     */
    public String showPlayerRank(){
        String result = "Player Rank:\n";
        for (int i = 0; i < playerRank.size(); i++){
            Player currentPlayer = model.getPlayer(playerRank.get(i));
            String currentPlayerName = currentPlayer.getPlayerName();
            int currentScore = currentPlayer.getScore().getScore();

            result = result + currentPlayerName +" : " +currentScore +"\n";
        }
        return result;
    }
}
