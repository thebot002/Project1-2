package com.golf2k18.AI;

import com.badlogic.gdx.math.Vector3;
import com.golf2k18.objects.Ball;
import com.golf2k18.objects.Goal;
import com.golf2k18.objects.Terrain;
import com.golf2k18.states.game.Game;

public class AI2 {
    private Ball ball;
    private Terrain terrain;
    private Game game;
    private Vector3 goal;
    public void AI(Game game){
        this.game = game;
        this.ball = game.getBall();
        this.terrain = game.getTerrain();
        this.goal = terrain.getHole();

    }
    public Vector3 holeInOne(){
        Vector3 addVectors = new Vector3();
        Vector3 ballOrigin = new Vector3();
        ballOrigin = ball.getPosition();

        while(!game.isHit(ball))
        {
            Vector3 tryBall = fitness();
            addVectors = addVectors.add(tryBall);
            ball.hit(tryBall);
        }
        ball.setLocation(ballOrigin);
        return addVectors;
    }
    private Vector3 fitness(){
        Vector3 coordinates = new Vector3();
        coordinates.x = goal.x - ball.getX();
        coordinates.y = goal.y - ball.getY();
        return coordinates;
    }



}