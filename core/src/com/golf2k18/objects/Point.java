package com.golf2k18.objects;

public class Point {
    private double height;
    private double value; //texture

    public Point(double height, double value) {
        this.height = height;
        this.value = value;
    }
    public double getHeight() {
        return height;
    }
    public double getValue() {
        return value;
    }
}
