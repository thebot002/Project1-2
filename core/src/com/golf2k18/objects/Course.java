package com.golf2k18.objects;

import java.io.Serializable;
import java.util.ArrayList;
/**TODO
 * Class that contains all the information relative to the different courses.
 */
public class Course implements Serializable {
    private ArrayList<Terrain> course;
    private String name;

    /**
     * Takes properties from the course class
     * @param course
     */
    public Course(ArrayList<Terrain> course, String name) {
        this.course = course;
        this.name = name;
    }

    public Course(Terrain terrain, String name){
        course = new ArrayList<>();
        course.add(terrain);
        this.name = name;
    }

    /**
     *Takes properties from the terrain class
     * @param i
     * @return a new course with the desired properties
     */
    public Terrain getTerrain(int i){
        return course.get(i);
    }

    public String getName(){
        return name;
    }
}
