package com.golf2k18.Engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.golf2k18.objects.Ball;
import com.golf2k18.objects.Terrain;

public class Euler extends Engine {

    public Euler(Terrain terrain,Ball ball) {
        super(terrain,ball);
    }

    private Vector3 eulerV(Vector3 position, Vector3 velocity){
        float aX = getAcceleration(position,velocity).x;
        float aY = getAcceleration(position,velocity).y;
        return new Vector3(velocity.x + dt*aX, velocity.y + dt*aY, 0);
    }

    @Override
    public void updateBall() {
        dt = Gdx.graphics.getDeltaTime();

        Vector3 pos = ball.getPosition();
        Vector3 vel = ball.getVelocity();

        Vector3 newVel = eulerV(pos,vel);
        ball.updateVelocity(newVel);
        Vector3 newPos = derivePos(pos,newVel);
        ball.updateLocation(newPos);

        super.updateBall(newPos,newVel);
    }
}