package model;

import model.player.Player;
import model.player.PlayerColor;

import java.util.*;

public class ScoreManager {

    private final Model model;

    private ArrayList<PlayerColor> playerRank = new ArrayList<>();

    public ScoreManager(Model model){
        this.model = model;
        //playerRank = model.getTurnManager().ge
    }

    public void updateScore(){
        ArrayList<Player> allPlayer = model.getTurnManager().getAllPlayers();
        //todo mettere un if che verifichi se nel turno corrente almeno un giocatore è morto
        for (int i = 0; i < allPlayer.size(); i++){
            if(allPlayer.get(i).isDead()){
                computeScore(allPlayer.get(i)); //se un giocatore è morto, calcola i punteggi
            }
        }

        updatePlayerRank();
    }

    private void computeScore(Player player){

        ArrayList<PlayerColor> temp = player.getPlayerBoard().getDamageCounter().getDamageCounter(); //lista di tutti i giocatori che hanno fatto danno
        ArrayList<PlayerColor> shooterColor = new ArrayList<>(); //lista di tutti i giocatori che hanno fatto danno , senza duplicati
        LinkedHashMap<PlayerColor,Integer> currentRank = new LinkedHashMap<>(); //classifica corrente

        //cancella duplicati
        for (int i = 0; i < temp.size(); i++){
            PlayerColor currentColor = temp.get(i);
            if(!shooterColor.contains(currentColor)){
                shooterColor.add(currentColor);
            }
        }
        //aggiunge il primo danno
        int firstBloodDamage = player.getPlayerBoard().getPoints().getFirstBlood(); //retrieve the damage to give for the first blood
        Player firstBloodPlayer = model.getPlayer(shooterColor.get(0)); //retrieve the player who made the first blood
        firstBloodPlayer.getScore().addScore(firstBloodDamage); //add damage

        Collections.reverse(shooterColor);

        for (int i = 0; i < shooterColor.size(); i++){
            PlayerColor currentColor = shooterColor.get(i);
            int currentDamage = player.getPlayerBoard().getDamageCounter().getDamageFromColor(currentColor);
            currentRank.put(currentColor,currentDamage); //build the linked map that maps every shooter to its damage
        }

        shooterColor = sortRank(currentRank); //sort the rank by descending order

        for (int i = 0; i < shooterColor.size(); i++){
            Player currentPlayer = model.getPlayer(shooterColor.get(i));
            int score = player.getPlayerBoard().getPoints().getPoint(i); //retrive the score for the i-th highest damage
            currentPlayer.getScore().addScore(score); //add damage
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
