package com.golf2k18.objects;

import com.badlogic.gdx.math.Vector3;

public class Wall {
    Vector3 p00, p10, p01, p11;

    float thickness = .2f;

    public Wall(Vector3 p0, Vector3 p1) {
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
}
