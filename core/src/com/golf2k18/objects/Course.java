package com.golf2k18.objects;

import java.io.Serializable;
import java.util.ArrayList;
/**TODO
 * Class that contains all the information relative to the different courses.
 */
public class Course implements Serializable {
    private ArrayList<Terrain> course;
    private String name;
    private int[] scores;

    /**
     * Takes properties from the course class
     * @param course
     */
    public Course(ArrayList<Terrain> course, String name) {
        this.course = course;
        this.name = name;
        scores = new int[course.size()];
    }

    public Course(Terrain terrain){
        course = new ArrayList<>();
        course.add(terrain);
        this.name = terrain.getName();
        scores = new int[1];
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

    public int getSize(){
        return course.size();
    }

    public void setScore(int courseNumber,int score){
        scores[courseNumber] = score;
    }

    public int[] getScores() {
        return scores;
    }
}
