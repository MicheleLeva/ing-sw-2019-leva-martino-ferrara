package model.cards;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestWeaponTree {

    WeaponTree weaponTreeTest;

    @Before
    public void initWeaponTreeTest(){
        weaponTreeTest = new WeaponTree("src/resources/Cyberblade.json");
    }

    @Test
    public void getRoot() {
        System.out.println(weaponTreeTest.getRoot().getData().getType());
    }
/*
    @Test
    public void updateLastAction() {
    }

    @Test
    public void updateLastActionPerformed() {
    }

    @Test
    public void availableAction() {
    }

    @Test
    public void isActionEnded() {
    }

    @Test
    public void resetAction() {
    }

    @Test
    public void endAction() {
    }

    @Test
    public void getType() {
    }

    @Test
    public void getEffectName() {
    }

    @Test
    public void getLastActionPerformed() {
    }

    @Test
    public void getLastAction() {
    }*/
}