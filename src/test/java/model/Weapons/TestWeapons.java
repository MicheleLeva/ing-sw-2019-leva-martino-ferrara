package model.Weapons;

import model.Ammo;
import model.Model;
import model.cards.weapons.Heatseeker;
import model.cards.weapons.LockRifle;
import model.cards.weapons.WeaponTree;
import model.player.Player;
import model.player.PlayerColor;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class TestWeapons {
    Ammo pickUpCost = new Ammo(0,0,1);
    Ammo baseCost = new Ammo(0,1,0);
    Player player1 = new Player("player1", PlayerColor.BLUE);
    Player player2 = new Player("player2",PlayerColor.YELLOW);
    Player player3 = new Player("player3",PlayerColor.GREEN);
    Model model ;
    Heatseeker heatseeker;

    @Before
    public void initWeapons(){
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        players.add(player3);
        this.model = new Model(players,5);
        heatseeker = new Heatseeker("HEATSEEKER",pickUpCost,baseCost,1,1,1,model);
        heatseeker.setWeaponTree(new WeaponTree("src/resources/Heatseeker.json"));

    }

    @Test
    public void testWeapons(){
        model.getCurrent().setSelectedWeapon(heatseeker);
        heatseeker.askBaseRequirements(player1);
        ArrayList<Player> selectedTargets = new ArrayList<>();
        model.getCurrent().setSelectedBaseTargets(selectedTargets);
        heatseeker.askBaseRequirements(player1);
    }
}
