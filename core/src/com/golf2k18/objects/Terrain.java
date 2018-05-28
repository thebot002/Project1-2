package com.golf2k18.objects;

import com.badlogic.gdx.math.Vector3;
import com.golf2k18.function.Function;
import com.golf2k18.function.Spline;

import java.io.Serializable;

/**
 * Class that contains the information about the course.
 */
public class Terrain implements Serializable {

    private int width;
    private int height;
    private Vector3 start;

    private Goal goal;
    private boolean goalHit;
    private Vector3 hole;

    private final float HOLE_DIAM = 1.08f;

        private float scale = 1; //formula unit to world unit
    private float scale = 1; //formula unit to world unit

    private Vector3 offset;
    private String name;

    private float MU = 0.5f;
    private Function function;

    /**
     * Constructor for the Terrain class, which takes a width and height specifying the area of the course,
     * a vector containing the location of the start and one for the goal,
     * it contains an object of the Function class and a String object.
     * @param width variable which specifies the width of the course.
     * @param height variable which specifies the height of the course.
     * @param start variable which contains the location of the start.
     * @param goal variable which contains the location of the goal.
     * @param function object of the Function class which contains the function.
     * @param name variable which gives an id to this specific course.
     */
    public Terrain(int width, int height, Vector3 start, Vector3 goal, Function function, String name) {
        this.width = width;
        this.height = height;
        this.start = start;
        this.goal = new Goal(goal);
        this.hole = goal;
        this.name = name;
        offset = new Vector3(0,0,0);
        this.function = function;
    }

    /**
     * Getter for the Function object.
     * @return Function function.
     */
    public Function getFunction() {return function;}

    /**
     * Getter for the name variable.
     * @return String name.
     */
    public String getName() {return name;}

    /**
     * Getter for the width of the course.
     * @return int width.
     */
    public int getWidth() {return width;}

    /**
     * Getter for the height of the course.
     * @return int height.
     */
    public int getHeight() {return height;}

    /**
     * Getter for the start vector.
     * @return Vector3 start.
     */
    public Vector3 getStart() {return start;}

    public Vector3 getHole() {
        return hole;
    }

    /**
     * Getter for friction coefficient.
     * @return float MU.
     */
    public float getMU() {return MU;}
    /**
     * Setter for the scale of the course.
     * @param scale variable which specifies the scale of the course.
     */
    public void setScale(float scale) {this.scale = scale;}

    /**
     * setter for the offsetVector
     * @param offset variable that contains offset
     */
    public void setOffset(Vector3 offset){this.offset.set(offset);}

    public float getHOLE_DIAM() {
        return HOLE_DIAM;
    }

    public void toSpline(int interval){
        if(function instanceof Spline) return;

        float[][] data = new float[(width*interval)+1][(height*interval)+1];
        float[][] xDeriv = new float[(width*interval)+1][(height*interval)+1];
        float[][] yDeriv = new float[(width*interval)+1][(height*interval)+1];

        for (int i = 0; i <= width ; i++) {
            for (int j = 0; j <=height ; j++) {
                data[i][j] = function.evaluateF(i,j);
                xDeriv[i][j] = function.evaluateXDeriv(i,j);
                yDeriv[i][j] = function.evaluateYDeriv(i,j);
            }
        }
        function = new Spline(data,xDeriv,yDeriv);
    public Goal getGoal() {return goal;}

    public float getMU() {
        return MU;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void setOffset(Vector3 offset){
        this.offset.set(offset);
    }


}

