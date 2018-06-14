package com.golf2k18.objects;

import com.badlogic.gdx.math.Vector3;

import java.io.Serializable;

public class Goal implements Serializable {
    private Vector3 location;
    private boolean hit;
    private final double diameter = 10;
    public Goal(Vector3 pos){
        this.hit = false;
        this.location = pos;
    }
    public boolean isHit(Ball ball) {
        Vector3 pos = ball.getPosition();
        if (pos.dst(location) < diameter/2) {
            if(ball.isStopped()){
                hit = true;
            }
        }
        return hit;
    }
    public Vector3 getLocation() {
        return location;
    }
}
