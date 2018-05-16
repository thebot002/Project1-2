package com.golf2k18.objects;

import com.badlogic.gdx.math.Vector3;

import java.io.Serializable;

public class Terrain implements Serializable {
    private int width;
    private int height;

    private Vector3 start;
    private Vector3 goal;

    private double scale = 1; //function unit to world unit

    private String name = "cosinus";
    private String[] formula;
    private double MU = 0.5;

    private double tolerance = 0.02;
    private double vMax = 3;
    private Function function;

    public Terrain(int width, int height, Vector3 start, Vector3 goal, String[] formula, String name) {
        this.width = width;
        this.height = height;
        this.start = start;
        this.goal = goal;
        this.formula = formula;
        this.name = name;

        function = new Function(formula);
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

    public double getMU() {
        return MU;
    }
}
