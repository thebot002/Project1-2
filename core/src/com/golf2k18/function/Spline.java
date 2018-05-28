package com.golf2k18.function;

import com.badlogic.gdx.math.Vector3;
import com.golf2k18.objects.Matrix;

import java.io.Serializable;
import java.util.ArrayList;

public class Spline implements Function, Serializable {
    private float[][] data;
    private Matrix[][] coefficients;

    private float[][] xDeriv;
    private float[][] yDeriv;
    private float[][] xyDeriv;

    private float[][] A1 = {{1,0,0,0},{0,0,1,0},{-3,3,-2,-1},{2,-2,1,1}};
    private float[][] A2 = {{1,0,-3,2},{0,0,3,-2},{0,1,-2,1},{0,0,-1,1}};

    public Spline(float[][] data, float[][] xDeriv, float[][] yDeriv){
        this.data = data;
        coefficients = new Matrix[data.length-1][data[0].length-1];

        this.xDeriv = xDeriv;
        this.yDeriv = yDeriv;

        xyDeriv = new float[data.length][data[0].length];
        for (int i = 1; i < data.length - 1; i++) {
            for (int j = 1; j < data[0].length-1; j++) {
                if(i == 1) xyDeriv[i][j] = (yDeriv[i][2] - yDeriv[i][0])/2;
                if(i == data.length - 1) xyDeriv[i][j] = (yDeriv[data.length-3][j] - yDeriv[data.length-1][j])/2;
                xyDeriv[i][j] = (yDeriv[i][j+1] - yDeriv[i][j-1])/2;
            }
        }
        interpolate();
    }

    public Spline(float[][] data) {
        this.data = data;
        coefficients = new Matrix[data.length-1][data[0].length-1];

        xDeriv = new float[data.length][data[0].length];
        yDeriv = new float[data.length][data[0].length];
        xyDeriv = new float[data.length][data[0].length];

        //init of derivatives
        for (int i = 1; i < data.length - 1; i++) {
            for (int j = 1; j < data[0].length-1; j++) {
                if(i == 1) yDeriv[i][j] = (data[2][j] - data[0][j])/2;
                if(i == data.length - 1) yDeriv[i][j] = (data[data.length-3][j] - data[data.length-1][j])/2;
                yDeriv[i][j] = (data[i+1][j] - data[i-1][j])/2;

                if(j == 1) xDeriv[i][j] = (data[i][2] - data[i][0])/2;
                if(j == data[0].length - 1) xDeriv[i][j] = (data[i][data[0].length-3] - data[i][data[0].length-1])/2;
                xDeriv[i][j] = (data[i][j+1] - data[i][j-1])/2;
            }
        }
        for (int i = 1; i < data.length - 1; i++) {
            for (int j = 1; j < data[0].length-1; j++) {
                if(i == 1) xyDeriv[i][j] = (yDeriv[i][2] - yDeriv[i][0])/2;
                if(i == data.length - 1) xyDeriv[i][j] = (yDeriv[data.length-3][j] - yDeriv[data.length-1][j])/2;
                xyDeriv[i][j] = (yDeriv[i][j+1] - yDeriv[i][j-1])/2;
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

    public float evaluateXYDeriv(float x, float y) {
        float nx = x%1;
        float ny = y%1;

        float[][] xVector = {{0,1,2*nx,(float)(3*Math.pow(nx,2))}};
        float[][] yVector = {{0},{1},{2*ny},{(float)(3*Math.pow(ny,2))}};
        return evaluate(new Matrix(xVector),new Matrix(yVector),x,y);
    }

    private float evaluate(Matrix xVector, Matrix yVector, float x, float y){
        if(x > coefficients.length-1) x = coefficients.length-1;
        if(y > coefficients[0].length-1) y = coefficients[0].length-1;
        Matrix result = Matrix.multiplication(Matrix.multiplication(xVector,coefficients[(int)x][(int)y]),yVector);
        return result.get(0,0);
    }

    public void update(Vector3 newData){
        data[(int)newData.x][(int)newData.y] = newData.z;
        interpolate(((int)newData.x)-1,((int)newData.y)-1);
        if((int)newData.y != data.length) interpolate(((int)newData.x)-1,(int)newData.y);
        if((int)newData.x != data[0].length) interpolate((int)newData.x,((int)newData.y)-1);
        if((int)newData.x != data.length && (int)newData.y != data[0].length) interpolate((int)newData.x,(int)newData.y);
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
}
