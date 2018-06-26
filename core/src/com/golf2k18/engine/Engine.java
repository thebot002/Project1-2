package com.golf2k18.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.golf2k18.engine.solver.Solver;
import com.golf2k18.objects.Ball;
import com.golf2k18.objects.Terrain;
import java.util.Random;


/**
 * Our physics engine
 */
public class Engine {
    protected Terrain terrain;
    private Ball ball;
    private float mass;
    private final float GRAVITY = 9.81f;
    protected final double STOP_TOLERANCE_VELOCITY = 0.02;
    protected final double STOP_TOLERANCE_ACCELERATION = 0.9;
    protected final double GOAL_TOLERANCE = 15.0;
    protected float dt = Gdx.graphics.getDeltaTime();
    private Solver sherlock;


    private float radius;
    private final float MARGIN_RADIUS;

    /**
     * The class' constructor
     * @param terrain the terrain
     * @param ball the ball
     * @param solver the solver for differential equations
     */
    public Engine(Terrain terrain, Ball ball, Solver solver) {
        this.terrain = terrain;
        this.ball = ball;
        this.mass = ball.getMass();
        this.sherlock = solver;

        MARGIN_RADIUS = .6f;
        radius = (terrain.getHOLE_DIAM() / 2) + MARGIN_RADIUS;

        solver.setEngine(this);
    }

    public float getDt() {
        return dt;
    }

    private Vector3 calcGravity(Vector3 position)
    {
        Vector3 Fz = new Vector3();
        Fz.x = -mass*GRAVITY*terrain.getFunction().evaluateXDeriv(position.x,position.y);
        Fz.y = -mass*GRAVITY*terrain.getFunction().evaluateYDeriv(position.x,position.y);
        return Fz;
    }
    private Vector3 calcFriction(Vector3 velocity)
    {
        Vector3 v = new Vector3(velocity);
        if(v.len() != 0.0) v.scl(1/v.len());
        v.scl(-terrain.getMU()*mass*GRAVITY);
        return v;
    }

    /**
     * Calculates the acceleration of the ball
     * @param position the ball's position at a certain time
     * @param velocity the ball's velocity at a certain time
     * @return the ball's acceleration
     */
    public Vector3 getAcceleration(Vector3 position, Vector3 velocity)
    {
        Vector3 v = calcGravity(position);
        v.add(calcFriction(velocity));
        v.scl(1/mass);
        return v;
    }

    /**
     * Updates the ball's position
     */
    public float updateBall(float dt){
        this.dt = dt;

        Vector3 position = ball.getPosition();
        Vector3 velocity = ball.getVelocity();

        Vector3 newVel = sherlock.solveVel(new Vector3(position),new Vector3(velocity));
        ball.getVelocity().set(newVel);
        Vector3 newPos = sherlock.solvePos(new Vector3(position),new Vector3(velocity));
        ball.getPosition().set(newPos);

        updateBall(newPos,newVel);

        return position.dst(newPos);
    }

    protected void updateBall(Vector3 position, Vector3 velocity){
        //stop the ball
        if(velocity.len() < STOP_TOLERANCE_VELOCITY && calcGravity(position).len() < STOP_TOLERANCE_ACCELERATION){
            ball.setStopped();
        }

        if(ball.getPosition().dst(terrain.getHole()) < (terrain.getHOLE_DIAM()/2) && (velocity.len() <= GOAL_TOLERANCE)){
            ball.setStopped();
        }

        //check for collisions
        if(position.x <= 0){
            Vector3 n = new Vector3(1,0,0);
            bounce(velocity, n);
            ball.getVelocity().set(n);
            ball.getPosition().x = 0;
        }
        if(position.x >= terrain.getWidth()){
            Vector3 n = new Vector3(-1,0,0);
            bounce(velocity, n);
            ball.getVelocity().set(n);
            ball.getPosition().x = terrain.getWidth();
        }
        if(position.y <= 0){
            Vector3 n = new Vector3(0,1,0);
            bounce(velocity, n);
            ball.getVelocity().set(n);
            ball.getPosition().y = 0;
        }
        if(position.y >= terrain.getHeight()){
            Vector3 n = new Vector3(0,-1,0);
            bounce(velocity,n);
            ball.getVelocity().set(n);
            ball.getPosition().y = terrain.getHeight();
        }

        ball.getPosition().z = terrain.getFunction().evaluateF(position.x,position.y);
    }

    private void bounce(Vector3 velocity, Vector3 n){
        Vector3 v = new Vector3(velocity);
        float dot = v.dot(n) * 2;
        n.scl(dot);
        n.add(v.scl(-1));
        n.scl(-1);
    }

    public boolean isGoal() {
        Vector3 pos = ball.getPosition();
        boolean goal = false;
        if ((pos.dst(terrain.getHole()) < radius)) {
            if (ball.isStopped())
                goal = true;
        }
        return goal;
    }
    public Vector3 noise(){
        Random r = new Random();
        Vector3 vel = ball.getVelocity();
        float noiseX = (float)r.nextGaussian();
        float noiseY = (float)r.nextGaussian();
        vel.add(noiseX, noiseY, 0);
        return vel;
    }
}
