package com.golf2k18.objects;

import com.badlogic.gdx.math.Vector3;

public class Goal {
    private Vector3 location;
    private boolean hit;
    private final double radius = 10;
    public Goal(Vector3 pos){
        this.hit = false;
        this.location = pos;
    }
    public boolean isHit(Ball ball) {
        Vector3 pos = ball.getPosition();
        if ((pos.x < location.x + radius / 2 || pos.x > location.x - radius/2)&&(pos.y < location.y + radius / 2 || pos.y > location.y - radius/2)) {
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
