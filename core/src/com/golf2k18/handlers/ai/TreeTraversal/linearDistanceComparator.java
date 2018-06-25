package com.golf2k18.handlers.ai.TreeTraversal;

import java.util.Comparator;

public class linearDistanceComparator implements Comparator {
    private Node goal;
    public linearDistanceComparator(Node goal){
        this.goal = goal;
    }
    @Override
    public int compare(Object o1, Object o2) {
        assert o1 instanceof Node;
        assert o2 instanceof Node;
        Node node1 = (Node)o1;
        Node node2 = (Node)o2;
        if(node1.distanceTo(goal)>node2.distanceTo(node2)){return 1;}
        else if(node1==node2){return 0;}
        else{return -1;}
    }
}
