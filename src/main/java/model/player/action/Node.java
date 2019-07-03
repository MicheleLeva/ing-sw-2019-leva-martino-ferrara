package model.player.action;

import java.util.ArrayList;
import java.util.List;

/**
 * Node Class. It represents a node of the actionTree
 * @author Stefano Martino
 */
public class Node<T> {

    private T data;

    private List<Node<T>> children = new ArrayList<>();

    private Node<T> parent = null;

    /**
     * Constructor of the Node class
     * @param data the data contained by the node
     */
    public Node(T data) {
        this.data = data;
    }

    /**
     * Adds a child to the current node
     * @param child the child to add
     */
    public void addChild(Node<T> child) {
        child.setParent(this);
        this.children.add(child);
    }

    /**
     * Returns all the node's children
     * @return all the node's children
     */
    public List<Node<T>> getChildren() {
        return children;
    }

    /**
     * Returns the node's data
     * @return the node's data
     */
    public T getData() {
        return data;
    }

    /**
     * Stes the parent of the current node
     * @param parent the parent of the current node
     */
    public void setParent(Node<T> parent) {
        this.parent = parent;
    }

    /**
     * Returns the parent of the current node
     * @return the parent of the current node
     */
    public Node<T> getParent() {
        return parent;
    }


}



