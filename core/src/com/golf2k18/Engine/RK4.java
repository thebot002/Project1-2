package com.golf2k18.Engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.golf2k18.objects.Ball;
import com.golf2k18.objects.Terrain;

public class RK4 extends Engine {


    public RK4(Terrain terrain, Ball ball) {
        super(terrain,ball);
    }

    public void rk4(Vector3 position, Vector3 velocity) {

        Vector3 force_k1 = getAcceleration(position,velocity);

        float x_k1 = position.x + (dt * velocity.x);
        float y_k1 = position.y + (dt * velocity.y);
        float xV_k1 = velocity.x + dt + force_k1.x;
        float yV_k1 = velocity.y + dt + force_k1.y;

        Vector3 pos_k1 = new Vector3(x_k1,y_k1,0);
        Vector3 vel_k1 = new Vector3(xV_k1,yV_k1,0);

        Vector3 force_k2 = getAcceleration(pos_k1,vel_k1);

        float x_k2 = position.x + (x_k1 * dt/2) + (dt* xV_k1); //or current velocity? ,most likely current velocity
        float y_k2 = position.y + (y_k1 * dt/2) + (dt* yV_k1);
        float xV_k2 = xV_k1 + (dt/2 * force_k2.x);
        float yV_k2 = yV_k1 + (dt/2 * force_k2.y);

        /*
        float xF_k3 = getAcceleration(ball).x;
        float yF_k3 = getAcceleration(ball).y;
        float x_k3 = ball.getX()+ x_k2 * dt/2 + dt * xV_k2;
        float y_k3 = ball.getY()+ y_k2 * dt/2 + dt * yV_k2;
        float xV_k3 = xV_k2 + dt/2 * xF_k3;
        float yV_k3 = yV_k2 + dt/2 * yF_k3;

        ball.updateVelocityX(xV_k3);
        ball.updateVelocityY(yV_k3);
        ball.updateLocation(x_k3, y_k3);

        float xF_k4 = getAcceleration(ball).x;
        float yF_k4 = getAcceleration(ball).y;
        float x_k4 = ball.getX()+ x_k3 * dt + dt * xV_k3;
        float y_k4 = ball.getY() + y_k3 * dt + dt * yV_k3;
        float xV_k4 = xV_k3 + dt * xF_k4;
        float yV_k4 = yV_k3 + dt * yF_k4;

        ball.updateVelocityX(xV_k4);
        ball.updateVelocityY(yV_k4);
        ball.updateLocation(x_k4, y_k4);

        xV = ball.getVelocity().x + dt * (dt/6)* xF_k1 + 2*xF_k2 + 2*xF_k3 + xF_k4;
        yV = ball.getVelocity().y + dt * (dt/6) * yF_k1 + 2*yF_k2 + 2*yF_k3 + yF_k4;

        ball.updateVelocityX(xV);
        ball.updateVelocityY(yV);

        float posX = ball.getX() + (dt/6)*(xV_k1 + 2*xV_k2 + 2* xV_k3 + xV_k4);
        float posY = ball.getY() + (dt/6)*(yV_k1 + 2*yV_k2 + 2* yV_k3 + yV_k4);

        ball.updateLocation(posX,posY);*/
    }

    public void updateBall()
    {
        /*rk4(ball,Gdx.graphics.getDeltaTime());
        ball.setZ(terrain.getFunction().evaluateF(ball.getX(),ball.getY()));

        if(ball.getVelocity().len() <= STOP_TOLERANCE && (calcGravity(ball).len() / ball.getMass()) <= STOP_TOLERANCE) ball.setStopped();
        rk4(ball,Gdx.graphics.getDeltaTime());*/
    }

}
