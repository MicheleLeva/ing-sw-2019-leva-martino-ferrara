package model.player.action;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.List;

public class ActionTree {
    //Tree ID:
    //1: standard tree
    //2: tree with one powered action
    //3: tree with two powered actions
    //4: frenzy tree for players whose turn comes after the first player's one
    //5: frenzy tree for players whose turn comes before the first player's one
    private final int ID;
    //JSON Path used to read the tree
    private String path = null;
    //Tree's root
    private Node<String> root = null;
    //Last action selected and performed
    private Node<String> lastActionPerformed = null;
    //Last action selected but not yet performed
    private Node<String> lastAction = null;
    //number of total action available for the player's turn
    private int actionCounter;
    //number of action already perfomed by the player in his turn
    private int performedAction;

    private boolean isMoveEnded = true;
    //the builder takes the ID as its input and builds the corresponding action tree
    public ActionTree(int ID){
        this.ID = ID;
        performedAction = 0;
        init();
        parseActionTree();
    }

    //set JSON path
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
    //helper method used to build the tree
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
    //builds the action tree
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
    //returns the tree's root
    public Node<String> getRoot(){
        return root;
    }
    //cheks whether or not the input move is valid
    public boolean checkAction(char move){

        List<Node<String>> children = lastActionPerformed.getChildren();

        for (int i = 0; i < children.size(); i++){
            String type = children.get(i).getData();
            if (KeyMap.isValid(type , move)){
                lastAction = children.get(i);
                return true;
            }
        }

        return false;
    }
    //after executing the action, the tree updates
    public void updateAction(){

        lastActionPerformed = lastAction;

        if (isActionEnded()){
            endAction();
        }
        /*else{
            lastActionPerformed = lastAction;
        }*/
        setMoveEnded(true);
    }

    public void setMoveEnded(boolean moveEnded) {
        isMoveEnded = moveEnded;
    }

    public boolean isMoveEnded() {
        return isMoveEnded;
    }
    //returns a string containing all the possible actions for the player
    public String availableAction(){
        StringBuilder result = new StringBuilder();
        result.append("Your available actions: \n");
        if(!isTurnEnded()) {
            result.append(availableAction(lastActionPerformed));
            result.append("Left actions: " + (actionCounter - performedAction) + "\n");
        }
        else {
            result.append("|Reload|\n");
        }

        return result.toString();
    }
    //helper recursive method called passing lastActionPerformed node
    private String availableAction(Node<String> node)
    {
        if(node.getChildren().isEmpty()){
            return node.getData();
        }
        String result = "";
        for (int i = 0; i < node.getChildren().size(); i++)
        {
            if(!node.getData().equals("root")) {
                result = result + node.getData();
                result = result + " " + availableAction(node.getChildren().get(i));

            }
            else result = result + availableAction(node.getChildren().get(i));

            result = result +"|";
        }



        return result;
    }
    //returns true if the player has walked the tree to a leaf
    public boolean isActionEnded(){
        return lastActionPerformed.getChildren().isEmpty();
    }
    //resets action
    public void resetAction(){
        performedAction = 0;
        resetLastAction();
    }
    //returns true if the player has done all the actions
    public boolean isTurnEnded(){
        return (performedAction == actionCounter);
    }

    private void resetLastAction(){
        lastAction = lastActionPerformed = root;
    }
    //called when the player has finished the whole action
    public void endAction(){
        performedAction++;
        resetLastAction();
    }
    //returns the tree's ID
    public int getID(){
        return  ID;
    }
    //returns the last action
    public Node<String> getLastAction() {
        return lastAction;
    }
}



