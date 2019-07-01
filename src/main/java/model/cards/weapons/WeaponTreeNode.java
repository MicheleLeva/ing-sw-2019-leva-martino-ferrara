package model.cards.weapons;

import java.util.ArrayList;
import java.util.List;

/**
 * Node of the weapon tree that represents a single fire mode with its name and type(base, alternative, first optional
 * and second optional
 * @param <T> Will always be the FireMode class(representation of a particular fire mode)
 */
public class WeaponTreeNode<T> {
    private T data;
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

}
