package com.golf2k18.objects;

import com.badlogic.gdx.math.Vector3;

import java.io.Serializable;
import java.util.ArrayList;

public class Wall implements Collider,Serializable {
    private Vector3 p0, p1, p00, p10, p01, p11;
    private float xMin, xMax, yMin, yMax;

    private final float thickness = .2f;
    private final boolean debug = false;

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
            Vector3 dir = p1.cpy().sub(p0).nor();
            dir.z = 0;
            p00 = new Vector3(p0).add(dir.cpy().scl(-thickness)).add(dir.cpy().rotate(90,0,0,1).scl(-thickness));
            p00.z = 0;

            xMin = p00.x;
            xMax = p00.x;
            yMin = p00.y;
            yMax = p00.y;

            p01 = new Vector3(p0).add(dir.cpy().scl(-thickness)).add(dir.cpy().rotate(90,0,0,1).scl(thickness));
            p01.z = 0;

            if(p01.x > xMax) xMax = p01.x;
            if(p01.x < xMin) xMin = p01.x;
            if(p01.y > yMax) yMax = p01.y;
            if(p01.y < yMin) yMax = p01.y;

            p10 = new Vector3(p1).add(dir.cpy().scl(thickness)).add(dir.cpy().rotate(90,0,0,1).scl(-thickness));
            p10.z = 0;

            if(p10.x > xMax) xMax = p10.x;
            if(p10.x < xMin) xMin = p10.x;
            if(p10.y > yMax) yMax = p10.y;
            if(p10.y < yMin) yMax = p10.y;

            p11 = new Vector3(p1).add(dir.cpy().scl(thickness)).add(dir.cpy().rotate(90,0,0,1).scl(thickness));
            p11.z = 0;

            if(p11.x > xMax) xMax = p11.x;
            if(p11.x < xMin) xMin = p11.x;
            if(p11.y > yMax) yMax = p11.y;
            if(p11.y < yMin) yMax = p11.y;
        }
        if (debug){
            System.out.println("p0: "+p0);
            System.out.println("p1: "+p1);
            System.out.println("p00: "+p00);
            System.out.println("p01: "+p01);
            System.out.println("p10: "+p10);
            System.out.println("p11: "+p11);
        }
    }

    public ArrayList<Vector3> getCore(){
        ArrayList<Vector3> points = new ArrayList<>();
        points.add(p0);
        points.add(p1);
        return points;
    }

    public float getThickness() {
        return thickness;
    }

    @Override
    public Vector3 getTopLeftCorner() {
        return p01;
    }

    public Vector3 getBottomLeftCorner(){return p00;}

    @Override
    public Vector3 getBottomRightCorner() {
        return p10;
    }

    public Vector3 getTopRightCorner(){ return p11;}

    public ArrayList<Vector3> getEdge(int i){
        ArrayList<Vector3> edge = new ArrayList<>();
        switch (i){
            case 0:
                edge.add(p00);
                edge.add(p10);
                break;
            case 1:
                edge.add(p10);
                edge.add(p11);
                break;
            case 2:
                edge.add(p11);
                edge.add(p01);
                break;
            case 3:
                edge.add(p01);
                edge.add(p00);
                break;
        }
        return edge;
    }

    public float getxMin() {
        return xMin;
    }

    public float getxMax() {
        return xMax;
    }

    public float getyMin() {
        return yMin;
    }

    public float getyMax() {
        return yMax;
    }
}
