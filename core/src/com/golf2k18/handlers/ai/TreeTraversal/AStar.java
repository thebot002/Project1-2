package com.golf2k18.handlers.ai.TreeTraversal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public class AStar {
    //Instantiate the needed variables
    private ArrayList<Node> closedSet = new ArrayList();
    private HashMap<Node, Float> gScore = new HashMap<>();
    private HashMap<Node, Float> fScore = new HashMap<>();
    private final int initAmount = 200;
    private Node goal;
    private Node start;
    private PriorityQueue<Node> openSet;

    public AStar(Node start, Node goal) {
        this.start = start;
        this.goal = goal;
        Comparator<Node> comparator = new Comparator<Node>() {
            @Override
            public int compare(Node node1, Node node2) {
                if (node1.distanceTo(goal) > node2.distanceTo(node2)) {
                    return 1;
                } else if (node1 == node2) {
                    return 0;
                } else {
                    return -1;
                }
            }
        };
        openSet = new PriorityQueue<>(initAmount,comparator);
        openSet.add(start);
        fScore.put(start, heuristicEstimate(start, goal));
        gScore.put(start, start.distanceTo(start));
    }

    public ArrayList getShortestPath() {
        while (openSet.isEmpty() != true) {
            Node pointer = openSet.poll();

            if (pointer == goal) {
                return reconstruct_path(pointer.getCameFrom(),pointer);
            }

            closedSet.add(pointer);
            ArrayList<Node> neighbours = pointer.getNeighbours();
            for (Node neighbour : neighbours) {
                if (closedSet.contains(neighbour)) {
                    continue;
                } else {
                    openSet.offer(neighbour);
                }
                gScore.put(neighbour, start.distanceTo(neighbour));
                float optionalDST = gScore.get(pointer) + pointer.getLocation().dst(neighbour.getLocation());
                if (optionalDST >= gScore.get(neighbour)) {
                    continue;
                }
                neighbour.addPrevious(pointer);
                gScore.put(neighbour, optionalDST);
                fScore.put(neighbour, gScore.get(neighbour) + neighbour.distanceTo(goal));

            }
        }
        return null;
    }

    public ArrayList reconstruct_path(ArrayList<Node> cameFrom, Node pointer) {
        ArrayList<Node> path = new ArrayList<>();
        path.add(pointer);
        for (int i = 0; i < cameFrom.size(); i++) {
            path.add(cameFrom.get(i));
        }
        return path;
    }



   //Linear distance heuristic
    public float heuristicEstimate(Node from, Node to) {
        return from.distanceTo(to);
    }

}
