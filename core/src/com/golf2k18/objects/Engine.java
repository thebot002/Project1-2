package com.golf2k18.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;

public class Engine
{
    private Terrain terrain;
    private final float GRAVITY = 9.81f;
    private final double STOP_TOLERANCE = 0.2;

    public Engine(Terrain terrain)
    {
        //String[] str = {"x","cos"};
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
        v.scl(-terrain.getMU()*ball.getMass()*GRAVITY);
        return v;
    }
    public Vector3 getAcceleration(Ball ball)
    {
        Vector3 v = calcGravity(ball);
        v.add(calcFriction(ball));
        v.scl(1/ball.getMass());
        return v;
    }

    private float eulerX(Ball ball, float dt)
    {
        float vX = ball.getVelocity().x;
        return ball.getX() + dt*vX;

    }
    private void eulerVx(Ball ball, float dt)
    {
        float aX = getAcceleration(ball).x;
        ball.updateVelocityX(ball.getVelocity().x + dt*aX);
    }
    private float eulerY(Ball ball, float dt)
    {
        float vY = ball.getVelocity().y;
        return ball.getY() + dt*vY;

    }
    private void eulerVy(Ball ball, float dt)
    {
        float aY = getAcceleration(ball).y;
        ball.updateVelocityY(ball.getVelocity().y + dt*aY);
    }

    public void rk4(Ball ball,float dt)
    {
        float xF_k1 = getAcceleration(ball).x;
        float xV = ball.getVelocity().x;
        float yF_k1 = getAcceleration(ball).y;
        float yV = ball.getVelocity().y;

        float x_k1 = ball.getX() + dt * xV;
        float y_k1 = ball.getY() + dt * yV;
        float xV_k1 = xV + dt + xF_k1/ball.getMass();
        float yV_k1 = yV + dt + yF_k1/ball.getMass();

        ball.updateVelocityX(xV_k1);
        ball.updateVelocityY(yV_k1);
        ball.updateLocation(x_k1, y_k1);

        float xF_k2 = getAcceleration(ball).x;
        float yF_k2 = getAcceleration(ball).y;
        float x_k2 = ball.getX() +x_k1 * dt/2 + dt* xV_k1; //or current velocity? ,most likely current velocity
        float y_k2 = ball.getY() +y_k1 * dt/2 + dt* yV_k1;
        float xV_k2 = xV_k1 + dt/2 * xF_k2/ball.getMass();
        float yV_k2 = yV_k1 + dt/2 * yF_k2/ball.getMass();

        ball.updateVelocityX(xV_k2);
        ball.updateVelocityY(yV_k2);
        ball.updateLocation(x_k2, y_k2);

        float xF_k3 = getAcceleration(ball).x;
        float yF_k3 = getAcceleration(ball).y;
        float x_k3 = ball.getX()+ x_k2 * dt/2 + dt * xV_k2;
        float y_k3 = ball.getY()+ y_k2 * dt/2 + dt * yV_k2;
        float xV_k3 = xV_k2 + dt/2 * xF_k3/ball.getMass();
        float yV_k3 = yV_k2 + dt/2 * yF_k3/ball.getMass();

        ball.updateVelocityX(xV_k3);
        ball.updateVelocityY(yV_k3);
        ball.updateLocation(x_k3, y_k3);

        float xF_k4 = getAcceleration(ball).x;
        float yF_k4 = getAcceleration(ball).y;
        float x_k4 = ball.getX()+ x_k3 * dt + dt * xV_k3;
        float y_k4 = ball.getY() + y_k3 * dt + dt * yV_k3;
        float xV_k4 = xV_k3 + dt * xF_k4/ball.getMass();
        float yV_k4 = yV_k3 + dt * yF_k4/ball.getMass();

        ball.updateVelocityX(xV_k4);
        ball.updateVelocityY(yV_k4);
        ball.updateLocation(x_k4, y_k4);

        xV = ball.getVelocity().x + dt * (dt/6)* xF_k1/ball.getMass() + 2*xF_k2/ball.getMass() + 2*xF_k3/ball.getMass() + xF_k4/ball.getMass();
        yV = ball.getVelocity().y + dt * (dt/6) * yF_k1/ball.getMass() + 2*yF_k2/ball.getMass() + 2*yF_k3/ball.getMass() + yF_k4/ball.getMass();

        ball.updateVelocityX(xV);
        ball.updateVelocityY(yV);

        float posX = ball.getX() + (dt/6)*(xV_k1 + 2*xV_k2 + 2* xV_k3 + xV_k4);
        float posY = ball.getY() + (dt/6)*(yV_k1 + 2*yV_k2 + 2* yV_k3 + yV_k4);

        ball.updateLocation(posX,posY);
        if(ball.getVelocity().len() <= STOP_TOLERANCE && (calcGravity(ball).len() / ball.getMass()) <= STOP_TOLERANCE) ball.setStopped();
        eulerVx(ball,Gdx.graphics.getDeltaTime());
        eulerVy(ball,Gdx.graphics.getDeltaTime());
    }

    /*
    public void updateBall(Ball ball)
    {
        ball.updateLocation(eulerX(ball,Gdx.graphics.getDeltaTime()),eulerY(ball,Gdx.graphics.getDeltaTime()));
        ball.setZ(terrain.getFunction().evaluateF(ball.getX(),ball.getY()));

        if(ball.getVelocity().len() <= STOP_TOLERANCE && (calcGravity(ball).len() / ball.getMass()) <= STOP_TOLERANCE) ball.setStopped();
        eulerVx(ball,Gdx.graphics.getDeltaTime());
        eulerVy(ball,Gdx.graphics.getDeltaTime());
    }
*/
    public void updateBallRK4(Ball ball)
    {
        rk4(ball,Gdx.graphics.getDeltaTime());
        ball.setZ(terrain.getFunction().evaluateF(ball.getX(),ball.getY()));

        if(ball.getVelocity().len() <= STOP_TOLERANCE && (calcGravity(ball).len() / ball.getMass()) <= STOP_TOLERANCE) ball.setStopped();
        rk4(ball,Gdx.graphics.getDeltaTime());
    }

    public Terrain getTerrain() {
        return terrain;
    }
}
