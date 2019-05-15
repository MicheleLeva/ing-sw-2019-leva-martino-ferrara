package model.cards;

import model.player_package.action.KeyMap;
import model.player_package.action.Node;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WeaponTree {


        //Identificatore dell'albero
        //1: albero normale
        //2: albero con una azione potenziata
        //3: albero con due azioni potenziate
        //4: albero frenesia per giocatori dopo il 1°
        //5: albero frenesia per giocatori prima del 1°
        private final int ID;

        private String path = null;
        private WeaponTreeNode<FireMode> root = null; //Radice dell'albero
        private WeaponTreeNode<FireMode> lastActionPerformed = null; //Ultima azione inserita
        private WeaponTreeNode<FireMode> lastAction = null; //Ultima azione inserita, ma non ancora performata

        private int actionCounter;
        private int performedAction;

        public WeaponTree(int ID){
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

        public WeaponTreeNode<FireMode> getRoot(){
            return root;
        }
        //Verifica che la mossa sia possibile
        public void updateLastAction(int index){

            List<WeaponTreeNode<FireMode>> children = lastActionPerformed.getChildren(); //Andrà messo lastActionPerformed al posto di LastAction
            lastAction = children.get(index);
        }
        //Se l'azione è stata eseguita, l'albero si aggiorna
        public void updateLastActionPerformed(){
            if (isActionEnded()){
                endAction();
            }
            else{
                lastActionPerformed = lastAction;
            }
        }

        public String availableAction(){ //Modificare per mostrare tutte le combinazioni
            String result = "Your available actions: \n";
            List<WeaponTreeNode<FireMode>> children = lastActionPerformed.getChildren();
            for(WeaponTreeNode<FireMode> child : children){
                result = result + child.getData().getEffectName();
            }
            return result;
        }

        public boolean isActionEnded(){
            return lastActionPerformed.getChildren().isEmpty();
        }

        public void resetAction(){
            performedAction = 0;
            resetLastAction();
        }

        public boolean isTurnEnded(){
            return (performedAction == actionCounter);
        }

        private void resetLastAction(){
            lastAction = lastActionPerformed = root;
        }

        public void endAction(){
            performedAction++;
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


    }
