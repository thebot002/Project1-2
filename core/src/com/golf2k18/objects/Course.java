package com.golf2k18.objects;

public class Course {
    //    private Point[][] grid;
//    private Function f;
    public final double MU = 0.5;
//    public static final int UNIT = 1;
    private static int courseWidth;
    private static int courseHeight;
    public String[] formula;
    public double[] start = {0,0,0};
    public double[] goal = {0.0,1.0,0.0};
    public double tolerance = 0.02;





    //default constructor
    public Course(int width, int height, String[] function) {
        this.formula = function;
        courseWidth = width;
        courseHeight = height;

    }
}
//
//    public Course(Function f){
//        this.f = f;
//    }
//}
