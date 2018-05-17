package com.golf2k18.objects;

public class Spline implements Function {
    private Matrix[][] coefficients;

    private int div;
    private float[][] data;
    private float[][] xDeriv;
    private float[][] yDeriv;
    private float[][] xyDeriv;


    private final float[][] A = {
            { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {-3, 3, 0, 0,-2,-1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            { 2,-2, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0},
            { 0, 0, 0, 0, 0, 0, 0, 0,-3, 3, 0, 0,-2,-1, 0, 0},
            { 0, 0, 0, 0, 0, 0, 0, 0, 2,-2, 0, 0, 1, 1, 0, 0},
            {-3, 0, 3, 0, 0, 0, 0, 0,-2, 0,-1, 0, 0, 0, 0, 0},
            { 0, 0, 0, 0,-3, 0, 3, 0, 0, 0, 0, 0,-2, 0,-1, 0},
            { 9,-9,-9, 9, 6, 3,-6,-3, 6,-6, 3,-3, 4, 2, 2, 1},
            {-6, 6, 6,-6,-3,-3, 3, 3,-4, 4,-2, 2,-2,-2,-1,-1},
            { 2, 0,-2, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 2, 0,-2, 0, 0, 0, 0, 0, 1, 0, 1, 0},
            {-6, 6, 6,-6,-4,-2, 4, 2,-3, 3,-3, 3,-2,-1,-2,-1},
            { 4,-4,-4, 4, 2, 2,-2,-2, 2,-2, 2,-2, 1, 1, 1, 1}
    };

    public Spline(float[][] data, int div, float[][] xDeriv, float[][] yDeriv, float[][] xyDeriv) {
        this.data = data;
        this.div = div;
        this.xDeriv = xDeriv;
        this.yDeriv = yDeriv;
        this.xyDeriv = xyDeriv;

        coefficients = interpolate(data,xDeriv,yDeriv,xyDeriv);
    }

    private Matrix[][] interpolate(float[][] data, float[][] xDeriv, float[][] yDeriv, float[][] xyDeriv){
        Matrix[][] coefficients = new Matrix[data.length-1][data[0].length-1];
        Matrix A = new Matrix(this.A);
        for (int i = 0; i < data.length - 1; i++) {
            for (int j = 0; j < data[0].length - 1; j++) {
                float[][] xarray = {{data[i][j]},{data[i+1][j]},{data[i][j+1]},{data[i+1][j+1]},
                        {xDeriv[i][j]},{xDeriv[i+1][j]},{xDeriv[i][j+1]},{xDeriv[i+1][j+1]},
                        {yDeriv[i][j]},{yDeriv[i+1][j]},{yDeriv[i][j+1]},{yDeriv[i+1][j+1]},
                        {xyDeriv[i][j]},{xyDeriv[i+1][j]},{xyDeriv[i][j+1]},{xyDeriv[i+1][j+1]}};
                Matrix x = new Matrix(xarray);
                coefficients[i][j] = Matrix.multiplication(A,x);
            }
        }
        return coefficients;
    }

    @Override
    public float evaluateF(float x, float y) {
        float[][] tempx = {{1,x,(float)Math.pow(x,2),(float)Math.pow(x,3)}};
        Matrix xVector = new Matrix(tempx);
        float[][] tempy = {{1},{y},{(float)Math.pow(y,2)},{(float)Math.pow(y,3)}};
        Matrix yVector = new Matrix(tempy);

        float[][] tempCoef = new float[4][4];
        for (int i = 0; i < tempCoef.length; i++) {
            for (int j = 0; j < tempCoef[0].length; j++) {
                tempCoef[i][j] = coefficients[(int)x][(int)y].get(1,i+j);
            }
        }
        Matrix coefs = new Matrix(tempCoef);

        Matrix result = Matrix.multiplication(Matrix.multiplication(xVector,coefs),yVector);
        return result.get(0,0);
    }

    @Override
    public float evaluateXDeriv(float x, float y) {
        return 0;
    }

    @Override
    public float evaluateYDeriv(float x, float y) {
        return 0;
    }
}
