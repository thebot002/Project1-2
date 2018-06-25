package com.golf2k18.handlers.ai.TreeTraversal;

import java.util.HashMap;

public class aStar {
    HashMap<Node,Float> score = new HashMap<>();




   //TODO Implementation
    private float heuristicEstimate(Node from, Node to) {
        return from.distanceTo(to);
    }

}
