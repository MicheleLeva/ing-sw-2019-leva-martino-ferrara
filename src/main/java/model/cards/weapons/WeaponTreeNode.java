package model.cards.weapons;

import java.util.ArrayList;
import java.util.List;

public class WeaponTreeNode<T> {
    private T data = null;
    private List<WeaponTreeNode<T>> children = new ArrayList();
    private WeaponTreeNode<T> parent = null;

    public WeaponTreeNode(T data) {
        this.data = data;
    }

    public void addChild(WeaponTreeNode<T> child) {
        child.setParent(this);
        this.children.add(child);
    }

    public List<WeaponTreeNode<T>> getChildren() {
        return children;
    }

    public T getData() {
        return data;
    }

    public void setParent(WeaponTreeNode<T> parent) {
        this.parent = parent;
    }

    public WeaponTreeNode<T> getParent() {
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
