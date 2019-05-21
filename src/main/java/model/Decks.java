package model;


import model.cards.*;
import model.cards.powerups.PowerUp;
import model.cards.weapons.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.ArrayList;

public class Decks {

    private ArrayList<Weapon> weaponsDeck;
    private ArrayList<PowerUp> powerUpDeck;
    private ArrayList<AmmoCard> ammoCardDeck;
    private ArrayList<PowerUp> discardedPowerUpDeck;
    private ArrayList<AmmoCard> discardedAmmoCardDeck;
    private Model model;
    private Random rand = new Random();

    private static Decks decksInstance = null;


    public static Decks getDecksInstance() {
        if (decksInstance == null)
            decksInstance = new Decks();
        return decksInstance;

    }


    public Decks() {
        weaponsDeck = new ArrayList<>();
        powerUpDeck = new ArrayList<>();
        ammoCardDeck = new ArrayList<>();
        discardedAmmoCardDeck = new ArrayList<>();
        discardedPowerUpDeck = new ArrayList<>();

    }

    //todo
    public Weapon drawWeapon() {
        if (weaponsDeck.size() > 0)
            return weaponsDeck.remove(rand.nextInt(weaponsDeck.size()));
        else
            return null;

    }

    public PowerUp drawPowerUp() {

        if (powerUpDeck.size() > 0) {
            return powerUpDeck.remove(rand.nextInt(powerUpDeck.size()));
        } else
            powerUpDeck = new ArrayList(discardedPowerUpDeck);
        discardedPowerUpDeck.clear();
        return powerUpDeck.remove(rand.nextInt(powerUpDeck.size()));
    }

    //todo rivedere
    public AmmoCard drawAmmoCard() {

        if (ammoCardDeck.size() > 0) {
            return ammoCardDeck.remove(rand.nextInt(ammoCardDeck.size()));
        } else
            ammoCardDeck = new ArrayList(discardedAmmoCardDeck);
        discardedAmmoCardDeck.clear();
        return ammoCardDeck.remove(rand.nextInt(ammoCardDeck.size()));
    }

    public ArrayList<Weapon> getWeaponsDeck() {
        return weaponsDeck;

    }

    public ArrayList<PowerUp> getPowerUpDeck() {
        return powerUpDeck;

    }

    public ArrayList<AmmoCard> getAmmoCardDeck() {
        return ammoCardDeck;

    }

    public ArrayList<PowerUp> getDiscardedPowerUpDeck() {
        return discardedPowerUpDeck;

    }

    public ArrayList<AmmoCard> getDiscardedAmmoCardDeck() {
        return discardedAmmoCardDeck;

    }

