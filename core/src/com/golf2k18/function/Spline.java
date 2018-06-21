package com.golf2k18.function;

import com.badlogic.gdx.math.Vector3;
import com.golf2k18.models.Matrix;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *This class is used to make splines interpolations.
 */
public class Spline implements Function, Serializable {
    private float[][] data;
    private Matrix[][] coefficients;

    private float[][] xDeriv;
    private float[][] yDeriv;
    private float[][] xyDeriv;

    private float[][] A1 = {{1,0,0,0},{0,0,1,0},{-3,3,-2,-1},{2,-2,1,1}};
    private float[][] A2 = {{1,0,-3,2},{0,0,3,-2},{0,1,-2,1},{0,0,-1,1}};

    /**
     * Constructor of the class
     * @param data a matrix
     * @param xDeriv derivative of x
     * @param yDeriv derivative of y
     */
    public Spline(float[][] data, float[][] xDeriv, float[][] yDeriv){
        this.data = data;
        coefficients = new Matrix[data.length-1][data[0].length-1];

        this.xDeriv = xDeriv;
        this.yDeriv = yDeriv;

        xyDeriv = new float[data.length][data[0].length];
        for (int i = 2; i < data.length - 2; i++) {
            for (int j = 2; j < data[0].length-2; j++) {
                if(i==2){
                    xyDeriv[0][j] = forwardDiff(1,yDeriv[i][0],yDeriv[i][1],yDeriv[i][2],yDeriv[i][3],yDeriv[i][4]);
                    xyDeriv[1][j] = assymetricDiff(1,yDeriv[i][0],yDeriv[i][1],yDeriv[i][2],yDeriv[i][3],yDeriv[i][4]);
                }
                if(i == data.length - 3){
                    xyDeriv[data.length-1][j] = forwardDiff(-1,yDeriv[i][data[0].length-1],yDeriv[i][data[0].length-2],yDeriv[i][data[0].length-3],yDeriv[i][data[0].length-4],yDeriv[i][data[0].length-5]);
                    xyDeriv[data.length-2][j] = assymetricDiff(-1,yDeriv[i][data[0].length-1],yDeriv[i][data[0].length-2],yDeriv[i][data[0].length-3],yDeriv[i][data[0].length-4],yDeriv[i][data[0].length-5]);
                }
                xyDeriv[i][j] = centeredDiff(1,yDeriv[i][j-2],yDeriv[i][j-1],yDeriv[i][j+1],yDeriv[i][j+2]);
            }
        }
        interpolate();
    }

