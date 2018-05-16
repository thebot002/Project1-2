package com.golf2k18.objects;

import java.util.ArrayList;

public class Course {
    ArrayList<Terrain> course;

    public Course(ArrayList<Terrain> course) {
        this.course = course;
    }

    public Terrain getTerrain(int i){
        return course.get(i);
    }
}
