package com.golf2k18.objects;

public class Course {
    public int courseWidth;
    public int courseHeight;
    public double MU = 0.5;
    public double[] start = {0,0,0};
    public double[] goal = {0.0,1.0,0.0};
    public double tolerance = 0.02;
    public double vMax = 3;

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
