package model.cards.weapons;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Contains all the possible combinations of fire modes a weapon can have
 */
public class WeaponTree {

        private String path = null;
        private WeaponTreeNode<FireMode> root = null; //Radice dell'albero
        private WeaponTreeNode<FireMode> lastActionPerformed = null; //Ultima azione inserita
        private WeaponTreeNode<FireMode> lastAction = null; //Ultima azione inserita, ma non ancora performata

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
                    WeaponTreeNode<FireMode> newNode = new WeaponTreeNode<FireMode>(new FireMode(type,name));
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
                Object obj = parser.parse(new FileReader(path));
                JSONObject myJo = (JSONObject) obj;

                root = new WeaponTreeNode<FireMode>(new FireMode("root","root"));
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

    /**
     * @return the tree's root
     */
        public WeaponTreeNode<FireMode> getRoot(){
            return root;
        }
        //Verifica che la mossa sia possibile
    /**
     * updates the tree by changing the last action chosen
     */
        public void updateLastAction(int index){

            List<WeaponTreeNode<FireMode>> children = lastActionPerformed.getChildren();
            lastAction = children.get(index);
        }
        //Se l'azione è stata eseguita, l'albero si aggiorna
    /**
     * updates the tree, after having performed the action
     */
        public void updateLastActionPerformed(){
            /*if (isActionEnded()){
                endAction();
            }
            else{*/
                lastActionPerformed = lastAction;
           // }
        }
/*
        public String availableAction(){ //Modificare per mostrare tutte le combinazioni
            String result = "Your available actions: \n";
            List<WeaponTreeNode<FireMode>> children = lastActionPerformed.getChildren();
            for(WeaponTreeNode<FireMode> child : children){
                result = result + child.getData().getEffectName();
            }
            return result;
        }*/

    /**
     * Checks if the the end of the tree has been reached
     */
        public boolean isActionEnded(){
            if(lastActionPerformed.getChildren().isEmpty())
                return true;
            else if(lastActionPerformed.getChildren().size()==1 &&
                    lastActionPerformed.getChildren().get(0).getData().getType().equals("end"))
                return true;
            else
                return false;
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

        public String getType(){
            return lastAction.getData().getType();
        }

        public String getEffectName(){
            return lastAction.getData().getType();
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
