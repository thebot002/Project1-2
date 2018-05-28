package com.golf2k18.objects;

import com.badlogic.gdx.math.Vector3;

import java.io.Serializable;

public class Terrain implements Serializable {

    private int width;
    private int height;
    private Vector3 start;

    private Goal goal;
    private boolean goalHit;

        private float scale = 1; //formula unit to world unit
    private Vector3 offset;

    private String name;
    private float MU = 0.5f;

    private Function function;

    public Terrain(int width, int height, Vector3 start, Vector3 goal, Function function, String name) {
        this.width = width;
        this.height = height;
        this.start = start;
        this.goal = new Goal(goal);
        this.name = name;
        offset = new Vector3(0,0,0);
        this.function = function;
    }

    public Function getFunction() {
        return function;
    }

    public String getName() {
        return name;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Vector3 getStart() {
        return start;
    }

    public Goal getGoal() {return goal;}

    public float getMU() {
        return MU;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void setOffset(Vector3 offset){
        this.offset.set(offset);
    }


}

