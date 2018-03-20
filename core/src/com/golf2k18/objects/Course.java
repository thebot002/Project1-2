package com.golf2k18.objects;

public class Course {
    Point[][] grid;
    Function f;

    //default constructor
    public Course() {
        grid = new Point[100][100]; //default size 100:100

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
