package com.golf2k18.objects;

import java.util.ArrayList;
/**TODO
 * Class that contains all the information relative to the different courses.
 */
public class Course {
    ArrayList<Terrain> course;

    /**
     * Takes properties from the course class
     * @param course
     */
    public Course(ArrayList<Terrain> course) {
        this.course = course;
    }

    /**
     *Takes properties from the terrain class
     * @param i
     * @return a new course with the desired properties
     */
    public Terrain getTerrain(int i){
        return course.get(i);
    }
}
