package com.golf2k18.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;

public class Engine
{
    private Terrain terrain;
    private final double GRAVITY = 9.81;
    private final double STOP_TOLERANCE = 0.2;

    public Engine(Terrain terrain)
    {
        String[] str = {"x","cos"};
        //String[] str = {"0"};
        //String[] str = {"0","0.5","x","5","-","2","^","^","-"};
        //String[] str = {"y","x","*"};
        //String[] str = {"0.2", "y", "*", "0.1", "x", "*", "+", "0.03", "x", "2", "^", "*", "+"};

        this.terrain = terrain;
    }
    private Vector3 calcGravity(Ball ball)
    {
        Vector3 Fz = new Vector3();
        Fz.x = (float)((-ball.getMass()*GRAVITY* terrain.getFunction().evaluateXDeriv(ball.getX(), ball.getY())));
        Fz.y = (float)((-ball.getMass()*GRAVITY* terrain.getFunction().evaluateYDeriv(ball.getX(),ball.getY())));
        return Fz;
    }
    private Vector3 calcFriction(Ball ball)
    {
        Vector3 v = ball.getVelocity();
        if(v.len() != 0.0) v.scl(1/v.len());
        v.scl((float)(-terrain.getMU()*ball.getMass()*GRAVITY));
        return v;
    }
    public Vector3 getAcceleration(Ball ball)
    {
        Vector3 v = calcGravity(ball);
        v.add(calcFriction(ball));
        v.scl((float)(1/ball.getMass()));
        return v;
    }
    private double eulerX(Ball ball, double dt)
    {
        double vX = ball.getVelocity().x;
        return ball.getX() + dt*vX;

    }
    private void eulerVx(Ball ball, double dt)
    {
        double aX = getAcceleration(ball).x;
        ball.updateVelocityX(ball.getVelocity().x + dt*aX);
    }
    private double eulerY(Ball ball, double dt)
    {
        double vY = ball.getVelocity().y;
        return ball.getY() + dt*vY;

    }
    private void eulerVy(Ball ball, double dt)
    {
        double aY = getAcceleration(ball).y;
        ball.updateVelocityY(ball.getVelocity().y + dt*aY);
    }

    public void updateBall(Ball ball)
    {
        ball.updateLocation(eulerX(ball,Gdx.graphics.getDeltaTime()),eulerY(ball,Gdx.graphics.getDeltaTime()));
        ball.setZ(terrain.getFunction().evaluateF(ball.getX(),ball.getY()));

        if(ball.getVelocity().len() <= STOP_TOLERANCE && (calcGravity(ball).len() / ball.getMass()) <= STOP_TOLERANCE) ball.setStopped();
        eulerVx(ball,Gdx.graphics.getDeltaTime());
        eulerVy(ball,Gdx.graphics.getDeltaTime());
    }

    public Terrain getTerrain() {
        return terrain;
    }
}
