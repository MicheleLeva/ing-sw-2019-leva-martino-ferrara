package model;

import controller.Checks;
import model.player.Player;
import model.player.PlayerColor;

import java.util.*;

public class ScoreManager {

    private final Model model;
    //ranking of the player
    private ArrayList<PlayerColor> playerRank = new ArrayList<>();

    private static final int frenzyKillShotPoints[] = {8,6,4,2,1};

    public ScoreManager(Model model){

        this.model = model;

        for(Player player : model.getTurnManager().getAllPlayers()){
            playerRank.add(player.getPlayerColor());
        }
    }

    public void updateScore(){
        ArrayList<Player> allPlayer = model.getTurnManager().getAllPlayers();

        for (int i = 0; i < allPlayer.size(); i++){
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

    public void updateScoreFrenzy(){
        ArrayList<Player> allPlayer = model.getTurnManager().getAllPlayers();
        //maps every player to his token on the killshot track
        LinkedHashMap<PlayerColor,Integer> unsortRank;
        ArrayList<PlayerColor> killShotTrackRank;

        for (int i = 0; i < allPlayer.size(); i++){
            if(allPlayer.get(i).getPlayerBoard().getDamageCounter().getDamage() > 0)
                computePoints(allPlayer.get(i));
        }

        //compute killshottrack points
         unsortRank = model.getGameBoard().getKillShotTrack().getRank();

        killShotTrackRank = sortRank(unsortRank);

        //add killshot track points
        for (int i = 0; i < killShotTrackRank.size(); i++){
            PlayerColor playerColor = killShotTrackRank.get(i);
            Player player = model.getPlayer(playerColor);
            player.addScore(frenzyKillShotPoints[i]);
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

    public PlayerColor getWinner(){
        return playerRank.get(0);
        //todo ties
    }

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