    /**
     * The method to interpolate a spline
     * @param data the matrix we know
     */
    public Spline(float[][] data) {
        this.data = data;
        coefficients = new Matrix[data.length-1][data[0].length-1];

        xDeriv = new float[data.length][data[0].length];
        yDeriv = new float[data.length][data[0].length];
        xyDeriv = new float[data.length][data[0].length];

        //init of derivatives
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                if(i==0) xDeriv[0][j] = forwardDiff(1,data[0][j],data[1][j],data[2][j],data[3][j],data[4][j]);
                else if(i==1) xDeriv[1][j] = assymetricDiff(1,data[0][j],data[1][j],data[2][j],data[3][j],data[4][j]);
                else if(i == data.length-1) xDeriv[data.length-1][j] = forwardDiff(-1,data[data.length-1][j],data[data.length-2][j],data[data.length-3][j],data[data.length-4][j],data[data.length-5][j]);
                else if(i == data.length-2) xDeriv[data.length-2][j] = assymetricDiff(-1,data[data.length-1][j],data[data.length-2][j],data[data.length-3][j],data[data.length-4][j],data[data.length-5][j]);
                else xDeriv[i][j] = centeredDiff(1,data[i-2][j],data[i-1][j],data[i+1][j],data[i+2][j]);

                if(j == 0) yDeriv[i][0] = forwardDiff(1,data[i][0],data[i][1],data[i][2],data[i][3],data[i][4]);
                else if(j == 1) yDeriv[i][1] = assymetricDiff(1,data[i][0],data[i][1],data[i][2],data[i][3],data[i][4]);
                else if(j == data[0].length - 1) yDeriv[i][data[0].length-1] = forwardDiff(-1,data[i][data[0].length-1],data[i][data[0].length-2],data[i][data[0].length-3],data[i][data[0].length-4],data[i][data[0].length-5]);
                else if(j == data[0].length - 2) yDeriv[i][data[0].length-2] = assymetricDiff(-1,data[i][data[0].length-1],data[i][data[0].length-2],data[i][data[0].length-3],data[i][data[0].length-4],data[i][data[0].length-5]);
                else yDeriv[i][j] = centeredDiff(1,data[i][j-2],data[i][j-1],data[i][j+1],data[i][j+2]);
            }
        }
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                if(j == 0) xyDeriv[0][j] = forwardDiff(1,yDeriv[i][0],yDeriv[i][1],yDeriv[i][2],yDeriv[i][3],yDeriv[i][4]);
                else if(j == 1) xyDeriv[1][j] = assymetricDiff(1,yDeriv[i][0],yDeriv[i][1],yDeriv[i][2],yDeriv[i][3],yDeriv[i][4]);
                else if(j == data.length - 1) xyDeriv[data.length-1][j] = forwardDiff(-1,yDeriv[i][data[0].length-1],yDeriv[i][data[0].length-2],yDeriv[i][data[0].length-3],yDeriv[i][data[0].length-4],yDeriv[i][data[0].length-5]);
                else if(j == data.length - 2) xyDeriv[data.length-2][j] = assymetricDiff(-1,yDeriv[i][data[0].length-1],yDeriv[i][data[0].length-2],yDeriv[i][data[0].length-3],yDeriv[i][data[0].length-4],yDeriv[i][data[0].length-5]);
                else xyDeriv[i][j] = centeredDiff(1,yDeriv[i][j-2],yDeriv[i][j-1],yDeriv[i][j+1],yDeriv[i][j+2]);
            }
        }
        interpolate();
    }

    private void interpolate(int x, int y){
        float[][] fMatrix = {
                {data[x][y],data[x][y+1],yDeriv[x][y],yDeriv[x][y+1]},
                {data[x+1][y],data[x+1][y+1],yDeriv[x+1][y],yDeriv[x+1][y+1]},
                {xDeriv[x][y],xDeriv[x][y+1],xyDeriv[x][y],xyDeriv[x][y+1]},
                {xDeriv[x+1][y],xDeriv[x+1][y+1],xyDeriv[x+1][y],xyDeriv[x+1][y+1]}};
        coefficients[x][y] = Matrix.multiplication(new Matrix(A1),Matrix.multiplication(new Matrix(fMatrix),new Matrix(A2)));
    }

    private void interpolate(){
        for (int i = 0; i < data.length - 1; i++) {
            for (int j = 0; j < data[0].length - 1; j++) {
                interpolate(i,j);
            }
        }
    }

    @Override
    public float evaluateF(float x, float y) {
        float nx = x%1;
        float ny = y%1;

        if(x > coefficients.length-1) nx = 1;
        if(y > coefficients[0].length) ny = 1;

        float[][] xVector = {{1,nx,(float)Math.pow(nx,2),(float)Math.pow(nx,3)}};
        float[][] yVector = {{1},{ny},{(float)Math.pow(ny,2)},{(float)Math.pow(ny,3)}};
        return evaluate(new Matrix(xVector),new Matrix(yVector),x,y);
    }

    @Override
    public float evaluateXDeriv(float x, float y) {
        float nx = x%1;
        float ny = y%1;

        float[][] xVector = {{0,1,2*nx,(float)(3*Math.pow(nx,2))}};
        float[][] yVector = {{1},{ny},{(float)Math.pow(ny,2)},{(float)Math.pow(ny,3)}};
        return evaluate(new Matrix(xVector),new Matrix(yVector),x,y);
    }

    @Override
    public float evaluateYDeriv(float x, float y) {
        float nx = x%1;
        float ny = y%1;

        float[][] xVector = {{1,nx,(float)Math.pow(nx,2),(float)Math.pow(nx,3)}};
        float[][] yVector = {{0},{1},{2*ny},{(float)(3*Math.pow(ny,2))}};
        return evaluate(new Matrix(xVector),new Matrix(yVector),x,y);
    }

    private float evaluate(Matrix xVector, Matrix yVector, float x, float y){
        if(x > coefficients.length-1) x = coefficients.length-1;
        if(y > coefficients[0].length-1) y = coefficients[0].length-1;
        Matrix result = Matrix.multiplication(Matrix.multiplication(xVector,coefficients[(int)x][(int)y]),yVector);
        return result.get(0,0);
    }

    public void update(ArrayList<Vector3> newData){
        boolean[][] toUpdate = new boolean[coefficients.length][coefficients[0].length];
        for (int i = 0; i < newData.size(); i++) {
            Vector3 nd = newData.get(i);
            data[(int)nd.x][(int)nd.y] = nd.z;
            toUpdate[((int)nd.x)-1][((int)nd.y)-1] = true;
            if((int)nd.y != data.length) toUpdate[((int)nd.x)-1][(int)nd.y] = true;
            if((int)nd.x != data[0].length) toUpdate[(int)nd.x][((int)nd.y)-1] = true;
            if((int)nd.x != data.length && (int)nd.y != data[0].length) toUpdate[(int)nd.x][(int)nd.y] = true;
        }
        for (int i = 0; i < toUpdate.length; i++) {
            for (int j = 0; j < toUpdate[0].length; j++) {
                if(toUpdate[i][j]) interpolate(i,j);
            }
        }
    }

    private float forwardDiff(float h, float v0, float v1, float v2, float v3, float v4){
        return (-(25*v0) + (48*v1) - (36*v2) + (16*v3) - (3*v4)) / (12*h);
    }
    private float assymetricDiff(float h, float vm1, float v0, float v1, float v2, float v3){
        return (-(3*vm1) - (10*v0) + (18*v1) - (6*v2) + v3) / (12*h);
    }
    private float centeredDiff(float h, float vm2, float vm1, float v1, float v2){
        return (vm2 + (8*v1) - (8*vm1) - v2) / (12*h);
    }

}
