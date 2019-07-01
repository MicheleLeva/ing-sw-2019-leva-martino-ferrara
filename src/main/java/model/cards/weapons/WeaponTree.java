package model.cards.weapons;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Contains all the possible combinations of fire modes a weapon can have
 */
public class WeaponTree {

        private String path;
        private WeaponTreeNode<FireMode> root = null;
        private WeaponTreeNode<FireMode> lastActionPerformed = null;
        private WeaponTreeNode<FireMode> lastAction = null;

    /**
     * Weapons Tree's constructor
     * @param path file path to the weapon's json
     */
        public WeaponTree(String path){
            this.path = path;
            parseActionTree();
        }

    /**
     * helper method used to build the tree
     * @param parent the father node
     * @param obj the current JSon object
     */
        private void buildTree(WeaponTreeNode<FireMode> parent , JSONObject obj){
            JSONArray array = (JSONArray) obj.get("child");

            if(!array.isEmpty())
            {
                for (int i = 0; i < array.size(); i++){
                    JSONObject obj1 = (JSONObject) array.get(i);

                    String type = obj1.get("type").toString();
                    String name = obj1.get("name").toString();
                    WeaponTreeNode<FireMode> newNode = new WeaponTreeNode<>(new FireMode(type, name));
                    parent.addChild(newNode);
                    buildTree(newNode, obj1);
                }
            }
        }

    /**
     * builds the action tree
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


                root = new WeaponTreeNode<>(new FireMode("root","root"));
                root.setParent(null);
                lastAction = lastActionPerformed = root;

                buildTree(root , myJo);

            } catch(IOException | ParseException e ){
                System.out.println(e);
            }
        }

    /**
     * @return the tree's root
     */
        public WeaponTreeNode<FireMode> getRoot(){
            return root;
        }

    /**
     * updates the tree by changing the last action chosen
     */
        public void updateLastAction(int index){

            List<WeaponTreeNode<FireMode>> children = lastActionPerformed.getChildren();
            lastAction = children.get(index);
        }

    /**
     * updates the tree, after having performed the action
     */
        public void updateLastActionPerformed(){
            lastActionPerformed = lastAction;
        }


    /**
     * Checks if the the end of the tree has been reached
     */
        public boolean isActionEnded(){
            if(lastActionPerformed.getChildren().isEmpty())
                return true;
            else return lastActionPerformed.getChildren().size() == 1 &&
                    lastActionPerformed.getChildren().get(0).getData().getType().equals("end");
        }

    /**
     * Resets the tree's by setting root as the last action performed
     */
        public void resetAction(){
            resetLastAction();
        }


        private void resetLastAction(){
            lastAction = lastActionPerformed = root;
        }

        public void endAction(){
            resetLastAction();
        }




        public WeaponTreeNode<FireMode> getLastActionPerformed(){
            return this.lastActionPerformed;
    }

        public WeaponTreeNode<FireMode> getLastAction(){
        return this.lastAction;
    }

        public void setLastAction(WeaponTreeNode<FireMode> fireMode){
            this.lastAction = fireMode;
        }




    }
