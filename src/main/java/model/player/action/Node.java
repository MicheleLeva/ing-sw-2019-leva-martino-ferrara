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


}



