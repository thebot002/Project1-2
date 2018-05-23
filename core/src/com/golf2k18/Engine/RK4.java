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

        Vector3 pos_k2 = new Vector3(x_k2,y_k2,0);
        Vector3 vel_k2 = new Vector3(xV_k2,yV_k2,0);

        Vector3 force_k3 = getAcceleration(pos_k2,vel_k2);
        float x_k3 = position.x + (x_k2 * dt/2) + (dt * xV_k2);
        float y_k3 = position.y + (y_k2 * dt/2) + (dt * yV_k2);
        float xV_k3 = xV_k2 + (dt/2 * force_k3.x);
        float yV_k3 = yV_k2 + (dt/2 * force_k3.y);

        Vector3 pos_k3 = new Vector3(x_k3,y_k3,0);
        Vector3 vel_k3 = new Vector3(xV_k3,yV_k3,0);

        Vector3 force_k4 = getAcceleration(pos_k3,vel_k3);
        float x_k4 = position.x + (x_k3 * dt) + (dt * xV_k3);
        float y_k4 = position.y + (y_k3 * dt) + (dt * yV_k3);
        float xV_k4 = xV_k3 + (dt * force_k4.x);
        float yV_k4 = yV_k3 + (dt * force_k4.y);

        float xV = ball.getVelocity().x + (dt * (dt/6) * force_k1.x) + (2*force_k2.x) + (2*force_k3.x) + force_k4.x;
        float yV = ball.getVelocity().y + (dt * (dt/6) * force_k1.y) + (2*force_k2.y) + (2*force_k3.y) + force_k4.y;

        ball.updateVelocity(new Vector3(xV,yV,0));

        float posX = position.x + ((dt/6)*(xV_k1 + (2*xV_k2) + (2* xV_k3) + xV_k4));
        float posY = position.y + ((dt/6)*(yV_k1 + (2*yV_k2) + (2* yV_k3) + yV_k4));

        ball.updateLocation(new Vector3(posX,posY,0));
    }

    public void updateBall() {
        Vector3 position = ball.getPosition();
        Vector3 velocity = ball.getVelocity();
        rk4(position,velocity);
        ball.setZ(terrain.getFunction().evaluateF(ball.getX(),ball.getY()));
    }
}
