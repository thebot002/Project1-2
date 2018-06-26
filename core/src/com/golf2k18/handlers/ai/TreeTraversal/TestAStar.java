package com.golf2k18.handlers.ai.TreeTraversal;

import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

public class TestAStar {
    private static ArrayList<Node> nodes = new ArrayList<>();
    public static void main(String[] args){
        initNetwork();
        AStar pathfinder = new AStar(nodes.get(0),nodes.get(10));
        String path = pathfinder.getShortestPath();
        System.out.println(path);

    }
    public static void initNetwork(){
        Node n00 = new Node(new Vector3(0,0,0),null);
        Node n02 = new Node(new Vector3(0,2f,0),null);
        Node n03 = new Node(new Vector3(0,3f,0),null);
        Node n04 = new Node(new Vector3(0,4f,0),null);
        Node n05 = new Node(new Vector3(0,5f,0),null);


        Node n10 = new Node(new Vector3(1f,0,0),null);
        Node n11 = new Node(new Vector3(1f,1f,0),null);
        Node n12 = new Node(new Vector3(1f,2f,0),null);
        Node n13 = new Node(new Vector3(1f,3f,0),null);
        Node n14 = new Node(new Vector3(1f,4f,0),null);
        Node n15 = new Node(new Vector3(1f,5f,0),null);

        Node n22 = new Node(new Vector3(2f,2f,0),null);
        Node n23 = new Node(new Vector3(2f,3f,0),null);
        Node n24 = new Node(new Vector3(2f,4f,0),null);
        Node n25 = new Node(new Vector3(2f,5f,0),null);

        nodes.add(n00);
        nodes.add(n02);
        nodes.add(n03);
        nodes.add(n04);
        nodes.add(n05);
        nodes.add(n10);
        nodes.add(n11);
        nodes.add(n12);
        nodes.add(n13);
        nodes.add(n14);
        nodes.add(n15);
        nodes.add(n22);
        nodes.add(n23);
        nodes.add(n24);
        nodes.add(n25);

        n00.connectTo(n10);
        n10.connectTo(n11);
        n11.connectTo(n12);

        n12.connectTo(n02);
        n02.connectTo(n03);
        n03.connectTo(n04);
        n04.connectTo(n05);
        n05.connectTo(n15);

        n12.connectTo(n22);
        n22.connectTo(n23);
        n23.connectTo(n13);
        n13.connectTo(n14);
        n14.connectTo(n24);
        n24.connectTo(n25);
        n25.connectTo(n15);
    }
}
