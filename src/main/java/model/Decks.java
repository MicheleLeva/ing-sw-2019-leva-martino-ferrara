package model;


import model.cards.*;
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

    private Random rand = new Random();

    private static Decks decksInstance = null;




    public static Decks getDecksInstance(){
        if(decksInstance == null)
            decksInstance = new Decks();
        return decksInstance;

    }



    public Decks(){
        weaponsDeck = new ArrayList<>();
        powerUpDeck = new ArrayList<>();
        ammoCardDeck = new ArrayList<>();
        discardedAmmoCardDeck = new ArrayList<>();
        discardedPowerUpDeck = new ArrayList<>();

    }



    public Weapon drawWeapon(){
        if(weaponsDeck.size()>0)
            return weaponsDeck.remove(rand.nextInt(weaponsDeck.size()));
        else
            return null;

    }

    public PowerUp drawPowerUp(){

        if(powerUpDeck.size() > 0){
            return powerUpDeck.remove(rand.nextInt(powerUpDeck.size()));
        }
        else
            powerUpDeck = new ArrayList(discardedPowerUpDeck);
            discardedPowerUpDeck.clear();
            return powerUpDeck.remove(rand.nextInt(powerUpDeck.size()));
    }

    public AmmoCard drawAmmoCard(){

        if(ammoCardDeck.size() > 0){
            return ammoCardDeck.remove(rand.nextInt(ammoCardDeck.size()));
        }
        else
            ammoCardDeck = new ArrayList(discardedAmmoCardDeck);
        discardedAmmoCardDeck.clear();
        return ammoCardDeck.remove(rand.nextInt(ammoCardDeck.size()));
    }

    public ArrayList<Weapon> getWeaponsDeck(){
        return weaponsDeck;

    }

    public ArrayList<PowerUp> getPowerUpDeck(){
        return powerUpDeck;

    }

    public ArrayList<AmmoCard> getAmmoCardDeck(){
        return ammoCardDeck;

    }

    public ArrayList<PowerUp> getDiscardedPowerUpDeck(){
        return discardedPowerUpDeck;

    }

    public ArrayList<AmmoCard> getDiscardedAmmoCardDeck(){
        return discardedAmmoCardDeck;

    }

    private void createPowerUpDeck(){
        JSONParser parser = new JSONParser();
        try{
            Object obj = parser.parse(new FileReader("src/resources/powerUps.json"));
            JSONObject myJo = (JSONObject) obj;
            JSONArray myArray = (JSONArray) myJo.get("powerUps");
            for(int i = 0; i < myArray.size();i++){
                JSONObject result1 = (JSONObject) myArray.get(i);
                if(result1.get("nome").equals("targetingScope"))
                    powerUpDeck.add(new TargetingScope(AmmoColor.valueOf((String)result1.get("color"))));
                if(result1.get("nome").equals("newton"))
                    powerUpDeck.add(new TargetingScope(AmmoColor.valueOf((String)result1.get("color"))));
                if(result1.get("nome").equals("tagbackGrenade"))
                    powerUpDeck.add(new TargetingScope(AmmoColor.valueOf((String)result1.get("color"))));
                if(result1.get("nome").equals("teleporter"))
                    powerUpDeck.add(new TargetingScope(AmmoColor.valueOf((String)result1.get("color"))));
            }
        }
        catch(FileNotFoundException e) {e.printStackTrace();}
        catch(IOException e) {e.printStackTrace();}
        catch(Exception e) {e.printStackTrace();}
    }

    public void createAmmoCardDeck(){
        JSONParser parser = new JSONParser();
        try{
            Object obj = parser.parse(new FileReader("src/resources/ammoCards.json"));
            JSONObject myJo = (JSONObject) obj;
            JSONArray myArray = (JSONArray) myJo.get("ammoCards");
            for(int i = 0; i < myArray.size();i++){
                JSONObject result1 = (JSONObject) myArray.get(i);
                ammoCardDeck.add(new AmmoCard((int)result1.get("RED"),(int)result1.get("BLUE"),(int)result1.get("YELLOW"),(boolean) result1.get("powerUps")));
            }
        }
        catch(FileNotFoundException e) {e.printStackTrace();}
        catch(IOException e) {e.printStackTrace();}
        catch(Exception e) {e.printStackTrace();}
    }

    public void addBasicWeapons(){
        JSONParser parser = new JSONParser();
        try{
            Object obj = parser.parse(new FileReader("src/resources/weaponCards.json"));
            JSONObject myJo = (JSONObject) obj;
            JSONArray myArray = (JSONArray) myJo.get("weaponCards");
            JSONObject result1 = (JSONObject) myArray.get(0);
            JSONArray myArray2 = (JSONArray) result1.get("basic");
            for(int i = 0; i < myArray2.size();i++){
                JSONObject result2 = (JSONObject) myArray2.get(i);
                // if(result2.get("name").equals("HEATSEEKER"))
                //{weaponsDeck.add(new LockRifle(result2.get("name"),result2.get("RED"),result2.get("BLUE"),result2.get("YELLOW")));}
                // if(result2.get("name").equals("WHISPER"))
                //{weaponsDeck.add(new Whisper(result2.get("name"),result2.get("RED"),result2.get("BLUE"),result2.get("YELLOW")));}
            }
        }
        catch(FileNotFoundException e) {e.printStackTrace();}
        catch(IOException e) {e.printStackTrace();}
        catch(Exception e) {e.printStackTrace();}



    }

    public void addOptionalWeapons1(){
        JSONParser parser = new JSONParser();
        try{
            Object obj = parser.parse(new FileReader("src/resources/weaponCards.json"));
            JSONObject myJo = (JSONObject) obj;
            JSONArray myArray = (JSONArray) myJo.get("weaponCards");
            JSONObject result1 = (JSONObject) myArray.get(0);
            JSONArray myArray2 = (JSONArray) result1.get("optional1");
            for(int i = 0; i < myArray2.size();i++){
                JSONObject result2 = (JSONObject) myArray2.get(i);

                String name = (String)result2.get("name");
                Ammo baseAmmo = new Ammo((int)result2.get("RED"),(int)result2.get("BLUE"),(int)result2.get("YELLOW"));
                Ammo optionalAmmo1 = new Ammo((int)result2.get("RED1"),(int)result2.get("BLUE1"),(int)result2.get("YELLOW1"));
                int baseDamage = (int)result2.get("baseDamage");
                int optionalDamage1 = (int)result2.get("optionalDamage1");
                int baseMarks=(int)result2.get("baseMarks");
                int optionalMarks1=(int)result2.get("optionalMarks1");
                ArrayList<Integer> visibility = new ArrayList<>();
                JSONArray myArray3 = (JSONArray) result2.get("visibility");
                if (myArray3 != null) {
                    for (int j = 0; i < myArray3.size(); i++) {
                        visibility.add((int)myArray3.get(j));
                    }
                }
                if(result2.get("name").equals("LOCK RIFLE")) {
                    weaponsDeck.add(new LockRifle(name,baseAmmo,optionalAmmo1,baseDamage,optionalDamage1,baseMarks,optionalMarks1,visibility));
                }

            }
        }
        catch(FileNotFoundException e) {e.printStackTrace();}
        catch(IOException e) {e.printStackTrace();}
        catch(Exception e) {e.printStackTrace();}



    }
