package model.player_package.action;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.List;

public class ActionTree {
    //Identificatore dell'albero
    //1: albero normale
    //2: albero con una azione potenziata
    //3: albero con due azioni potenziate
    //4: albero frenesia per giocatori dopo il 1°
    //5: albero frenesia per giocatori prima del 1°
    private final int ID;

    private String path = null;
    private Node<String> root = null; //Radice dell'albero
    private Node<String> lastActionPerformed = null; //Ultima azione inserita
    private Node<String> lastAction = null; //Ultima azione inserita, ma non ancora performata

    private int actionCounter;
    private int performedAction;

    public ActionTree(int ID){
        this.ID = ID;
        performedAction = 0;
        init();
        parseActionTree();
    }

    //Imposta la destinazione del file JSON
    private void init(){
        switch (ID)
        {
            case 1:
                path = "src/resources/actionTree1.json";
                actionCounter = 2;
                break;

            case 2:
                path = "src/resources/actionTree2.json";
                actionCounter = 2;
                break;

            case 3:
                path = "src/resources/actionTree3.json";
                actionCounter = 2;
                break;

            case 4:
                path = "src/resources/actionTree4.json";
                actionCounter = 2;
                break;

            case 5:
                path = "src/resources/actionTree5.json";
                actionCounter = 1;
                break;
        }
    }

    private void buildTree(Node<String> parent , JSONObject obj){
        JSONArray array = (JSONArray) obj.get("child");

        if(!array.isEmpty())
        {
            for (int i = 0; i < array.size(); i++){
                JSONObject obj1 = (JSONObject) array.get(i);

                String type = obj1.get("type").toString();
                Node<String> newNode = new Node<>(type);
                parent.addChild(newNode);
                buildTree(newNode , obj1);
            }
        }
    }

    private void parseActionTree(){
        JSONParser parser = new JSONParser();

        try{
            Object obj = parser.parse(new FileReader(path));
            JSONObject myJo = (JSONObject) obj;

            root = new Node<String>("root");
            root.setParent(null);
            lastAction = lastActionPerformed = root;

            buildTree(root , myJo);

        }

        catch(FileNotFoundException e ){
            System.out.println(e);
        }

        catch(IOException e ){
            System.out.println(e);
        }

        catch(ParseException e){
            System.out.println(e);
        }
    }

    public Node<String> getRoot(){
        return root;
    }
    //Verifica che la mossa sia possibile
    public boolean checkAction(char move){

        List<Node<String>> children = lastAction.getChildren(); //Andrà messo lastActionPerformed al posto di LastAction

        for (int i = 0; i < children.size(); i++){
            String type = children.get(i).getData();
            if (KeyMap.isValid(type , move)){
                lastAction = children.get(i);
                return true;
            }
        }

        return false;
    }
    //Se l'azione è stata eseguita, l'albero si aggiorna
    public void updateAction(){
        lastActionPerformed = lastAction;
    }

    public String availableAction(){ //Modificare per mostrare tutte le combinazioni
        String result = "Your available action: \n";
 ////////////
 ////////////       //showChildren() method not available
 ///////////       //result = result +lastAction.showChildren();
 ///////////
        result = result +"Action left: " +(actionCounter-performedAction) +"\n";
        return result;
    }

    public boolean isFinished(){
        return lastAction.getChildren().isEmpty(); //Andrà cambiato con lastactionperformed
    }

    public void resetPerformedAction(){
        performedAction = 0;
    }

    public boolean isEnd(){
        return (performedAction == actionCounter);
    }

    public void endAction(){
        performedAction++;
        lastAction = lastActionPerformed = root;
    }

}



