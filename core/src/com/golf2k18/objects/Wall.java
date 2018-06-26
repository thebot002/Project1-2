package com.golf2k18.objects;

import com.badlogic.gdx.math.Vector3;
import com.golf2k18.states.game.Game;

import java.io.Serializable;
import java.util.ArrayList;

public class Wall implements Collider,Serializable {
    private Vector3 p0, p1, p00, p10, p01, p11;

    private final float thickness = .2f;

    public Wall(Vector3 p0, Vector3 p1) {
        this.p0 = p0;
        this.p1 = p1;

        if(p0.equals(p1)){
            p00 = new Vector3(p0.x - thickness,p0.y - thickness,0);
            p01 = new Vector3(p0.x + thickness,p0.y - thickness,0);
            p10 = new Vector3(p0.x - thickness,p0.y + thickness,0);
            p11 = new Vector3(p0.x + thickness,p0.y + thickness,0);
        }
        else {
            p00 = new Vector3(p0.x - thickness,p0.y - thickness,0);
            p01 = new Vector3(p1.x + thickness,p0.y - thickness,0);
            p10 = new Vector3(p0.x - thickness,p1.y + thickness,0);
            p11 = new Vector3(p1.x + thickness,p1.y + thickness,0);
        }
    }

    @Override
    public Wall collide(Game game) {
        return game.getTerrain().getObstacles().get(0);
    }

    public ArrayList<Vector3> getCore(){
        ArrayList<Vector3> points = new ArrayList<>();
        points.add(p0);
        points.add(p1);
        return points;
    }

    public float getThickness(){
        return thickness;
    }

    @Override
    public Vector3 getTopLeftCorner() {
        return p01;
    }

    @Override
    public Vector3 getBottomRightCorner() {
        return p10;
    }
}
