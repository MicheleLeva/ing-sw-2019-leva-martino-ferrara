package model.cards;

import model.Ammo;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestAmmoCard {

    private AmmoCard ammoCard = new AmmoCard(new Ammo(1,1,1),true);

    @Test
    public void getAmmo() {
        Ammo ammo = ammoCard.getAmmo();
        assertEquals(1, ammo.getRed());
        assertEquals(1, ammo.getYellow());
        assertEquals(1, ammo.getBlue());
    }

    @Test
    public void hasPowerUp() {
        assertTrue(ammoCard.hasPowerUp());
    }
}