package com.golf2k18.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.golf2k18.StateManager;
import com.golf2k18.engine.solver.Solver;
import com.golf2k18.function.Matrix;
import com.golf2k18.objects.Ball;
import com.golf2k18.objects.Terrain;
import com.golf2k18.objects.Wall;

import java.util.ArrayList;
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

    private float[][] wind;

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

        radius = (terrain.getHOLE_DIAM() / 2);

        solver.setEngine(this);

        double r = Math.random();
        float[][] w = {{(float)Math.cos(r*2*Math.PI)},{(float)Math.sin(r*2*Math.PI)}};
        setWind(w);
    }

    public float getDt() {
        return dt;
    }

    private Vector3 calcGravity(Vector3 position) {
        Vector3 Fz = new Vector3();
        Fz.x = -mass*GRAVITY*terrain.getFunction().evaluateXDeriv(position.x,position.y);
        Fz.y = -mass*GRAVITY*terrain.getFunction().evaluateYDeriv(position.x,position.y);
        return Fz;
    }

    private Vector3 calcFriction(Vector3 velocity) {
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
    public Vector3 getAcceleration(Vector3 position, Vector3 velocity) {
        Vector3 v = calcGravity(position);
        v.add(calcFriction(velocity));
        if(StateManager.settings.hasNoise()) v.add(noise(velocity));
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
        ball.getVelocity().set(newVel.cpy());
        Vector3 newPos = sherlock.solvePos(new Vector3(position),new Vector3(velocity));
        ball.getPosition().set(newPos);

        updateBall(newPos,newVel);

        return position.dst(newPos);
    }

    protected void updateBall(Vector3 position, Vector3 velocity){
        //stop the ball
        if(velocity.len() < STOP_TOLERANCE_VELOCITY+StateManager.settings.getWindIntensity() && calcGravity(position).len() < STOP_TOLERANCE_ACCELERATION){
            ball.setStopped();
        }

        //check for collisions
        if(position.x <= 0){
            Vector3 n = new Vector3(1,0,0);
            bounce(velocity, n);
            ball.getPosition().x = 0;
        }
        if(position.x >= terrain.getWidth()){
            Vector3 n = new Vector3(-1,0,0);
            bounce(velocity, n);
            ball.getPosition().x = terrain.getWidth();
        }
        if(position.y <= 0){
            Vector3 n = new Vector3(0,1,0);
            bounce(velocity, n);
            ball.getPosition().y = 0;
        }
        if(position.y >= terrain.getHeight()){
            Vector3 n = new Vector3(0,-1,0);
            bounce(velocity,n);
            ball.getPosition().y = terrain.getHeight();
        }

        collide();

        ball.getPosition().z = terrain.getFunction().evaluateF(position.x,position.y);
    }

    private void bounce(Vector3 velocity, Vector3 n){
        Vector3 v = new Vector3(velocity);
        float dot = v.dot(n) * 2;
        n.scl(dot);
        n.add(v.scl(-1));
        n.scl(-1);
        ball.getVelocity().set(n);
    }

    public boolean isGoal() {
        Vector3 pos = ball.getPosition().cpy();
        Vector3 vel = ball.getVelocity().cpy();
        Vector3 hol = terrain.getHole().cpy();
        pos.z = 0f;
        vel.z = 0f;
        hol.z = 0f;
        boolean goal = false;
        if ((pos.dst(hol) < radius) && vel.len() < GOAL_TOLERANCE) {
            goal = true;
        }
        return goal;
    }

    public Vector3 noise(Vector3 vel){
        double r = new Random().nextGaussian()/10;

        float[][] rotArr = {
                {(float)Math.cos(r),(float)-Math.sin(r)},
                {(float)Math.sin(r),(float)Math.cos(r)}};
        Matrix rot = new Matrix(rotArr);
        Matrix w = new Matrix(wind);

        Matrix nW = Matrix.multiplication(rot,w);

        float noiseX = nW.get(0,0);
        float noiseY = nW.get(1,0);

        Vector3 v = new Vector3(noiseX,noiseY,0).scl(StateManager.settings.getWindIntensity()).add(vel);
        return v.scl((float)(-1.225f*0.47f*vel.len()*Math.PI*Math.pow(ball.getDiameter()/2,2))/2);
    }

    public double getGoalTolerance()
    {
        return GOAL_TOLERANCE;
    }

    public void collide() {
        float r = ball.getDiameter()/2f;
        Vector3 C = ball.getPosition().cpy();

        for (Wall wall : terrain.getObstacles()) {
            for (int i = 0; i < 4; i++) {
                ArrayList<Vector3> edge = wall.getEdge(i);
                Vector3 A = edge.get(0).cpy();
                Vector3 B = edge.get(1).cpy();

                Vector3 ab = B.cpy().sub(A);
                Vector3 ac = A.cpy().sub(C);

                float d = ab.dot(ac);

                if(d < ab.len() && d > 0){
                    Vector3 D = A.cpy().add(ab.cpy().nor().scl(d));
                    Vector3 dc = D.cpy().sub(C);

                    if(dc.len() <= r){
                        //bounce(ball.getVelocity(),dc.nor());
                        return;
                    }
                }
            }
        }
    }

    public void sclDt(float scl){
        dt = dt * scl;
    }

    public void setWind(float[][] wind){
        this.wind = wind;
    }
}
