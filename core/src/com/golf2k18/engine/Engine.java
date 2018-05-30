package com.golf2k18.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.golf2k18.engine.solver.Solver;
import com.golf2k18.objects.Ball;
import com.golf2k18.objects.Terrain;

/**
 * Our physics engine
 */
public class Engine {
    protected Terrain terrain;
    private Ball ball;
    private float mass;
    private final float GRAVITY = 9.81f;
    protected final double STOP_TOLERANCE = 0.2;
   // protected float dt = 0.001f;
    protected float dt = Gdx.graphics.getDeltaTime();
    private Solver sherlock;

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
        solver.setEngine(this);
    }

    public float getDt() {
        return dt;
    }


    private Vector3 calcGravity(Vector3 position)
    {
        Vector3 Fz = new Vector3();
        Fz.x = -mass*GRAVITY* terrain.getFunction().evaluateXDeriv(position.x, position.y);
        Fz.y = -mass*GRAVITY* terrain.getFunction().evaluateYDeriv(position.x, position.y);
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
    public void updateBall(){
        dt = Gdx.graphics.getDeltaTime();

        Vector3 position = ball.getPosition();
        Vector3 velocity = ball.getVelocity();

        Vector3 newVel = sherlock.solveVel(new Vector3(position),new Vector3(velocity));
        ball.updateVelocity(new Vector3(newVel));
        Vector3 newPos = sherlock.solvePos(new Vector3(position),new Vector3(velocity));
        ball.updateLocation(newPos);

        updateBall(newPos,newVel);
        if(ball.getPosition().z <= 0){
            ball.updateLocation(position);
        }
    }

    protected void updateBall(Vector3 position, Vector3 velocity){
        //stop the ball
        if(velocity.len() <= STOP_TOLERANCE && (calcGravity(position).len() / mass) <= STOP_TOLERANCE) ball.setStopped();

        //check for collisions
        if(position.x <= 0){
            Vector3 n = new Vector3(1,0,0);
            bounce(velocity, n);
            ball.updateVelocity(n);
            updateBall();
        }
        if(position.x >= terrain.getWidth()){
            Vector3 n = new Vector3(-1,0,0);
            bounce(velocity, n);
            ball.updateVelocity(n);
            updateBall();
        }
        if(position.y <= 0){
            Vector3 n = new Vector3(0,1,0);
            bounce(velocity, n);
            ball.updateVelocity(n);
            updateBall();
        }
        if(position.y >= terrain.getHeight()){
            Vector3 n = new Vector3(0,-1,0);
            bounce(velocity,n);
            ball.updateVelocity(n);
            updateBall();
        }

        ball.setZ(terrain.getFunction().evaluateF(position.x,position.y));
    }

    private void bounce(Vector3 velocity, Vector3 n){
        Vector3 v = new Vector3(velocity);
        float dot = v.dot(n) * 2;
        n.scl(dot);
        n.add(v.scl(-1));
        n.scl(-1);
    }

    public void sclDt(float scl){
        dt = dt * scl;
    }
}
