package view;

import model.events.message.TeleporterMessage;
import model.events.playermove.TeleporterMove;
import model.events.message.*;
import model.events.playermove.*;
import model.player_package.PlayerColor;
import utils.Observable;
import utils.Observer;

import java.util.ArrayList;
import java.util.Scanner;

public class View extends Observable<PlayerMove> implements Observer<Message>{

    private final PlayerColor playerColor;

    public View(PlayerColor playerColor){
        this.playerColor = playerColor;
    }

    @Override
    public void update(Message message){
        //this method will never be called because of overloading
    }

    public void update(StartMessage message){
        printMessage(message);
        System.out.println("Insert the number of the chosen move:");
        Scanner reader = new Scanner(System.in);
        int index = reader.nextInt();
        if (index == 1){

        }
        if (index == 2){

        }
        if (index == 3){

        }
    }


    public void update(DrawMessage message){
        printMessage(message);
    }

    public void update(GrabMessage message){
        printMessage(message);
    }

    public void update(PowerUpMessage message){
        printMessage(message);
    }

    public void update(TeleporterMessage message){
        message.toPlayer();
        Scanner scan = new Scanner(System.in);
        int row = scan.nextInt();
        int column = scan.nextInt();
        notify(new TeleporterMove(this,row, column));
    }

    public void update (ReloadMessage message){
        printMessage(message);
    }

    public void update (RunMessage message){
        printMessage(message);
    }

    public void update (ShootMessage message){
        printMessage(message);
    }

    public void update (ShowCardsMessage message){
        printMessage(message);
    }

    public void printMessage(Message message){
        if (this.playerColor == message.getPlayerColor()){
            System.out.println(message.toPlayer());
        }

        else{
            System.out.println(message.toOthers());
        }
    }

    public void update(ShowTargetsMessage message){
        message.toPlayer();
        message.toOthers();
        ArrayList<PlayerColor> chosenTargets = new ArrayList<>();
        int counter = message.getTargetsNumber();
        while (counter>0){
        Scanner scan = new Scanner(System.in);
        int selected = scan.nextInt();
        chosenTargets.add(message.getTargets().get(selected).getPlayerColor());
        counter--;
        }
        notify(new ShootMove(this,chosenTargets,message.getWeapon(), message.getFireMode()));
    }

    public PlayerColor getPlayerColor(){
        return playerColor;
    }


    public void requestRun(int steps){
        //notify(new RequestRunMove(this, steps);
        chooseMove(1);
    }

    public void requestShoot(){
        notify(new ShowCardsMove(playerColor,this,0));


        Scanner scan = new Scanner(System.in);
        int weaponIndex = scan.nextInt();
        int fireModeIndex = scan.nextInt();
        notify(new ShowTargetsMove(this, weaponIndex,fireModeIndex));
    }





    public void chooseMove(int index){
        Scanner reader = new Scanner(System.in);

        switch(index){

            case 1: //RunMove
                System.out.println("Insert the chosen x coordinate:");
                int x = reader.nextInt();
                System.out.println("Insert the chosen y coordinate:");
                int y = reader.nextInt();
                notify(new RunMove(this, x, y));
                reader.close();
                break;
            case 2: //GrabMove
                notify((new GrabMove(this)));
                break;
            case 3: //ShootMove
                //ShootMove da cambiare, non può avere oggetti del model!

            case 4: //ReloadMove
                System.out.println("Insert the number of the weapon to reload:");
                int weapon = reader.nextInt();
                notify(new ReloadMove(this, weapon));
                reader.close();
                break;
            case 5: //ShowCardsMove
                notify((new ShowCardsMove(playerColor, this, 1)));
                break;
            case 6: //PowerUpMove
                System.out.println("Insert the number of the Power Up to use:");
                int powerUp = reader.nextInt();
                notify(new ReloadMove(this, powerUp));
                reader.close();
                break;
            case 7: //DrawMove (Il giocatore può decidere di pescare carte? Caso >3 carte in mano?)
                notify((new DrawMove(playerColor, this)));
                break;
        }




    }

    public void reportError(String error){

    }


}
