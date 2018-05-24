package com.golf2k18.Engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.golf2k18.objects.Ball;
import com.golf2k18.objects.Terrain;

public abstract class Engine {
    protected Terrain terrain;
    protected Ball ball;
    private float mass;
    private final float GRAVITY = 9.81f;
    protected final double STOP_TOLERANCE = 0.2;
    protected float dt = Gdx.graphics.getDeltaTime();

    public Engine(Terrain terrain, Ball ball) {
        this.terrain = terrain;
        this.ball = ball;
        this.mass = ball.getMass();
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
    protected Vector3 getAcceleration(Vector3 position, Vector3 velocity)
    {
        Vector3 v = calcGravity(position);
        v.add(calcFriction(velocity));
        v.scl(1/mass);
        return v;
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

    protected Vector3 derivePos(Vector3 position, Vector3 velocity){
        return new Vector3(position.x + dt*velocity.x,position.y + dt*velocity.y,0);
    }

    public abstract void updateBall();

    public Terrain getTerrain() {
        return terrain;
    }
}
