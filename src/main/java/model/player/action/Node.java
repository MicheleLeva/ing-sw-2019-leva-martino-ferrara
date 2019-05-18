package model.player.action;

import java.util.ArrayList;
import java.util.List;
//Classe nodo, usata per la costruzione dell'albero delle azioni
public class Node<T> {

    private T data = null;

    private List<Node<T>> children = new ArrayList();

    private Node<T> parent = null;

    public Node(T data) {
        this.data = data;
    }

    public void addChild(Node<T> child) {
        child.setParent(this);
        this.children.add(child);
    }

    public List<Node<T>> getChildren() {
        return children;
    }

    public T getData() {
        return data;
    }

    public void setParent(Node<T> parent) {
        this.parent = parent;
    }

    public Node<T> getParent() {
        return parent;
    }

    /*public void printChildren() {
        if (!children.isEmpty()) {
            for (int i = 0; i < children.size(); i++)
                children.get(i).printChildren();
        }

        System.out.println(data);
    }*/

    public String showChildren(){
        String result = "";
        for (int i = 0; i< children.size(); i++){
            result = result +children.get(i).data +" ";
        }
        return result;
    }
}



