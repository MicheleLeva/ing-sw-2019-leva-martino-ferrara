package view;

import model.events.message.*;
import model.events.playermove.*;
import model.player_package.PlayerColor;
import utils.Observable;
import utils.Observer;

import java.util.Scanner;

public class View extends Observable<PlayerMove> implements Observer<Message>{

    private final PlayerColor playerColor;

    public View(PlayerColor playerColor){
        this.playerColor = playerColor;
    }

    @Override
    public void update(Message message){
        if (playerColor == message.getPlayerColor()){
            System.out.println(message.toPlayer());
        }

        else{
           System.out.println(message.toOthers());
        }
    }

    public void reportError(String error){
        System.out.println(error);
    }

    public void update(PowerUpDiscardMessage message){
        if (playerColor == message.getPlayerColor()){
            System.out.println(message.toPlayer());
            discardPowerUp();
        }
        else
            System.out.println(message.toOthers());
    }

    public void discardPowerUp(){
        Scanner input = new Scanner(System.in);
        notify (new DiscardPowerUpMove(this , input.nextInt()));
    }

    public void update(StartMessage message){
        printMessage(message);
        System.out.println("Insert the number of the chosen move:");
        Scanner reader = new Scanner(System.in);
        int index = reader.nextInt();
        if (index == 1){ //chiedi di vedere tutte le carte, forse non necessario?
            notify(new ShowCardsMove(playerColor, this, 1));
        }
        if (index == 2){ //chiedi le azioni
            notify(new ShowActionsMove(this));
        }
        if (index == 3){ //chiedi i powerup
            notify((new ShowCardsMove(playerColor, this, 2)));
        }
    }

    public void update (ShowActionsMessage message){
        printMessage(message);
        System.out.println("Insert the number of the chosen move:");
        Scanner reader = new Scanner(System.in);
        int index = 0;
        while (index<1 || index>4){
            index = reader.nextInt();
        }
        if (index == 1){ //RunMove
            //notify(new RequestRunMove...)
        }
        if (index == 2) { //GrabMove
            chooseMove(2);
        }
        if (index == 3){ //ShootMove
            //notify(new RequestShootMove...)
        }
        if (index == 4){
            //notify(new RequestReloadMove
        }
    }

    public void update(DrawPowerUpMessage message){
        if (this.playerColor == message.getPlayerColor()){
            System.out.println(message.toPlayer());
        }

        else
            System.out.println(message.toOthers());
    }

    public void update(GrabMessage message){
        printMessage(message);
        notify(new ShowActionsMove(this));
    }

    public void update(AskTurnInputMessage message){
        if (playerColor == message.getPlayerColor()){
            System.out.println(message.toPlayer());
            input();
        }
    }

    public void input(){
        Scanner input = new Scanner(System.in);
        notify(new InputMove(this , input.next().charAt(0)));
    }

    public void update(PowerUpMessage message){
        printMessage(message);
        //InitMove per tornare a StartMessage?
    }

    public void update (ReloadMessage message){
        printMessage(message);
        notify(new ShowActionsMove(this));
    }

    public void update (RunMessage message){
        printMessage(message);
        notify(new ShowActionsMove(this));
    }

    public void update (ShootMessage message){
        printMessage(message);
        notify(new ShowActionsMove(this));
    }

    public void update (ShowCardsMessage message){
        printMessage(message);
        if (message.getType() == 1){ //vede tutte le carte
            //ritorna a StartMove, InitMove?
        }
        if (message.getType() == 2){ //vede le carte powerup
            chooseMove(5);
        }

    }

    public void printMessage(Message message){
        if (this.playerColor == message.getPlayerColor()){
            System.out.println(message.toPlayer());
        }

        else{
            System.out.println(message.toOthers());
        }
    }

    public PlayerColor getPlayerColor(){
        return playerColor;
    }

    public void usePowerUp(int index){
        Scanner reader = new Scanner(System.in);

        switch (index){
            case 1: //Targeting Scope
                //prima di chiamare questo powerup ha ricevuto la lista dei possibili giocatori a cui dare il marchio
                System.out.println("Insert the number of the player to give a mark to:");
                int player = reader.nextInt();
                //notify(new UsePowerUpMove(playerColor, this, type:1, player);
                //come fa il controller/model ad associare player al giocatore corretto?
                //deve ricordarsi i giocatori che hanno ricevuto danno questo turno?
                reader.close();
                break;

            case 2: //Newton pt. 1
                //prima di chiamare questo powerup ha ricevuto la lista dei giocatori
                System.out.println("Insert the number of the player you wish to move:");
                int target = reader.nextInt();
                //notify(new UsePowerUpMove(playerColor, this, type:2, player);
                reader.close();
                break;

            case 3: //Newton pt. 2
                //ora riceve i possibili square dove può muovere il bersaglio scelto
                System.out.println("Insert the number of the square you wish to move the player to:");
                int square = reader.nextInt();
                //notify(new UsePowerUpMove(playeColor, this, type:3, square);
                reader.close();
                break;

            case 4: //TagBack granade
                //al giocatore arriva la notifica che gli è stato inflitto danno
                System.out.println("Do you wish to use Tagback Granade on the player who shot you?\nInsert 1 for yes, 0 for no");
                int answer = reader.nextInt();
                //notify(new UsePowerUpMove(playeColor, this, type:4, answer);
                reader.close();
                break;

            case 5: //Teleporter
                //al giocatore arriva la lista di tutti gli square della mappa
                System.out.println("Choose the number of the square to teleport to:");
                int newSquare = reader.nextInt();
                //notify(new UsePowerUpMove(playeColor, this, type:5, newSquare);
                reader.close();
                break;

        }
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
            case 5: //PowerUpMove
                System.out.println("Insert the number of the Power Up to use:");
                int powerUp = reader.nextInt();
                notify(new ReloadMove(this, powerUp));
                reader.close();
                break;
            case 6: //DrawMove (Il giocatore può decidere di pescare carte? Caso >3 carte in mano?)
                notify((new DrawMove(playerColor, this)));
                break;
        }




    }
}
