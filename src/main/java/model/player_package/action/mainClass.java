package model.player_package.action;

import java.util.Scanner;

public class mainClass {
    public static void main (String[] args){
        ActionTree actionTree = new ActionTree(4);
        Scanner tastiera = new Scanner(System.in);
        KeyMap km = new KeyMap();
        char input;



        do {
            System.out.println("Mosse disponibili:");
            //actionTree.printAvailableAction();
            System.out.println("\nInserire mossa.");
            input = tastiera.nextLine().charAt(0);

            if (input != KeyMap.getEnd()) {

                if (actionTree.checkAction(input)) {
                    System.out.println("Mossa accettata.");
                } else
                    System.out.println("Mossa rifiutata.");
            }

        }while ((input != KeyMap.getEnd()) && (!actionTree.isFinished()));

        System.out.println("Azione completata\n");
    }
}
