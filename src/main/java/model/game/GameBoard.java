package model.game;

import model.map.Map;
import model.map.Square;
import model.player.PlayerColor;

public class GameBoard {
    /**
     * Gameboard Class. It models Adrenaline's gameboard
     */
    private KillShotTrack killShotTrack;
    private Map map;
    private Decks decks;

    /**
     * Construcotr of the class GameBoard
     * @param chosenMap the map as chosen by players
     * @param killShotCellsNumber the number of killshot cells to play with
     * @param model the game's model
     */
    public GameBoard(int chosenMap, int killShotCellsNumber, Model model){
        this.killShotTrack = new KillShotTrack(killShotCellsNumber, model);
        this.map = new Map(chosenMap);
        this.decks = new Decks(model);
        setCardsOnMap();
    }

    /**
     * Add tokens to the killshot track
     * @param playerColor the player's color of the token
     */
    public void addToken(PlayerColor playerColor){
    this.killShotTrack.removeSkull(playerColor);
    }

    /**
     * Returns the killshot track
     * @return the killshot track
     */
    public KillShotTrack getKillShotTrack(){

        return this.killShotTrack;
    }
    /**
     * Returns the map
     * @return the map
     */
    public Map getMap(){
        return this.map;
    }
    /**
     * Returns the decks
     * @return the deks
     */
    public Decks getDecks(){
        return this.decks;
    }

    /**
     * Places cards on the map
     */
    public void setCardsOnMap(){
        for(int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                Square square = getMap().getMap()[i][j];
                if (square != null) {
                    if (square.isSpawn) {
                        /*while (square.hasEmptyWeaponSlot()) {
                            square.setWeapon(getDecks().drawWeapon());
                            }*/
                        for (int k = 0; k < 3; k++) {
                            if(square.getWeapon()[k] == null){
                                square.getWeapon()[k] = getDecks().drawWeapon();
                            }
                        }

                    } else if (square.hasEmptyAmmoSlot()) {
                        square.setAmmo(getDecks().drawAmmoCard());
                    }
                }
            }
        }
    }

}
