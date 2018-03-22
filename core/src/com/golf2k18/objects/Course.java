package com.golf2k18.objects;

public class Course {
    private Point[][] grid;
    private Function f;
    public static final int UNIT = 1;
    private static int courseWidth;
    private static int courseHeight;

    //default constructor
    public Course(int width, int height) {
        courseWidth = width;
        courseHeight = height;
        grid = new Point[width*UNIT][height*UNIT]; //default size 100:100

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                grid[i][j] = new Point(Math.random()*10,Math.random()*2); //random heights & friction value
            }
        }
    }

    public Course(Function f){
        this.f = f;
    }
}
