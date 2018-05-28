package com.golf2k18.AI;

import com.badlogic.gdx.math.Vector3;
import com.golf2k18.objects.Ball;
import com.golf2k18.objects.Terrain;

public class AI {
    private Ball ball;
    private Terrain terrain;
    public void AI(Ball ball, Terrain terrain){
        this.ball = ball;
        this.terrain = terrain;
    }
    public Vector3 holeInOne(){
        Vector3 addVectors = new Vector3();
        while(!terrain.isGoalHit())
        {
            Vector3 tryBall = fitness();
            addVectors = addVectors.add(tryBall);
            ball.hit(addVectors);
        }
        return addVectors;
    }
    private Vector3 fitness(){
        Vector3 coordinates = new Vector3();
        Vector3 goal = terrain.getGoal();
        coordinates.x = goal.x - ball.getX();
        coordinates.y = goal.y - ball.getY();
        return coordinates;
    }

}
