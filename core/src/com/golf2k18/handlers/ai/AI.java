
package com.golf2k18.handlers.ai;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector3;
import com.golf2k18.engine.Engine;
import com.golf2k18.engine.solver.Euler;
import com.golf2k18.engine.solver.Solver;
import com.golf2k18.handlers.Bot;
import com.golf2k18.objects.Ball;
import com.golf2k18.objects.Terrain;
import com.golf2k18.states.game.Game;

import java.util.ArrayList;


public class AI extends Bot {
    private Ball ball;
    private Terrain terrain;
   // private Vector3 ballDirection; = terrain.getHole().add(ball.getPosition().scl(-1));
    private Euler solver;
    private Vector3 tempBallVelocity;
    private Vector3 newPosition;
    private ArrayList<Vector3> velocities = new ArrayList();
    private int count;


    public Vector3 holeInOne(Game game, Vector3 ballVelocity){
        terrain = game.getTerrain();
       // ball = game.getBall();
        solver = new Euler();
        solver.setEngine(game.getEngine());
        Vector3 tempBallVelocity = ballVelocity;
        Vector3 newPosition = solver.solvePos(game.getBall().getPosition(),tempBallVelocity);

        while(newPosition.dst(terrain.getHole()) > (terrain.getHOLE_DIAM() / 2) + 0.6f){
            tempBallVelocity = tempBallVelocity.scl(.1f);
            newPosition = solver.solvePos(game.getBall().getPosition(),tempBallVelocity);

        }
        return tempBallVelocity;

    }

    @Override
    public void handleInput(Game game) {
        super.handleInput(game);
        terrain = game.getTerrain();
        // ball = game.getBall();
        solver = new Euler();
        solver.setEngine(game.getEngine());
        if(velocities.size() == 0) {
            tempBallVelocity = game.getBall().getPosition();
            velocities.add(tempBallVelocity);
        }
        newPosition = solver.solvePos(game.getBall().getPosition(),tempBallVelocity);

        if(newPosition.dst(terrain.getHole()) > (terrain.getHOLE_DIAM() / 2 + .2f)){
            count++;
            if(velocities.size() < 1) {
                tempBallVelocity = tempBallVelocity.add(.3f);
            }
            else{
                velocities.add(tempBallVelocity);
                tempBallVelocity = velocities.get(count).add(.3f);
            }
            newPosition = solver.solvePos(game.getBall().getPosition(),tempBallVelocity);

        }
        else{
            System.out.println("i got here");
            game.getBall().hit(tempBallVelocity.add(new Vector3(-7,-7,0)));
            velocities = new ArrayList<>();
            count = 0;
            System.out.println("even farther");
        }
        System.out.println("X = " + newPosition.x + " " + "Y = " + newPosition.y);
    }
}

