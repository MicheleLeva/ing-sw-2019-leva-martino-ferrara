package model.game;


import model.cards.*;
import model.cards.powerups.*;
import model.cards.weapons.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.Random;
import java.util.ArrayList;

public class Decks {

    private ArrayList<Weapon> weaponsDeck;
    private ArrayList<PowerUp> powerUpDeck;
    private ArrayList<AmmoCard> ammoCardDeck;
    private ArrayList<PowerUp> discardedPowerUpDeck;
    private ArrayList<AmmoCard> discardedAmmoCardDeck;
    private final Model model;
    private final Random rand = new Random();

    public Decks(Model model) {
        this.model = model;

        weaponsDeck = new ArrayList<>();
        powerUpDeck = new ArrayList<>();
        ammoCardDeck = new ArrayList<>();
        discardedAmmoCardDeck = new ArrayList<>();
        discardedPowerUpDeck = new ArrayList<>();
        createPowerUpDeck();
        createAmmoCardDeck();
        addBasicWeapons();
        addAlternativeWeapons();
        addOptionalWeapons1();
        addOptionalWeapons2();
    }

    /**
     * returns a random weapon from the weapon deck
     */
    public Weapon drawWeapon() {
        if (weaponsDeck.size() > 0)
            return weaponsDeck.remove(rand.nextInt(weaponsDeck.size()));
        else
            return null;

    }

    /**
     * returns a random powerup from the powerup deck
     */
    public PowerUp drawPowerUp() {

        if (!powerUpDeck.isEmpty()) {
            return powerUpDeck.remove(rand.nextInt(powerUpDeck.size()));
        } else
            powerUpDeck = new ArrayList<>(discardedPowerUpDeck);
        discardedPowerUpDeck.clear();
        return powerUpDeck.remove(rand.nextInt(powerUpDeck.size()));
    }

    /**
     * returns a random Ammo Card from the Ammo Card deck
     */
    public AmmoCard drawAmmoCard() {

        if (ammoCardDeck.size() > 0) {
            return ammoCardDeck.remove(rand.nextInt(ammoCardDeck.size()));
        } else
            ammoCardDeck = new ArrayList<>(discardedAmmoCardDeck);
        discardedAmmoCardDeck.clear();
        return ammoCardDeck.remove(rand.nextInt(ammoCardDeck.size()));
    }

    /**
     * returns an arrayList containing the weapons in weaponDeck
     */
    public ArrayList<Weapon> getWeaponsDeck() {
        return weaponsDeck;

    }
    /**
     * returns an arrayList containing the powerUps in powerUp deck
     */
    public ArrayList<PowerUp> getPowerUpDeck() {
        return powerUpDeck;

    }
    /**
     * returns an arrayList containing the Ammo cards in AmmoCard deck
     */
    public ArrayList<AmmoCard> getAmmoCardDeck() {
        return ammoCardDeck;

    }
    /**
     * returns an arrayList containing the discarded powerUps
     */
    public ArrayList<PowerUp> getDiscardedPowerUpDeck() {
        return discardedPowerUpDeck;

    }
    /**
     * returns an arrayList containing the discarded Ammo cards
     */
    public ArrayList<AmmoCard> getDiscardedAmmoCardDeck() {
        return discardedAmmoCardDeck;

    }

