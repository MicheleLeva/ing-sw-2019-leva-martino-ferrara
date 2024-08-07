package model.player.action;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

import java.io.*;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Contains all the possible actions the player is allowed to perform
 * Each tree has a specific ID based upon the possible moves:
 *
 *  1: standard tree
 *  2: tree with one powered action
 *  3: tree with two powered actions
 *  4: frenzy tree for players whose turn comes after the first player's one
 *  5: frenzy tree for players whose turn comes before the first player's one
 *  @author Stefano Martino
 */
public class ActionTree {
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
    //number of action already performed by the player in his turn
    private int performedAction;
    //true if the current player has ended his turn
    private boolean doneTurn = false;
    private boolean isMoveEnded = false;

    /**
     * Action Tree's constructor
     * @param ID represents the specific set of actions
     */
    public ActionTree(int ID){
        this.ID = ID;
        performedAction = 0;
        init();
        parseActionTree();
        resetAction();
    }

    /**
     * Sets the JSon path based upon the ID
     */
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

            default:
                path = "src/resources/actionTree5.json";
                actionCounter = 1;
                break;

        }
    }

    /**
     * Helper method used to build the tree
     * @param parent the father node
     * @param obj the current JSon object
     */
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

    /**
     * Builds the action tree
     */
    private void parseActionTree(){
        JSONParser parser = new JSONParser();

        try{
            JSONObject myJo;
            try {
                Object obj = parser.parse(new FileReader(path));
                myJo = (JSONObject) obj;
            } catch (FileNotFoundException e) {
                String[] splitPath = path.split("/");
                String newPath = splitPath[splitPath.length - 1];
                newPath = "/" + newPath;
                InputStream configStream = this.getClass().getResourceAsStream(newPath);
                myJo = (JSONObject)parser.parse(
                        new InputStreamReader(configStream, StandardCharsets.UTF_8));
            }

            root = new Node<>("root");
            root.setParent(null);
            lastAction = lastActionPerformed = root;

            buildTree(root , myJo);

        } catch(ParseException | IOException e ){
            System.out.println(e);
        }
    }

    /**
     * Return the tree's root
     * @return the tree's root
     */
    public Node<String> getRoot(){
        return root;
    }

    /**
     * Checks whether or not the input move is valid
     * @param move player's input
     * @return a boolean that tells whether the input is or not valid
     */
    public boolean checkAction(char move){
        if(move == KeyMap.getEnd()){
            return true;
        }

        if(move == KeyMap.getUsePowerUp()) {
            return true;
        }

        if(isTurnEnded()) {
            return (move == KeyMap.getReload());
        }

        List<Node<String>> children = lastActionPerformed.getChildren();

        for (Node<String> child : children) {
            String type = child.getData();
            if (KeyMap.isValid(type, move)) {
                lastAction = child;
                return true;
            }
        }

        return false;
    }

    /**
     * Updates the tree, after having executed the action
     */
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

    /**
     * Sets move ended equal to the parameter
     * @param moveEnded the given parameter
     */
    public void setMoveEnded(boolean moveEnded) {
        isMoveEnded = moveEnded;
    }

    /**
     * Checks whether or not the move is ended
     * @return true if the move is ended, false otherwise
     */
    public boolean isMoveEnded() {
        return isMoveEnded;
    }

    /**
     * This method is used to print the current player's available actions
     * @return a String containing the available actions
     */
    public String availableAction(){
        StringBuilder result = new StringBuilder();
        result.append("Your available actions: \n");
        if(!isTurnEnded()) {
            result.append(availableAction(lastActionPerformed).replaceAll("root",""));
            result.append(" |Use PowerUp| |End action| ");
            result.append("Left actions: ");
            result.append((actionCounter - performedAction));
            result.append("\n");
        }
        else {
            result.append("|Reload|");
            result.append(" |Use PowerUp|");
            result.append(" |End action|\n");
        }


        return result.toString();
    }
    //helper recursive method called passing lastActionPerformed node
    private String availableAction(Node<String> node) {
    StringBuilder stringBuilder = new StringBuilder();
     for (int i = 0; i < node.getChildren().size(); i++){
         stringBuilder.append(node.getChildren().get(i).getData());
         stringBuilder.append(" | ");
     }
     return stringBuilder.toString();

    }

    /**
     * Returns true if the player has walked the tree to a leaf
     * @return true if the player has walked the tree to a leaf, false otherwise
     */
    public boolean isActionEnded(){
        return lastActionPerformed.getChildren().isEmpty();
    }

    /**
     * Resets the action Tree
     */
    public void resetAction(){
        performedAction = 0;
        resetLastAction();
    }

    /**
     * Returns true if the player has done all the actions
     * @return true if the player has done all the actions
     */
    public boolean isTurnEnded(){
        return (performedAction == actionCounter);
    }

    /**
     * Returns true if the player has completed his or her turn
     * @return true if the player has completed his or her turn
     */
    public boolean hasDoneTurn(){
        return doneTurn;
    }
    public void setDoneTurn(boolean doneTurn){
        this.doneTurn = doneTurn;
    }

    /**
     * This method is called whenever an action is completed
     */
    private void resetLastAction(){
        lastAction = lastActionPerformed = root;
    }

    /**
     * Called when the player has finished the whole action
     */
    public void endAction(){
        performedAction++;
        setMoveEnded(true);
        resetLastAction();
    }

    /**
     * Returns the tree's ID
     * @return the tree's ID
     */
    public int getID(){
        return  ID;
    }
    /**
     * Returns the last action
     * @return the last action
     */
    public Node<String> getLastAction() {
        return lastAction;
    }
    /**
     * Returns the last action performed
     * @return the last action performed
     */
    public Node<String> getLastActionPerformed(){return lastActionPerformed;}

    /**
     * Returns the number of performed actions
     * @return the number of performed actions
     */
    public int getPerformedAction(){return performedAction;}
}



