package com.golf2k18.objects;

public class Course {
    protected int courseWidth;
    protected int courseHeight;
    protected String[] formula;
    protected double MU = 0.5;
    protected double[] start = {0,0,0};
    protected double[] goal = {0.0,1.0,0.0};
    protected double tolerance = 0.02;
    protected double vMax = 3;

    private Function function;

    public Function getFunction() {
        return function;
    }

    //default constructor
    public Course(int width, int height, String[] formula) {
        courseWidth = width;
        courseHeight = height;

        function = new Function(formula);
    }
}
