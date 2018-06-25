package com.golf2k18.handlers.ai.TreeTraversal;

import com.badlogic.gdx.math.Vector3;

public class Node<E> {
    private Vector3 location;
    public Node(Vector3 location){
        this.location = location;

    }
    public float distanceTo(Node to){
        return this.location.dst(to.location);
    }
}
