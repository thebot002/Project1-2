package com.golf2k18.handlers.ai.TreeTraversal;

import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

public class Node<E> {

    private Vector3 location;
    private ArrayList<Node> cameFrom;
    private ArrayList<Node> neighbours;

    public Node(Vector3 location, ArrayList<Node> cameFrom){
        this.location = location;
        this.cameFrom = cameFrom;

    }

    public float distanceTo(Node to){
        return this.location.dst(to.location);
    }

    public void connectTo(Node neighbour){
        this.neighbours.add(neighbour);
    }

    public ArrayList getNeighbours(){
        return this.neighbours;
    }

    public Vector3 getLocation() {
        return location;
    }

    public void addPrevious(Node n){
        cameFrom.add(n);
    }

    public ArrayList<Node> getCameFrom() {
        return cameFrom;
    }
}