    //todo
   /* private void createPowerUpDeck() {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("src/resources/powerUps.json"));
            JSONObject myJo = (JSONObject) obj;
            JSONArray myArray = (JSONArray) myJo.get("powerUps");
            for (int i = 0; i < myArray.size(); i++) {
                JSONObject result1 = (JSONObject) myArray.get(i);
                if (result1.get("nome").equals("targetingScope"))
                    powerUpDeck.add(new TargetingScope(AmmoColor.valueOf((String) result1.get("color"))));
                if (result1.get("nome").equals("newton"))
                    powerUpDeck.add(new TargetingScope(AmmoColor.valueOf((String) result1.get("color"))));
                if (result1.get("nome").equals("tagbackGrenade"))
                    powerUpDeck.add(new TargetingScope(AmmoColor.valueOf((String) result1.get("color"))));
                if (result1.get("nome").equals("teleporter"))
                    powerUpDeck.add(new TargetingScope(AmmoColor.valueOf((String) result1.get("color"))));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void createAmmoCardDeck() {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("src/resources/ammoCards.json"));
            JSONObject myJo = (JSONObject) obj;
            JSONArray myArray = (JSONArray) myJo.get("ammoCards");
            for (int i = 0; i < myArray.size(); i++) {
                JSONObject result1 = (JSONObject) myArray.get(i);
                ammoCardDeck.add(new AmmoCard((int) result1.get("RED"), (int) result1.get("BLUE"), (int) result1.get("YELLOW"), (boolean) result1.get("powerUps")));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/


    public void addBasicWeapons() {
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
                Ammo baseAmmo = new Ammo((int) result2.get("RED"), (int) result2.get("BLUE"), (int) result2.get("YELLOW"));
                Ammo pickUpAmmo = new Ammo((int) result2.get("PRED"), (int) result2.get("PBLUE"), (int) result2.get("PYELLOW"));
                int baseDamage = (int) result2.get("DAMAGE");
                int baseMarks = (int) result2.get("MARKS");
                int baseTargetsNumber = (int) result2.get("TARGETSNUMBER");
                if(result2.get("name").equals("HEATSEEKER")) {
                     weaponsDeck.add(new Heatseeker(name,pickUpAmmo,baseAmmo,baseDamage,baseMarks,baseTargetsNumber,model));
                     weaponsDeck.get(weaponsDeck.size()-1).setWeaponTree(new WeaponTree("src/resources/Heatseeker.json"));

                }
                if(result2.get("name").equals("WHISPER")){
                    weaponsDeck.add(new Whisper(name,pickUpAmmo,baseAmmo,baseDamage,baseMarks,baseTargetsNumber,model));
                    weaponsDeck.get(weaponsDeck.size()-1).setWeaponTree(new WeaponTree("src/resources/Whisper.json"));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void addAlternativeWeapons() {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("src/resources/weaponCards.json"));
            JSONObject myJo = (JSONObject) obj;
            JSONArray myArray = (JSONArray) myJo.get("weaponCards");
            JSONObject result1 = (JSONObject) myArray.get(0);
            JSONArray myArray2 = (JSONArray) result1.get("alternative");
            for (int i = 0; i < myArray2.size(); i++) {
                JSONObject result2 = (JSONObject) myArray2.get(i);
                String name = (String) result2.get("name");
                Ammo baseAmmo = new Ammo((int) result2.get("RED"), (int) result2.get("BLUE"), (int) result2.get("YELLOW"));
                Ammo alternativeAmmo = new Ammo((int) result2.get("RED1"), (int) result2.get("BLUE1"), (int) result2.get("YELLOW1"));
                Ammo pickUpAmmo = new Ammo((int) result2.get("PRED"), (int) result2.get("PBLUE"), (int) result2.get("PYELLOW"));
                int baseDamage = (int) result2.get("DAMAGE");
                int alternativeDamage = (int) result2.get("DAMAGE1");
                int baseMarks = (int) result2.get("MARKS");
                int alternativeMarks = (int) result2.get("MARKS1");
                int baseTargetsNumber = (int) result2.get("TARGETSNUMBER");
                int alternativeTargetsNumber = (int) result2.get("TARGETSNUMBER1");
                if(result2.get("name").equals("ELECTROSCYTHE")) {
                        weaponsDeck.add(new Electroscythe(name,pickUpAmmo,baseAmmo,alternativeAmmo,baseDamage,alternativeDamage,baseMarks,alternativeMarks,baseTargetsNumber,alternativeTargetsNumber,model));
                        weaponsDeck.get(weaponsDeck.size()-1).setWeaponTree(new WeaponTree("src/resources/Heatseeker.json"));
                }
                if(result2.get("name").equals("TRACTOR BEAM")) {
                    //weaponsDeck.add(new Tractorbeam(name,pickUpAmmo,baseAmmo,alternativeAmmo,baseDamage,alternativeDamage,baseMarks,alternativeMarks,baseTargetsNumber,alternativeTargetsNumber,model));
                    weaponsDeck.get(weaponsDeck.size()-1).setWeaponTree(new WeaponTree("src/resources/Tracktorbeam.json"));
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
                    //weaponsDeck.add(new Flamethrower(name,pickUpAmmo,baseAmmo,alternativeAmmo,baseDamage,alternativeDamage,baseMarks,alternativeMarks,baseTargetsNumber,alternativeTargetsNumber,model));
                    weaponsDeck.get(weaponsDeck.size()-1).setWeaponTree(new WeaponTree("src/resources/Flamethrower.json"));
                }
                if(result2.get("name").equals("ZX-2")) {
                    weaponsDeck.add(new ZX2(name,pickUpAmmo,baseAmmo,alternativeAmmo,baseDamage,alternativeDamage,baseMarks,alternativeMarks,baseTargetsNumber,alternativeTargetsNumber,model));
                    weaponsDeck.get(weaponsDeck.size()-1).setWeaponTree(new WeaponTree("src/resources/Zx2.json"));
                }
                if(result2.get("name").equals("SHOTGUN")) {
                    //weaponsDeck.add(new Shotgun(name,pickUpAmmo,baseAmmo,alternativeAmmo,baseDamage,alternativeDamage,baseMarks,alternativeMarks,baseTargetsNumber,alternativeTargetsNumber,model));
                    weaponsDeck.get(weaponsDeck.size()-1).setWeaponTree(new WeaponTree("src/resources/Shotgun.json"));
                }
                if(result2.get("name").equals("POWERGLOVE")) {
                    //weaponsDeck.add(new Powerglove(name,pickUpAmmo,baseAmmo,alternativeAmmo,baseDamage,alternativeDamage,baseMarks,alternativeMarks,baseTargetsNumber,alternativeTargetsNumber,model));
                    weaponsDeck.get(weaponsDeck.size()-1).setWeaponTree(new WeaponTree("src/resources/Powerglove.json"));
                }
                if(result2.get("name").equals("RAILGUN")) {
                    weaponsDeck.add(new RailGun(name,pickUpAmmo,baseAmmo,alternativeAmmo,baseDamage,alternativeDamage,baseMarks,alternativeMarks,baseTargetsNumber,alternativeTargetsNumber,model));
                    weaponsDeck.get(weaponsDeck.size()-1).setWeaponTree(new WeaponTree("src/resources/Railgun.json"));
                }
                if(result2.get("name").equals("SHOCKWAVE")) {
                    //weaponsDeck.add(new Shockwave(name,pickUpAmmo,baseAmmo,alternativeAmmo,baseDamage,alternativeDamage,baseMarks,alternativeMarks,baseTargetsNumber,alternativeTargetsNumber,model));
                    weaponsDeck.get(weaponsDeck.size()-1).setWeaponTree(new WeaponTree("src/resources/Shockwave.json"));
                }
                if(result2.get("name").equals("SLEDGEHAMMER")) {
                    weaponsDeck.add(new Sledgehammer(name,pickUpAmmo,baseAmmo,alternativeAmmo,baseDamage,alternativeDamage,baseMarks,alternativeMarks,baseTargetsNumber,alternativeTargetsNumber,model));
                    weaponsDeck.get(weaponsDeck.size()-1).setWeaponTree(new WeaponTree("src/resources/Sledgehammer.json"));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void addOptionalWeapons1() {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("src/resources/weaponCards.json"));
            JSONObject myJo = (JSONObject) obj;
            JSONArray myArray = (JSONArray) myJo.get("weaponCards");
            JSONObject result1 = (JSONObject) myArray.get(0);
            JSONArray myArray2 = (JSONArray) result1.get("optional1");
            for (int i = 0; i < myArray2.size(); i++) {
                JSONObject result2 = (JSONObject) myArray2.get(i);
                String name = (String) result2.get("name");
                Ammo baseAmmo = new Ammo((int) result2.get("RED"), (int) result2.get("BLUE"), (int) result2.get("YELLOW"));
                Ammo optionalAmmo1 = new Ammo((int) result2.get("RED1"), (int) result2.get("BLUE1"), (int) result2.get("YELLOW1"));
                Ammo pickUpAmmo = new Ammo((int) result2.get("PRED"), (int) result2.get("PBLUE"), (int) result2.get("PYELLOW"));
                int baseDamage = (int) result2.get("DAMAGE");
                int optionalDamage1 = (int) result2.get("DAMAGE1");
                int baseMarks = (int) result2.get("MARKS");
                int optionalMarks1 = (int) result2.get("MARKS1");
                int baseTargetsNumber = (int) result2.get("TARGETSNUMBER");
                int optionalTargetsNumber1 = (int) result2.get("TARGETSNUMBER1");
                if(result2.get("name").equals("LOCKRIFLE")) {
                    weaponsDeck.add(new LockRifle(name,pickUpAmmo,baseAmmo,optionalAmmo1,baseDamage,optionalDamage1,baseMarks,optionalMarks1,baseTargetsNumber,optionalTargetsNumber1,model));
                    weaponsDeck.get(weaponsDeck.size()-1).setWeaponTree(new WeaponTree("src/resources/Lockrifle.json"));
                }
                if(result2.get("name").equals("GRENADELAUNCHER")) {
                    //weaponsDeck.add(new Grenadelauncher(name,pickUpAmmo,baseAmmo,optionalAmmo1,baseDamage,optionalDamage1,baseMarks,optionalMarks1,baseTargetsNumber,optionalTargetsNumber1,model));
                    weaponsDeck.get(weaponsDeck.size()-1).setWeaponTree(new WeaponTree("src/resources/Grenadelauncher.json"));
                }
                if(result2.get("name").equals("VORTEX")) {
                    //weaponsDeck.add(new Vortexcannon(name,pickUpAmmo,baseAmmo,optionalAmmo1,baseDamage,optionalDamage1,baseMarks,optionalMarks1,baseTargetsNumber,optionalTargetsNumber1,model));
                    weaponsDeck.get(weaponsDeck.size()-1).setWeaponTree(new WeaponTree("src/resources/Vortexcannon.json"));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void addOptionalWeapons2() {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("src/resources/weaponCards.json"));
            JSONObject myJo = (JSONObject) obj;
            JSONArray myArray = (JSONArray) myJo.get("weaponCards");
            JSONObject result1 = (JSONObject) myArray.get(0);
            JSONArray myArray2 = (JSONArray) result1.get("optional1");
            for (int i = 0; i < myArray2.size(); i++) {
                JSONObject result2 = (JSONObject) myArray2.get(i);
                String name = (String) result2.get("name");
                Ammo baseAmmo = new Ammo((int) result2.get("RED"), (int) result2.get("BLUE"), (int) result2.get("YELLOW"));
                Ammo optionalAmmo1 = new Ammo((int) result2.get("RED1"), (int) result2.get("BLUE1"), (int) result2.get("YELLOW1"));
                Ammo optionalAmmo2 = new Ammo((int) result2.get("RED1"), (int) result2.get("BLUE1"), (int) result2.get("YELLOW1"));
                Ammo pickUpAmmo = new Ammo((int) result2.get("PRED"), (int) result2.get("PBLUE"), (int) result2.get("PYELLOW"));
                int baseDamage = (int) result2.get("DAMAGE");
                int optionalDamage1 = (int) result2.get("DAMAGE1");
                int optionalDamage2 = (int) result2.get("DAMAGE2");
                int baseMarks = (int) result2.get("MARKS");
                int optionalMarks1 = (int) result2.get("MARKS1");
                int optionalMarks2 = (int) result2.get("MARKS2");
                int baseTargetsNumber = (int) result2.get("TARGETSNUMBER");
                int optionalTargetsNumber1 = (int) result2.get("TARGETSNUMBER1");
                int optionalTargetsNumber2 = (int) result2.get("TARGETSNUMBER2");

                if(result2.get("name").equals("MACHINEGUN")) {
                    //weaponsDeck.add(new Machinegun(name,pickUpAmmo,baseAmmo,optionalAmmo1,baseDamage,optionalDamage1,baseMarks,optionalMarks1,baseTargetsNumber,optionalTargetsNumber1,model));
                    weaponsDeck.get(weaponsDeck.size()-1).setWeaponTree(new WeaponTree("src/resources/Machinegun.json"));
                }
                if(result2.get("name").equals("THOR")) {
                    weaponsDeck.add(new Thor(name,pickUpAmmo,baseAmmo,optionalAmmo1,optionalAmmo2,baseDamage,optionalDamage1,optionalDamage2,baseMarks,optionalMarks1,optionalMarks2,baseTargetsNumber,optionalTargetsNumber1,optionalTargetsNumber2,model));
                    weaponsDeck.get(weaponsDeck.size()-1).setWeaponTree(new WeaponTree("src/resources/Thor.json"));
                }
                if(result2.get("name").equals("PLASMAGUN")) {
                    //weaponsDeck.add(new Plasmagun(name,pickUpAmmo,baseAmmo,optionalAmmo1,optionalAmmo2,baseDamage,optionalDamage1,optionalDamage2,baseMarks,optionalMarks1,optionalMarks2,baseTargetsNumber,optionalTargetsNumber1,optionalTargetsNumber2,model));
                    weaponsDeck.get(weaponsDeck.size()-1).setWeaponTree(new WeaponTree("src/resources/Plasmagun.json"));
                }
                if(result2.get("name").equals("ROCKETLAUNCHER")) {
                    //weaponsDeck.add(new Rocketlauncher(name,pickUpAmmo,baseAmmo,optionalAmmo1,optionalAmmo2,baseDamage,optionalDamage1,optionalDamage2,baseMarks,optionalMarks1,optionalMarks2,baseTargetsNumber,optionalTargetsNumber1,optionalTargetsNumber2,model));
                    weaponsDeck.get(weaponsDeck.size()-1).setWeaponTree(new WeaponTree("src/resources/Rocketlauncher.json"));
                }
                if(result2.get("name").equals("CYBERBLADE")) {
                    weaponsDeck.add(new Cyberblade(name,pickUpAmmo,baseAmmo,optionalAmmo1,optionalAmmo2,baseDamage,optionalDamage1,optionalDamage2,baseMarks,optionalMarks1,optionalMarks2,baseTargetsNumber,optionalTargetsNumber1,optionalTargetsNumber2,model));
                    weaponsDeck.get(weaponsDeck.size()-1).setWeaponTree(new WeaponTree("src/resources/Cyberblade.json"));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}