    /**
     * Fills the powerUp deck reading from a json file
     */
    private void createPowerUpDeck() {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("src/resources/powerUps.json"));
            JSONObject myJo = (JSONObject) obj;
            JSONArray myArray = (JSONArray) myJo.get("powerUps");
            for (int i = 0; i < myArray.size(); i++) {
                JSONObject result1 = (JSONObject) myArray.get(i);
                if (result1.get("name").equals("targetingScope")) {
                    if (result1.get("color").equals("RED"))
                        powerUpDeck.add(new TargetingScope(model, AmmoColor.RED));
                    if (result1.get("color").equals("BLUE"))
                        powerUpDeck.add(new TargetingScope(model, AmmoColor.BLUE));
                    if (result1.get("color").equals("YELLOW"))
                        powerUpDeck.add(new TargetingScope(model, AmmoColor.YELLOW));
                }
                if (result1.get("name").equals("newton")) {
                    if (result1.get("color").equals("RED"))
                        powerUpDeck.add(new Newton(model, AmmoColor.RED));
                    if (result1.get("color").equals("BLUE"))
                        powerUpDeck.add(new Newton(model, AmmoColor.BLUE));
                    if (result1.get("color").equals("YELLOW"))
                        powerUpDeck.add(new Newton(model, AmmoColor.YELLOW));
                }
                if (result1.get("name").equals("tagbackGrenade")) {
                    if (result1.get("color").equals("RED"))
                        powerUpDeck.add(new TagbackGrenade(model, AmmoColor.RED));
                    if (result1.get("color").equals("BLUE"))
                        powerUpDeck.add(new TagbackGrenade(model, AmmoColor.BLUE));
                    if (result1.get("color").equals("YELLOW"))
                        powerUpDeck.add(new TagbackGrenade(model, AmmoColor.YELLOW));
                }
                if (result1.get("name").equals("teleporter")) {
                    if (result1.get("color").equals("RED"))
                        powerUpDeck.add(new Teleporter(model, AmmoColor.RED));
                    if (result1.get("color").equals("BLUE"))
                        powerUpDeck.add(new Teleporter(model, AmmoColor.BLUE));
                    if (result1.get("color").equals("YELLOW"))
                        powerUpDeck.add(new Teleporter(model, AmmoColor.YELLOW));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Fills the Ammo Card deck reading from a json file
     */
    private void createAmmoCardDeck() {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("src/resources/ammoCards.json"));
            JSONObject myJo = (JSONObject) obj;
            JSONArray myArray = (JSONArray) myJo.get("ammoCards");
            for (int i = 0; i < myArray.size(); i++) {
                JSONObject result1 = (JSONObject) myArray.get(i);
                Ammo ammo = new Ammo(((Long) result1.get("RED")).intValue(), ((Long) result1.get("BLUE")).intValue(), ((Long) result1.get("YELLOW")).intValue());
                ammoCardDeck.add(new AmmoCard(ammo, (boolean) result1.get("powerUps")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Fills the weapons deck with weapons that have only the Base Fire Mode
     */
    private void addBasicWeapons() {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("src/resources/weaponCards.json"));
            JSONObject myJo = (JSONObject) obj;
            JSONArray myArray = (JSONArray) myJo.get("weaponCards");
            JSONObject result1 = (JSONObject) myArray.get(0);
            JSONArray myArray2 = (JSONArray) result1.get("basic");
            for (int i = 0; i < myArray2.size(); i++) {
                JSONObject result2 = (JSONObject) myArray2.get(i);
                String name = (String) result2.get("name");
                Ammo baseAmmo = new Ammo( ((Long)result2.get("RED")).intValue(), ((Long) result2.get("BLUE")).intValue(), ((Long)result2.get("YELLOW")).intValue());
                Ammo pickUpAmmo = new Ammo(((Long) result2.get("PRED")).intValue(), ((Long) result2.get("PBLUE")).intValue(), ((Long) result2.get("PYELLOW")).intValue());
                int baseDamage = ((Long) result2.get("DAMAGE")).intValue();
                int baseMarks = ((Long) result2.get("MARKS")).intValue();
                int baseTargetsNumber = ((Long) result2.get("TARGETSNUMBER")).intValue();
                if(result2.get("name").equals("HEATSEEKER")) {
                     weaponsDeck.add(new Heatseeker(name,pickUpAmmo,baseAmmo,baseDamage,baseMarks,baseTargetsNumber,model));
                     weaponsDeck.get(weaponsDeck.size()-1).setWeaponTree(new WeaponTree("src/resources/Heatseeker.json"));

                }
                if(result2.get("name").equals("WHISPER")){
                    weaponsDeck.add(new Whisper(name,pickUpAmmo,baseAmmo,baseDamage,baseMarks,baseTargetsNumber,model));
                    weaponsDeck.get(weaponsDeck.size()-1).setWeaponTree(new WeaponTree("src/resources/Whisper.json"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    /**
     * Fills the weapons deck with weapons that have an alternative Fire Mode
     */
    private void addAlternativeWeapons() {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("src/resources/weaponCards.json"));
            JSONObject myJo = (JSONObject) obj;
            JSONArray myArray = (JSONArray) myJo.get("weaponCards");
            JSONObject result1 = (JSONObject) myArray.get(1);
            JSONArray myArray2 = (JSONArray) result1.get("alternative");
            for (int i = 0; i < myArray2.size(); i++) {
                JSONObject result2 = (JSONObject) myArray2.get(i);
                String name = (String) result2.get("name");
                Ammo baseAmmo = new Ammo(((Long) result2.get("RED")).intValue(), ((Long) result2.get("BLUE")).intValue(), ((Long) result2.get("YELLOW")).intValue());
                Ammo alternativeAmmo = new Ammo(((Long) result2.get("RED1")).intValue(), ((Long) result2.get("BLUE1")).intValue(), ((Long) result2.get("YELLOW1")).intValue());
                Ammo pickUpAmmo = new Ammo(((Long) result2.get("PRED")).intValue(), ((Long) result2.get("PBLUE")).intValue(), ((Long) result2.get("PYELLOW")).intValue());
                int baseDamage = ((Long) result2.get("DAMAGE")).intValue();
                int alternativeDamage = ((Long) result2.get("DAMAGE1")).intValue();
                int baseMarks = ((Long) result2.get("MARKS")).intValue();
                int alternativeMarks = ((Long) result2.get("MARKS1")).intValue();
                int baseTargetsNumber = ((Long) result2.get("TARGETSNUMBER")).intValue();
                int alternativeTargetsNumber = ((Long) result2.get("TARGETSNUMBER1")).intValue();
                if(result2.get("name").equals("ELECTROSCYTHE")) {
                        weaponsDeck.add(new Electroscythe(name,pickUpAmmo,baseAmmo,alternativeAmmo,baseDamage,alternativeDamage,baseMarks,alternativeMarks,baseTargetsNumber,alternativeTargetsNumber,model));
                        weaponsDeck.get(weaponsDeck.size()-1).setWeaponTree(new WeaponTree("src/resources/Electroscythe.json"));
                }
                if(result2.get("name").equals("TRACTOR BEAM")) {
                    weaponsDeck.add(new Tractorbeam(name,pickUpAmmo,baseAmmo,alternativeAmmo,baseDamage,alternativeDamage,baseMarks,alternativeMarks,baseTargetsNumber,alternativeTargetsNumber,model));
                    weaponsDeck.get(weaponsDeck.size()-1).setWeaponTree(new WeaponTree("src/resources/Tractorbeam.json"));
                }
                if(result2.get("name").equals("FURNACE")) {
                    weaponsDeck.add(new Furnace(name,pickUpAmmo,baseAmmo,alternativeAmmo,baseDamage,alternativeDamage,baseMarks,alternativeMarks,baseTargetsNumber,alternativeTargetsNumber,model));
                    weaponsDeck.get(weaponsDeck.size()-1).setWeaponTree(new WeaponTree("src/resources/Furnace.json"));
                }
                if(result2.get("name").equals("HELLION")) {
                    weaponsDeck.add(new Hellion(name,pickUpAmmo,baseAmmo,alternativeAmmo,baseDamage,alternativeDamage,baseMarks,alternativeMarks,baseTargetsNumber,alternativeTargetsNumber,model));
                    weaponsDeck.get(weaponsDeck.size()-1).setWeaponTree(new WeaponTree("src/resources/Hellion.json"));
                }
                if(result2.get("name").equals("FLAMETHROWER")) {
                    weaponsDeck.add(new Flamethrower(name,pickUpAmmo,baseAmmo,alternativeAmmo,baseDamage,alternativeDamage,baseMarks,alternativeMarks,baseTargetsNumber,alternativeTargetsNumber,model));
                    weaponsDeck.get(weaponsDeck.size()-1).setWeaponTree(new WeaponTree("src/resources/Flamethrower.json"));
                }
                if(result2.get("name").equals("ZX-2")) {
                    weaponsDeck.add(new ZX2(name,pickUpAmmo,baseAmmo,alternativeAmmo,baseDamage,alternativeDamage,baseMarks,alternativeMarks,baseTargetsNumber,alternativeTargetsNumber,model));
                    weaponsDeck.get(weaponsDeck.size()-1).setWeaponTree(new WeaponTree("src/resources/Zx2.json"));
                }
                if(result2.get("name").equals("SHOTGUN")) {
                    weaponsDeck.add(new Shotgun(name,pickUpAmmo,baseAmmo,alternativeAmmo,baseDamage,alternativeDamage,baseMarks,alternativeMarks,baseTargetsNumber,alternativeTargetsNumber,model));
                    weaponsDeck.get(weaponsDeck.size()-1).setWeaponTree(new WeaponTree("src/resources/Shotgun.json"));
                }
                if(result2.get("name").equals("POWERGLOVE")) {
                    weaponsDeck.add(new Powerglove(name,pickUpAmmo,baseAmmo,alternativeAmmo,baseDamage,alternativeDamage,baseMarks,alternativeMarks,baseTargetsNumber,alternativeTargetsNumber,model));
                    weaponsDeck.get(weaponsDeck.size()-1).setWeaponTree(new WeaponTree("src/resources/Powerglove.json"));
                }
                if(result2.get("name").equals("RAILGUN")) {
                    weaponsDeck.add(new RailGun(name,pickUpAmmo,baseAmmo,alternativeAmmo,baseDamage,alternativeDamage,baseMarks,alternativeMarks,baseTargetsNumber,alternativeTargetsNumber,model));
                    weaponsDeck.get(weaponsDeck.size()-1).setWeaponTree(new WeaponTree("src/resources/Railgun.json"));
                }
                if(result2.get("name").equals("SHOCKWAVE")) {
                    weaponsDeck.add(new Shockwave(name,pickUpAmmo,baseAmmo,alternativeAmmo,baseDamage,alternativeDamage,baseMarks,alternativeMarks,baseTargetsNumber,alternativeTargetsNumber,model));
                    weaponsDeck.get(weaponsDeck.size()-1).setWeaponTree(new WeaponTree("src/resources/Shockwave.json"));
                }
                if(result2.get("name").equals("SLEDGEHAMMER")) {
                    weaponsDeck.add(new Sledgehammer(name,pickUpAmmo,baseAmmo,alternativeAmmo,baseDamage,alternativeDamage,baseMarks,alternativeMarks,baseTargetsNumber,alternativeTargetsNumber,model));
                    weaponsDeck.get(weaponsDeck.size()-1).setWeaponTree(new WeaponTree("src/resources/Sledgehammer.json"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    /**
     * Fills the weapons deck with weapons that have only one optional Fire Mode
     */
    private void addOptionalWeapons1() {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("src/resources/weaponCards.json"));
            JSONObject myJo = (JSONObject) obj;
            JSONArray myArray = (JSONArray) myJo.get("weaponCards");
            JSONObject result1 = (JSONObject) myArray.get(2);
            JSONArray myArray2 = (JSONArray) result1.get("optional1");
            for (int i = 0; i < myArray2.size(); i++) {
                JSONObject result2 = (JSONObject) myArray2.get(i);
                String name = (String) result2.get("name");
                Ammo baseAmmo = new Ammo(((Long) result2.get("RED")).intValue(), ((Long) result2.get("BLUE")).intValue(), ((Long) result2.get("YELLOW")).intValue());
                Ammo optionalAmmo1 = new Ammo(((Long) result2.get("RED1")).intValue(), ((Long) result2.get("BLUE1")).intValue(), ((Long) result2.get("YELLOW1")).intValue());
                Ammo pickUpAmmo = new Ammo(((Long) result2.get("PRED")).intValue(), ((Long) result2.get("PBLUE")).intValue(), ((Long) result2.get("PYELLOW")).intValue());
                int baseDamage = ((Long) result2.get("DAMAGE")).intValue();
                int optionalDamage1 = ((Long) result2.get("DAMAGE1")).intValue();
                int baseMarks = ((Long) result2.get("MARKS")).intValue();
                int optionalMarks1 = ((Long) result2.get("MARKS1")).intValue();
                int baseTargetsNumber = ((Long) result2.get("TARGETSNUMBER")).intValue();
                int optionalTargetsNumber1 = ((Long) result2.get("TARGETSNUMBER1")).intValue();
                if(result2.get("name").equals("LOCK RIFLE")) {
                    weaponsDeck.add(new LockRifle(name,pickUpAmmo,baseAmmo,optionalAmmo1,baseDamage,optionalDamage1,baseMarks,optionalMarks1,baseTargetsNumber,optionalTargetsNumber1,model));
                    weaponsDeck.get(weaponsDeck.size()-1).setWeaponTree(new WeaponTree("src/resources/Lockrifle.json"));
                }
                if(result2.get("name").equals("GRENADE LAUNCHER")) {
                    weaponsDeck.add(new Grenadelauncher(name,pickUpAmmo,baseAmmo,optionalAmmo1,baseDamage,optionalDamage1,baseMarks,optionalMarks1,baseTargetsNumber,optionalTargetsNumber1,model));
                    weaponsDeck.get(weaponsDeck.size()-1).setWeaponTree(new WeaponTree("src/resources/Grenadelauncher.json"));
                }
                if(result2.get("name").equals("VORTEX")) {
                    weaponsDeck.add(new Vortexcannon(name,pickUpAmmo,baseAmmo,optionalAmmo1,baseDamage,optionalDamage1,baseMarks,optionalMarks1,baseTargetsNumber,optionalTargetsNumber1,model));
                    weaponsDeck.get(weaponsDeck.size()-1).setWeaponTree(new WeaponTree("src/resources/Vortexcannon.json"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    /**
     * Fills the weapons deck with weapons that have two optional Fire Modes
     */
    private void addOptionalWeapons2() {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("src/resources/weaponCards.json"));
            JSONObject myJo = (JSONObject) obj;
            JSONArray myArray = (JSONArray) myJo.get("weaponCards");
            JSONObject result1 = (JSONObject) myArray.get(3);
            JSONArray myArray2 = (JSONArray) result1.get("optional2");
            for (int i = 0; i < myArray2.size(); i++) {
                JSONObject result2 = (JSONObject) myArray2.get(i);
                String name = (String) result2.get("name");
                Ammo baseAmmo = new Ammo(((Long) result2.get("RED")).intValue(), ((Long) result2.get("BLUE")).intValue(), ((Long) result2.get("YELLOW")).intValue());
                Ammo optionalAmmo1 = new Ammo(((Long) result2.get("RED1")).intValue(), ((Long) result2.get("BLUE1")).intValue(), ((Long) result2.get("YELLOW1")).intValue());
                Ammo optionalAmmo2 = new Ammo(((Long) result2.get("RED2")).intValue(), ((Long) result2.get("BLUE2")).intValue(), ((Long) result2.get("YELLOW2")).intValue());
                Ammo pickUpAmmo = new Ammo(((Long) result2.get("PRED")).intValue(), ((Long) result2.get("PBLUE")).intValue(), ((Long) result2.get("PYELLOW")).intValue());
                int baseDamage = ((Long) result2.get("DAMAGE")).intValue();
                int optionalDamage1 = ((Long) result2.get("DAMAGE1")).intValue();
                int optionalDamage2 = ((Long) result2.get("DAMAGE2")).intValue();
                int baseMarks = ((Long) result2.get("MARKS")).intValue();
                int optionalMarks1 = ((Long) result2.get("MARKS1")).intValue();
                int optionalMarks2 = ((Long) result2.get("MARKS2")).intValue();
                int baseTargetsNumber = ((Long) result2.get("TARGETSNUMBER")).intValue();
                int optionalTargetsNumber1 = ((Long) result2.get("TARGETSNUMBER1")).intValue();
                int optionalTargetsNumber2 = ((Long) result2.get("TARGETSNUMBER2")).intValue();

                if(result2.get("name").equals("MACHINE GUN")) {
                    weaponsDeck.add(new Machinegun(name,pickUpAmmo,baseAmmo,optionalAmmo1,optionalAmmo2,baseDamage,optionalDamage1,optionalDamage2,baseMarks,optionalMarks1,optionalMarks2,baseTargetsNumber,optionalTargetsNumber1,optionalTargetsNumber2,model));
                    weaponsDeck.get(weaponsDeck.size()-1).setWeaponTree(new WeaponTree("src/resources/Machinegun.json"));
                }
                if(result2.get("name").equals("THOR")) {
                    weaponsDeck.add(new Thor(name,pickUpAmmo,baseAmmo,optionalAmmo1,optionalAmmo2,baseDamage,optionalDamage1,optionalDamage2,baseMarks,optionalMarks1,optionalMarks2,baseTargetsNumber,optionalTargetsNumber1,optionalTargetsNumber2,model));
                    weaponsDeck.get(weaponsDeck.size()-1).setWeaponTree(new WeaponTree("src/resources/Thor.json"));
                }
                if(result2.get("name").equals("PLASMA GUN")) {
                    weaponsDeck.add(new Plasmagun(name,pickUpAmmo,baseAmmo,optionalAmmo1,optionalAmmo2,baseDamage,optionalDamage1,optionalDamage2,baseMarks,optionalMarks1,optionalMarks2,baseTargetsNumber,optionalTargetsNumber1,optionalTargetsNumber2,model));
                    weaponsDeck.get(weaponsDeck.size()-1).setWeaponTree(new WeaponTree("src/resources/Plasmagun.json"));
                }
                if(result2.get("name").equals("ROCKET LAUNCHER")) {
                    weaponsDeck.add(new Rocketlauncher(name,pickUpAmmo,baseAmmo,optionalAmmo1,optionalAmmo2,baseDamage,optionalDamage1,optionalDamage2,baseMarks,optionalMarks1,optionalMarks2,baseTargetsNumber,optionalTargetsNumber1,optionalTargetsNumber2,model));
                    weaponsDeck.get(weaponsDeck.size()-1).setWeaponTree(new WeaponTree("src/resources/Rocketlauncher.json"));
                }
                if(result2.get("name").equals("CYBERBLADE")) {
                    weaponsDeck.add(new Cyberblade(name,pickUpAmmo,baseAmmo,optionalAmmo1,optionalAmmo2,baseDamage,optionalDamage1,optionalDamage2,baseMarks,optionalMarks1,optionalMarks2,baseTargetsNumber,optionalTargetsNumber1,optionalTargetsNumber2,model));
                    weaponsDeck.get(weaponsDeck.size()-1).setWeaponTree(new WeaponTree("src/resources/Cyberblade.json"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}