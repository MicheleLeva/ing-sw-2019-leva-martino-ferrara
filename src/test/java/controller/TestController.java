package controller;

import model.game.Model;
import model.player.Player;
import model.player.PlayerColor;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Tests the correct initialization of the Controller class
 * @author Michele Leva, Stefano Martino, Marco Maria Ferrara
 */
public class TestController {

    /**
     * Tests the model getter in Controller class
     */
    @Test
    public void getModel() {
        ArrayList<Player> players = new ArrayList<>();
        Player player1 = new Player("player1", PlayerColor.BLUE);
        Player player2 = new Player("player2",PlayerColor.YELLOW);
        Player player3 = new Player("player3",PlayerColor.GREEN);
        players.add(player1);
        players.add(player2);
        players.add(player3);
        Model model = new Model(players,8);
        Controller controller = new Controller(model);
        assertSame(model,controller.getModel());
    }
}